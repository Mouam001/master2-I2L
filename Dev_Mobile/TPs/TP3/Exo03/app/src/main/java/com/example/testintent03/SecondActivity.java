package com.example.testintent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewContenu;
    private Button btnMajuscules, btnInverser, btnRetour;

    private String texteOriginal = "";
    private String texteTransforme = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewContenu = findViewById(R.id.textResult2);
        btnMajuscules = findViewById(R.id.btnMajuscule);
        btnInverser = findViewById(R.id.btnInverser);
        btnRetour = findViewById(R.id.btnRetour);


        // Récupérer le texte actuel de MainActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEXTE_ACTUEL")) {
            texteOriginal = intent.getStringExtra("TEXTE_ACTUEL");
            textViewContenu.setText(texteOriginal);
            texteTransforme = texteOriginal; // Initialiser avec le texte original
        }

        btnMajuscules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transformation en majuscules
                texteTransforme = texteOriginal.toUpperCase();
                textViewContenu.setText(texteTransforme);
            }
        });

        btnInverser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inversion de la chaîne
                texteTransforme = new StringBuilder(texteOriginal).reverse().toString();
                textViewContenu.setText(texteTransforme);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Renvoyer le texte transformé à MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("TEXTE_MODIFIE", texteTransforme);
                setResult(RESULT_OK, resultIntent);
                finish(); // Fermer cette activité et retourner à MainActivity
            }
        });
    }
}