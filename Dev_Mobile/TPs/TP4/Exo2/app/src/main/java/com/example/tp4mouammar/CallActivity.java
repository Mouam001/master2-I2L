package com.example.tp4mouammar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CallActivity extends AppCompatActivity {
    private EditText etCall;
    private Button btnOK, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        // Assiciations des vues
        etCall = findViewById(R.id.etNumero);
        btnOK = findViewById(R.id.btnValiderCall);
        btnCancel = findViewById(R.id.btnAnnulerCall);


        btnOK.setOnClickListener(v -> {
            String numero = etCall.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + numero));
            startActivity(intent);
        });

        // On annule l'operation et on retourne à l'activité principale
        btnCancel.setOnClickListener(v -> finish());

    }
}