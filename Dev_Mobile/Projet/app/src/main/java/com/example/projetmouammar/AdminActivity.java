package com.example.projetmouammar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;


public class AdminActivity extends AppCompatActivity {

    private Button btnLeave, btnAjouter;
    private EditText etCategorie, etQuestions, etR1, etR2, etR3;
    private RadioGroup rgBonne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnLeave = findViewById(R.id.btnRetourAdm);
        etCategorie = findViewById(R.id.etCategorie);
        etQuestions = findViewById(R.id.etQuestion);
        etR1 = findViewById(R.id.etRep1);
        etR2 = findViewById(R.id.etRep2);
        etR3 = findViewById(R.id.etRep3);
        rgBonne = findViewById(R.id.rgBonneReponse);
        btnAjouter = findViewById(R.id.btnValiderAdm);

        btnAjouter.setOnClickListener(v -> {
            ajouterQuestion();
        });

        btnLeave.setOnClickListener(v -> {
            finish();
        });

    }

    private void ajouterQuestion() {
        try {
            // Verifications 
            String categorie = etCategorie.getText().toString().trim();
            String question = etQuestions.getText().toString().trim();
            String r1 = etR1.getText().toString().trim();
            String r2 = etR2.getText().toString().trim();
            String r3 = etR3.getText().toString().trim();

            // protection pour les categories par defauts
            String categorieLower = categorie.toLowerCase();
            if (categorieLower.equals("prehistoire")
                    || categorieLower.equals("informatique")
                    || categorieLower.equals("litterature")) {
                Toast.makeText(this, "Catégorie existe deja", Toast.LENGTH_LONG).show();
                return;
            }

            if (categorie.isEmpty() || question.isEmpty() || r1.isEmpty() || r2.isEmpty() || r3.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            int checkedId = rgBonne.getCheckedRadioButtonId();
            if (checkedId == -1) {
                Toast.makeText(this, "Veuillez sélectionner la bonne réponse", Toast.LENGTH_SHORT).show();
                return;
            }

            // Nom du fichier propre
            String fileName = categorie.toLowerCase().replace(" ", "_") + ".txt";

            boolean fileExists = false;
            for (String file : fileList()) {
                if (file.equals(fileName)) {
                    fileExists = true;
                    break;
                }
            }

            FileOutputStream fos;
            // Si le fichier n'existe pas encore, on le crée avec la catégorie en 1ere ligne
            if (!fileExists) {
                fos = openFileOutput(fileName, MODE_PRIVATE);
                fos.write((categorie + "\n").getBytes());
            } else {
                fos = openFileOutput(fileName, MODE_APPEND);
            }

            // Marque la bonne réponse avec " x"
            if (checkedId == R.id.rb1) r1 += " x";
            if (checkedId == R.id.rb2) r2 += " x";
            if (checkedId == R.id.rb3) r3 += " x";

            // Ecriture complete du bloc de question 
            fos.write(("\n" + question + "\n").getBytes());
            fos.write((r1 + "\n").getBytes());
            fos.write((r2 + "\n").getBytes());
            fos.write((r3 + "\n").getBytes());

            fos.close();

            // Nettoyage UI
            etQuestions.setText("");
            etR1.setText("");
            etR2.setText("");
            etR3.setText("");
            rgBonne.clearCheck();

            Toast.makeText(this, "Question ajoutée", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de l'ajout de la question", Toast.LENGTH_SHORT).show();
        }
    }

}