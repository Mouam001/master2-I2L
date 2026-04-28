package com.example.projetmouammar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;


public class ScoreActivity extends AppCompatActivity {
    private static final String[] DEFAULT_QCMS = {
            "qcm01", "qcm02", "qcm03"};
    private Button btnRetourHome;
    private TextView scorePre, scoreInfo, scoreLit, noteMoyenne, noteAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Text Viex pour les scores
        scorePre = findViewById(R.id.notePre);
        scoreInfo = findViewById(R.id.noteInfo);
        scoreLit = findViewById(R.id.scoreLit);
        noteMoyenne = findViewById(R.id.noteMoy);
        noteAdmin = findViewById(R.id.noteAdmin);

        // bouton Ok pour Retour à l'acceuil
        btnRetourHome = findViewById(R.id.OK);

        // Affichage les 3 QCM par defaut
        int totalScore = 0;
        int totalMax = 0;

        //qcm01
        totalScore += setDefaultLine(scorePre, "qcm01");
        totalMax += countQuestions("qcm01");

        //qcm02
        totalScore += setDefaultLine(scoreInfo, "qcm02");
        totalMax += countQuestions("qcm02");

        //qcm03
        totalScore += setDefaultLine(scoreLit, "qcm03");
        totalMax += countQuestions("qcm03");

        // Categorie ajouté en ADMIN
        StringBuilder adminText = new StringBuilder();
        Map<String, ?> all = ScoreManager.getAllScores(this);


        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String key = entry.getKey();
            // On ne traite que lescores
            if (!key.endsWith("_score")) continue;

            String quizId = key.replace("_score", "");

            // j'ignore les 3 QCM par defaut
            if (isdefaultQcm(quizId)) continue;

            int score = (int) entry.getValue();
            String categoryName = readCategoryFromFile(quizId);
            int max = countQuestions(quizId);

            adminText.append("Note questionnaire ")
                    .append(categoryName)
                    .append(" : ")
                    .append(score)
                    .append("/")
                    .append(max)
                    .append("\n");

            totalScore += score;
            totalMax += max;

        }

        noteAdmin.setText(adminText.toString().trim());

        double moyenne = (totalMax == 0) ? 0 : ((double) totalScore / totalMax) * 20.0;
        noteMoyenne.setText("Note moyenne : " + String.format("%.2f", moyenne) + "/20");

        btnRetourHome.setOnClickListener(v -> finish());

    }

    private int countQuestions(String quizId) {
        int count = 0;
        try {
            var reader = new BufferedReader(
                    new InputStreamReader(openFileInput(quizId + ".txt"))
            );
            reader.readLine(); // catégorie

            String line;
            while ((line = reader.readLine()) != null) {

                // sauter les lignes vides
                if (line.trim().isEmpty()) continue;
                count++;

                for (int i = 0; i < 3; i++) {
                    do {
                        line = reader.readLine();
                    } while (line != null && line.trim().isEmpty());
                }

                // }
            }
            reader.close();
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    private String readCategoryFromFile(String quizId) {
        try {
            var br = new BufferedReader(
                    new InputStreamReader(openFileInput(quizId + ".txt"))
            );
            String category = br.readLine();
            br.close();
            if (category != null && !category.trim().isEmpty()) {
                return category;
            }

        } catch (Exception ignored) {
        }
        return quizId;

    }

    private boolean isdefaultQcm(String quizId) {
        for (String id : DEFAULT_QCMS) {
            if (id.equals(quizId)) return true;
        }
        return false;
    }

    /**
     * Affichage une ligne QCM par defaut et renvoie du score pour additionner
     */
    private int setDefaultLine(TextView tv, String quizId) {
        String category = readCategoryFromFile(quizId);
        int score = ScoreManager.getScore(this, quizId);
        int max = countQuestions(quizId);

        tv.setText("Note questionnaire " + category
                + " : " + score + "/" + max);
        return score;
    }
}

