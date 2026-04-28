package com.example.examen21_22;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnSaisie, btnStart;
    private String player1 = null, player2 = null;
    private ActivityResultLauncher<Intent> playersLaucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gestion des redictions avec les layouts
        btnSaisie = findViewById(R.id.btnSaisiePlayers);
        btnStart = findViewById(R.id.btnStartPart);

        // Désactiver le demarrage tant que les jouers ne sont pas saisies
        btnStart.setEnabled(false);

        // initialisation du laucher
        playersLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        player1 = result.getData().getStringExtra("PLAYER_1");
                        player2 = result.getData().getStringExtra("PLAYER_2");

                        btnStart.setEnabled(true);
                    }
                }
        );
        //Lancer le launcher
        btnSaisie.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, PlayersActivity.class);
            playersLaucher.launch(intent);
        });


        //Demarrer le match
        btnStart.setOnClickListener(v -> {
            if(player1 == null || player2 == null){
                Toast.makeText(this,
                        "Veuillez saisir les joeurs avant de demarrer",
                Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, MatchActivity.class);
            intent.putExtra("PLAYER_1", player1);
            intent.putExtra("PLAYER_2", player2);
            startActivity(intent);
        });

    }

}