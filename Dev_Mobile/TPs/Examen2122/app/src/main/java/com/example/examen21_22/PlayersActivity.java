package com.example.examen21_22;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayersActivity extends AppCompatActivity {

    private Button btnOkMain, btnValider;
    private EditText etPlayer1, etPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        btnOkMain = findViewById(R.id.btnOkMain);
        btnValider = findViewById(R.id.btnValiderPlayers);
        etPlayer1 = findViewById(R.id.etPlayer1);
        etPlayer2 = findViewById(R.id.etPlayer2);

        // Retour à l'acceuil
        btnOkMain.setOnClickListener(v -> finish());

        btnValider.setOnClickListener(v -> {
            // Verfifier et retourner les noms
            String player1 = etPlayer1.getText().toString().trim();
            String player2 = etPlayer2.getText().toString().trim();
            if (player1.isEmpty() || player2.isEmpty()) {
                Toast.makeText(
                        PlayersActivity.this, "Veuillez saisir les noms des deux joueurs",
                        Toast.LENGTH_SHORT).show();
                return;

            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra("PLAYER_1", player1);
            resultIntent.putExtra("PLAYER_2", player2);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}

