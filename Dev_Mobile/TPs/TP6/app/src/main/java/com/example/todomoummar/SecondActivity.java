package com.example.todomoummar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private EditText editView;
    private Button btnValider, btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editView = findViewById(R.id.etView);
        btnValider = findViewById(R.id.btnValider);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        btnValider.setOnClickListener(v -> {
            String text = editView.getText().toString();
            Intent data = new Intent();
            data.putExtra("TACHE", text);
            setResult(RESULT_OK, data);
            finish();
        });

        // On annule l'operation et on retourne à l'activité principale
        btnAnnuler.setOnClickListener(v -> finish());


    }
}