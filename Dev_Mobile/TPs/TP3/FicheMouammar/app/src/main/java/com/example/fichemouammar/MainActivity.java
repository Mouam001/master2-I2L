package com.example.fichemouammar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //champs identité
    private TextView valueNom, valuePrenom, valueTel;
    //Champs adresse
    private TextView valueNumero, valueRue, valueVille, valueCP;
    //Boutons
    private Button btnModifierIdentite, btnModifierAdresse;

    //Lanceurs pour recuperer les resultats
    private ActivityResultLauncher<Intent> launcherIdentite;
    private ActivityResultLauncher<Intent> launcherAdresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialiation des vues
        valueNom = findViewById(R.id.valueNom);
        valuePrenom = findViewById(R.id.valuePrenom);
        valueTel = findViewById(R.id.valueTel);

        valueNumero = findViewById(R.id.valueNumero);
        valueRue = findViewById(R.id.valueRue);
        valueVille = findViewById(R.id.valueVille);
        valueCP = findViewById(R.id.valueCP);

        btnModifierAdresse = findViewById(R.id.btnModifierAdresse);
        btnModifierIdentite = findViewById(R.id.btnModifierIdentite);

        //Configurer le laucher pour l'identité
        launcherIdentite = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        String nom = result.getData().getStringExtra("NOM");
                        String prenom = result.getData().getStringExtra("PRENOM");
                        String tel = result.getData().getStringExtra("TEL");

                        if(nom != null && !nom.isEmpty()) valueNom.setText(nom);
                        if(prenom != null && !prenom.isEmpty()) valuePrenom.setText(prenom);
                        if(tel != null && !tel.isEmpty()) valueTel.setText(tel);
                    }
                }
        );

                //Configurer le laucher pour l'adresse
                launcherAdresse = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result ->{
                            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                                String numero = result.getData().getStringExtra("NUMERO");
                                String rue = result.getData().getStringExtra("RUE");
                                String codePostale = result.getData().getStringExtra("CP");
                                String ville = result.getData().getStringExtra("VILLE");

                                if(numero != null && !numero.isEmpty()) valueNumero.setText(numero);
                                if(rue != null && !rue.isEmpty()) valueRue.setText(rue);
                                if(codePostale != null && !codePostale.isEmpty()) valueCP.setText(codePostale);
                                if(ville != null && !ville.isEmpty()) valueVille.setText(ville);
                            }
                        }
        );
                // Bouton pour modifier l'identité
        btnModifierIdentite.setOnClickListener(v -> {

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("NOM", valueNom.getText().toString());
                intent.putExtra("PRENOM", valuePrenom.getText().toString());
                intent.putExtra("TEL", valueTel.getText().toString());
                launcherIdentite.launch(intent);
        });

        // Bouton pour modifier l'adresse
        btnModifierAdresse.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            intent.putExtra("NUMERO", valueNumero.getText().toString());
            intent.putExtra("RUE", valueRue.getText().toString());
            intent.putExtra("CP", valueCP.getText().toString());
            intent.putExtra("VILLE", valueVille.getText().toString());
            launcherAdresse.launch(intent);
        });

    }
}