package com.example.projetmouammar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SelectQuizActivity extends AppCompatActivity {
    private ListView listQuiz;
    private Button btnLeave;
    private final List<String> quizIds = new ArrayList<>();
    private final List<String> quizTitles = new ArrayList<>();
    private final List<String> quizFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz);

        listQuiz = findViewById(R.id.listQuiz);
        btnLeave = findViewById(R.id.btnQuitter);

        displayQuizList();
        setupClickListener();
        btnLeave.setOnClickListener(v -> finish());

    }

    /**
     * Preparre et affiche la lisres des questionnaires
     * en indiquant ceux deja completés
     */
    private void displayQuizList() {
        quizIds.clear();
        quizFiles.clear();
        quizTitles.clear();

        String[] files = fileList(); // Fichiers internes

        for (String file : files) {
            if (!file.endsWith(".txt")) continue;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(openFileInput(file))
                );

                // 1ere ligne = categorie
                String categorie = reader.readLine();
                reader.close();

                if (categorie == null || categorie.trim().isEmpty()) continue;

                String quizId = file.replace(".txt", "");
                quizIds.add(quizId);
                quizFiles.add(file);

                if (ScoreManager.isDone(this, quizId)) {
                    quizTitles.add(categorie + " (complété)");
                } else {
                    quizTitles.add(categorie);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    quizTitles
            );
            listQuiz.setAdapter(adapter);

        }
    }

    /**
     * Gestion du clic qur un questionnaire
     */

    private void setupClickListener() {
        listQuiz.setOnItemClickListener((parent, view, position, id) -> {

            // Si deja completé
            if (ScoreManager.isDone(this, quizIds.get(position))) {
                Toast.makeText(this, "Vous avez déjà complété ce questionnaire.", Toast.LENGTH_SHORT).show();
                return;
            }
            launchQuiz(position);
        });
    }

    /**
     * Lance l'activité QuizActivity avec le questionnaire sélectionné
     */
    private void launchQuiz(int position) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("QUIZ_ID", quizIds.get(position));
        intent.putExtra("QUIZ_FILE", quizFiles.get(position));
        startActivity(intent);

    }
}