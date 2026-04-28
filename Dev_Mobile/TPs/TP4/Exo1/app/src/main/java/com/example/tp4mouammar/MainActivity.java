package com.example.tp4mouammar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.net.Uri;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity{
    private Button btnAppel, btnSMS, btnMMS, btnWEB, btnGEO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperation des cliques sur les boutons
        btnAppel = findViewById(R.id.btnAppel);
        btnSMS = findViewById(R.id.btnSMS);
        btnMMS = findViewById(R.id.btnMMS);
        btnWEB = findViewById(R.id.btnWEB);
        btnGEO = findViewById(R.id.btnGEO);


        //Exo 1 => Q3  action sur le bouton Appeler
        btnAppel.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0773779164"));
            startActivity(intent);
        });

        //Exo 1 => Q4  action sur le bouton SMS
        btnSMS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:0773779164"));
            intent.putExtra("sms_body", "Bonjour c'est moi le dev mobile");
            startActivity(intent);
        });

        //Exo 1 => Q5  action sur le bouton MMS  /home/mouammar/Images/Captures d’écran
        btnMMS.setOnClickListener(v -> {
           // String fileString = "";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra("address", "0773779164");
            intent.putExtra("sms_body", "Bonjour c'est moi le dev mobile");
           // intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(fileString)));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Envoyer un MMS"));
        });

        //Exo 1 => Q6  action sur le bouton WEB
        btnWEB.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www-lisic.univ-littoral.fr"));
            startActivity(intent);
        });

        //Exo 1 => Q7  action sur le bouton GEO
        btnGEO.setOnClickListener(v -> {
            Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+CA+94043");
            Intent intent = new Intent(Intent.ACTION_VIEW, location);
            startActivity(intent);
        });
    }

    //Exo 1 => Q2  en gérant le tous sur un seul
    //Methode unique appelée pour touts les boutons
   /* @Override
    Exo 1 => Q2  en gérant le tous sur un seul
        btnAppel.setOnClickListener(this);
        btnSMS.setOnClickListener(this);
        btnMMS.setOnClickListener(this);
        btnWEB.setOnClickListener(this);
        btnGEO.setOnClickListener(this);
    public void onClick(View v){
        if(v instanceof Button){
            Button b = (Button) v;
            String text = b.getText().toString();
            Toast.makeText(this, "vous avez cliqué sur :" + text, Toast.LENGTH_SHORT).show();

        }

    }*/
}