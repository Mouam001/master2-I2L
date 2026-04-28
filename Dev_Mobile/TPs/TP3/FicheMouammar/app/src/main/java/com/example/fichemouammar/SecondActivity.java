package com.example.fichemouammar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private EditText etNom, etPrenom, etTel;
    private Button btnCancel, btnOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etTel = findViewById(R.id.etTelephone);

        btnCancel = findViewById(R.id.btnAnnulerIdentite);
        btnOK = findViewById(R.id.btnValiderIdentite);

        // Remplir avec les données reçues
        Intent intent = getIntent();
        if(intent != null){
            etNom.setText(intent.getStringExtra("NOM"));
            etPrenom.setText(intent.getStringExtra("PRENOM"));
            etTel.setText(intent.getStringExtra("TEL"));
        }

        //Valider et renvoyer les données
        btnOK.setOnClickListener(
                v -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("NOM", etNom.getText().toString());
                    resultIntent.putExtra("PRENOM", etPrenom.getText().toString());
                    resultIntent.putExtra("TEL", etTel.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                });

        //Annulation et retour sans modification
        btnCancel.setOnClickListener(
                v -> {
                    setResult(RESULT_CANCELED);
                    finish();
                });
    }
}