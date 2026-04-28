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

public class ThirdActivity extends AppCompatActivity {
    private EditText etNumero, etRue, etCP, etVille;
    private Button btnCancel, btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etNumero = findViewById(R.id.etNumero);
        etRue = findViewById(R.id.etRue);
        etCP = findViewById(R.id.etCodePostal);
        etVille = findViewById(R.id.etVille);

        btnOK = findViewById(R.id.btnValiderAdresse);
        btnCancel = findViewById(R.id.btnAnnulerAdresse);

        // Remplir avec les données reçues
        Intent intent = getIntent();
        if(intent != null){
            etNumero.setText(intent.getStringExtra("NUMERO"));
            etRue.setText(intent.getStringExtra("RUE"));
            etCP.setText(intent.getStringExtra("CP"));
            etVille.setText(intent.getStringExtra("VILLE"));
        }

        //Valider et renvoyer les données
        btnOK.setOnClickListener(
                v -> {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("NUMERO", etNumero.getText().toString());
                    resultIntent.putExtra("RUE", etRue.getText().toString());
                    resultIntent.putExtra("CP", etCP.getText().toString());
                    resultIntent.putExtra("VILLE", etVille.getText().toString());
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