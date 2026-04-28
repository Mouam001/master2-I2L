package com.example.testintent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnEdit, btnValider;
    private EditText editTextSaisie;
    private TextView textViewAttente, textViewModifier;

    private String texteInitial = "";
    private String modificationAttente = "";

    // Pour recevoir le résultat de SecondActivity
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        modificationAttente = result.getData().getStringExtra("TEXTE_MODIFIE");

                        if (modificationAttente != null && !modificationAttente.isEmpty()) {
                            // Afficher la modification en attente
                            textViewModifier.setText(modificationAttente);
                            textViewAttente.setVisibility(View.VISIBLE);
                            textViewModifier.setVisibility(View.VISIBLE);
                            btnValider.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnEdit = findViewById(R.id.btnModifier);
        btnValider = findViewById(R.id.btnValider);
        editTextSaisie = findViewById(R.id.textEdit);
        textViewAttente = findViewById(R.id.textViewAttente);
        textViewModifier = findViewById(R.id.textViewResultat1);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texteInitial = editTextSaisie.getText().toString();

                if (texteInitial.isEmpty()) {
                    texteInitial = "Texte vide";
                }

                // Créer l'intention pour SecondActivity avec le texte actuel
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("TEXTE_ACTUEL", texteInitial);
                launcher.launch(intent);
            }
        });

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Valider la modification
                if (!modificationAttente.isEmpty()) {
                    editTextSaisie.setText(modificationAttente);

                    // Masquer la section d'attente
                    textViewAttente.setVisibility(View.GONE);
                    textViewModifier.setVisibility(View.GONE);
                    btnValider.setVisibility(View.GONE);

                    modificationAttente = "";
                }
            }
        });
    }
}