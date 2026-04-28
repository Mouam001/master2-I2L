package com.example.contentProviderMouammar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnAgenda, btnVisualiser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgenda = findViewById(R.id.btnAgenda);
        btnVisualiser = findViewById(R.id.btnVisualiser);

        // Acceder à l'agenda et ajouter
        btnAgenda.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            startActivity(intent);
        });

        btnVisualiser.setOnClickListener(v->{
            Intent intent = new Intent(this, VisualiserActivity.class);
            startActivity(intent);
        });

    }
}