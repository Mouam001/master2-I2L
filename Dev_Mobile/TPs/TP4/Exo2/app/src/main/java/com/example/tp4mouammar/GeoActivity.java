package com.example.tp4mouammar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GeoActivity extends AppCompatActivity {
    private EditText etLatitude, etLongitude;
    private Button btnOK, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        // Assiciations des vues
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        btnOK = findViewById(R.id.btnValiderGeo);
        btnCancel = findViewById(R.id.btnAnnulerGeo);

        btnOK.setOnClickListener(v -> {
            String lat = etLatitude.getText().toString();
            String lon = etLongitude.getText().toString();

            if (!lat.isEmpty() && !lon.isEmpty()) {
                try{
                double latitude = Double.parseDouble(lat);
                double longitude = Double.parseDouble(lon);

                // Construction de l'URI geo
                String uri = "geo:" + latitude + "," + longitude;

                //Creation de l'intention
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

                // gere les execeptions en cas de numero invalide ou la saisies des lettres ou caractere
            } catch(NumberFormatException e){
                Toast.makeText(this, "Veuillez entrer des nombres valides", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Veuillez remplir latitude et longitude", Toast.LENGTH_SHORT).show();
        }
        });

        // On annule l'operation et on retourne à l'activité principale
        btnCancel.setOnClickListener(v -> finish());


    }
}