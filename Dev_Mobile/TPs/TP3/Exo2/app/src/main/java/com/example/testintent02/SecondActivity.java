package com.example.testintent02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private EditText editText;
    private Button btnValider;
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

        editText = findViewById(R.id.textEdit);
        btnValider = findViewById(R.id.btnValider);

        //Recuperation du text actuel à modifier
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TEXTE_ACTUEL")){
            String textActuel = intent.getStringExtra("TEXTE_ACTUEL");
            editText.setText(textActuel);
        }

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textModifer = editText.getText().toString();

                //Preparer le resultat à envoyer
                Intent intentResult = new Intent();
                intentResult.putExtra("TEXTE_MODIFIE", textModifer);
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });
    }
}