package com.example.projetmouammar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuizActivity extends AppCompatActivity {
    // UI
    private Button btnRetourHome, btnValider;
    private TextView tvCategorie, tvProgress, tvQuestion;
    private RadioGroup qcmChoices;
    private RadioButton q1, q2, q3;

    // Données
    private List<Question> questions;
    private int indexQuestion = 0;
    private int score = 0;

    // infos recus
    private String quizId;
    private String quizFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Boutons
        btnRetourHome = findViewById(R.id.btnStop);
        btnValider = findViewById(R.id.btnValider);

        // TextView
        tvCategorie = findViewById(R.id.tvCategorie);
        tvQuestion = findViewById(R.id.questionQCM);
        tvProgress = findViewById(R.id.textView2);

        // RadioGroup and RadioButton
        qcmChoices = findViewById(R.id.qcmChoices);
        q1 = findViewById(R.id.Q1);
        q2 = findViewById(R.id.Q2);
        q3 = findViewById(R.id.Q3);

        // Recuperation des données envoyées par SelectQuizActivity
        quizId = getIntent().getStringExtra("QUIZ_ID");
        quizFile = getIntent().getStringExtra("QUIZ_FILE");
        if (quizId == null || quizFile == null) {
            finish();
            return;
        }

        // Stop => abandon = score 0
        btnRetourHome.setOnClickListener(v -> {
            ScoreManager.saveScore(this, quizId, 0);
            finish();
        });

        // Charger le premier QCM
        chargerQCM();
        btnValider.setOnClickListener(v -> validerReponse());
    } // fin de On Create


    private void chargerQCM() {
        questions = loadQuestionsFromAssets(quizFile);
        // Mode aleatoire des questions
        Collections.shuffle(questions);
        indexQuestion = 0;
        score = 0;

        if (questions.isEmpty()) {
            finish();
            return;
        }

        afficherQuestion();

    }

    private void validerReponse() {
        //Verifier q'une reponse est selectionnée
        int checkedId = qcmChoices.getCheckedRadioButtonId();
        if (checkedId == -1) {
            return;
        }

        // recuperation de l'index de la réponse choisie
        int selectedIndex;
        if (checkedId == R.id.Q1) selectedIndex = 0;
        else if (checkedId == R.id.Q2) selectedIndex = 1;
        else selectedIndex = 2;

        // verifier si la reponse est correcte
        Question q = questions.get(indexQuestion);
        if (selectedIndex == q.getBonneReponse()) {
            score++;
        }

        indexQuestion++;

        // Question suivante
        if (indexQuestion < questions.size()) {
            afficherQuestion();
        } else {
            // QCM terminé
            ScoreManager.saveScore(this, quizId, score);
            startActivity(new Intent(this, ScoreActivity.class));
            finish();
        }
    }


    /**
     * Charge les questions depuis les fichiers QCM dans assets
     */
    private List<Question> loadQuestionsFromAssets(String fileName) {
        List<Question> questions = new ArrayList<>();

        try {
            InputStream is = openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String categorie = reader.readLine(); // 1ere ligne = categorie
            String line;

            while ((line = reader.readLine()) != null) {

                //ignorer lignes vides
                if (line.trim().isEmpty()) continue;

                //ligne = enoncé
                String enonce = line;

                List<String> reponses = new ArrayList<>();
                int bonneReponse = -1;

                // lire les 3 reponses
                for (int i = 0; i < 3; i++) {
                    // String rep = reader.readLine().trim();
                    String rep;
                    do {
                        rep = reader.readLine();
                    } while (rep != null && rep.trim().isEmpty());
                    rep = rep.trim();

                    if (rep.endsWith("x")) {
                        rep = rep.replace("x", "").trim();
                        bonneReponse = i;
                    }
                    reponses.add(rep);
                }
                questions.add(new Question(categorie, enonce, reponses, bonneReponse));
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    private void afficherQuestion() {
        Question q = questions.get(indexQuestion);

        tvCategorie.setText(q.getCategorie());
        tvProgress.setText("Question " + (indexQuestion + 1) + "/" + questions.size());
        tvQuestion.setText(q.getEnnonce());

        q1.setText(q.getReponses().get(0));
        q2.setText(q.getReponses().get(1));
        q3.setText(q.getReponses().get(2));

        qcmChoices.clearCheck();
    }
}