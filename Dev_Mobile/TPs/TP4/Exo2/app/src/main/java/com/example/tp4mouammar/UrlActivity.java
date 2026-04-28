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

public class UrlActivity extends AppCompatActivity {
    private EditText etUrl;
    private Button btnOK, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        //Associations des vues
        etUrl = findViewById(R.id.etURL);
        btnOK = findViewById(R.id.btnValiderURL);
        btnCancel = findViewById(R.id.btnAnnulerURL);

        /**
         * Action sur le bouton valider de l'activté URL
         * L'url peut s'ouvrir soit en HTTP ou HTTPS
         */

        btnOK.setOnClickListener(v ->{
            String url = etUrl.getText().toString();
            if(!url.isEmpty()){
                if(!url.startsWith("http://") && !url.startsWith("https://")){
                    url = "http://" + url;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        // On annule l'operation et on retourne à l'activité principale
        btnCancel.setOnClickListener(v -> finish());

    }
}