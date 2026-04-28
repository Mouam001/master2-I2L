package com.example.tp4mouammar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Declarations des variables pour recuperer les actions des boutons dans le menu principale
    private Button btnCall, btnSMS,btnMMS, btnGeo, btnURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assiciations des vues de l'acitivté principale
        btnCall = findViewById(R.id.btnAppel);
        btnSMS = findViewById(R.id.btnSMS);
        btnMMS = findViewById(R.id.btnMMS);
        btnURL = findViewById(R.id.btnWEB);
        btnGeo = findViewById(R.id.btnGEO);

        //J'actionne chaque bouton vers son activitée
        btnCall.setOnClickListener(v -> startActivity(new Intent(this, CallActivity.class)));
        btnSMS.setOnClickListener(v -> startActivity(new Intent(this, SmsActivity.class)));
        btnMMS.setOnClickListener(v -> startActivity(new Intent(this, SmsActivity.class)));
        btnURL.setOnClickListener(v -> startActivity(new Intent(this, UrlActivity.class)));
        btnGeo.setOnClickListener(v -> startActivity(new Intent(this, GeoActivity.class)));

    }
}