package com.example.testintent01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * Recuperation du text écris sur l'activité principale pour l'affiché
         */

        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        //ON verifie si l'intention n'est pas vide
        if(intent != null && intent.hasExtra("MESSAGE_KEY")){
            String message = intent.getStringExtra("MESSAGE_KEY");
            textView.setText(message);
        } else {
            textView.setText(R.string.message_re_u);
        }

    }
}