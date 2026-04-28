package com.example.examen21_22;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {
    private Button btnOkMathc, btnStartGame;
    private TextView tvSet1P1, tvSet1P2;
    private TextView tvSet2P1, tvSet2P2;
    private TextView tvSet3P1, tvSet3P2;
    private String player1, player2;
    private TextView tvPlayers;
    private int gameSet1P1 = 0, gameSet1P2 = 0;
    private int gameSet2P1 = 0, gameSet2P2 = 0;
    private int gameSet3P1 = 0, gameSet3P2 = 0;
    private int setsWonP1 = 0, setsWonP2 = 0;
    private int currentSet= 1;
    private ActivityResultLauncher<Intent> gameLaucher;
    private ArrayList<String> scoreHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Toolbar toolbar = findViewById(R.id.toolbarMatch);
        setSupportActionBar(toolbar);

        // recuperation des vues
        btnOkMathc = findViewById(R.id.btnOkMathc);
        btnStartGame = findViewById(R.id.btnStartGame);
        tvPlayers = findViewById(R.id.tvPlayers);

        tvSet1P1 = findViewById(R.id.tvSet1Player1);
        tvSet1P2 = findViewById(R.id.tvSet1Player2);
        tvSet2P1 = findViewById(R.id.tvSet2Player1);
        tvSet2P2 = findViewById(R.id.tvSet2Player2);
        tvSet3P1 = findViewById(R.id.tvSet3Player1);
        tvSet3P2 = findViewById(R.id.tvSet3Player2);

        // recuperation des joueurs depuis l'activité précedente
        Intent intent = getIntent();
        player1 = intent.getStringExtra("PLAYER_1");
        player2 = intent.getStringExtra("PLAYER_2");

        //Affichage des noms
        tvPlayers.setText(player1 + " vs " + player2);

        //Initialisations des scores 0
        tvSet1P1.setText("0");
        tvSet1P2.setText("0");
        tvSet2P1.setText("0");
        tvSet2P2.setText("0");
        tvSet3P1.setText("0");
        tvSet3P2.setText("0");

        // Lancer GameActitvity
        btnStartGame.setOnClickListener(v ->{
            Intent gameIntent = new Intent(MatchActivity.this, GameActivity.class);
            gameIntent.putExtra("PLAYER_1", player1);
            gameIntent.putExtra("PLAYER_2", player2);
            //startActivity(gameIntent);
            gameLaucher.launch(gameIntent);
        });

        gameLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        int winner = result.getData().getIntExtra("GAME_WINNER", -1);
                        handleGameWinner(winner);
                    }
                }
        );

        btnOkMathc.setOnClickListener(v -> finish());

    }

    /**
     * Methode pour gerer l'option du menu
     * dans le toolbar : afficher Score
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.miroir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.affichageScores){
            Intent intent = new Intent(MatchActivity.this, ScoreActivity.class);
            intent.putStringArrayListExtra("SCORES_HISTORY", scoreHistory);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleGameWinner(int winner) {

        if (currentSet == 1) {
            if (winner == 1) gameSet1P1++;
            else gameSet1P2++;

            tvSet1P1.setText(String.valueOf(gameSet1P1));
            tvSet1P2.setText(String.valueOf(gameSet1P2));

            if (isSetFinished(gameSet1P1, gameSet1P2)) {
                if (gameSet1P1 > gameSet1P2) setsWonP1++;
                else setsWonP2++;
                currentSet = 2;
            }

        } else if (currentSet == 2) {
            if (winner == 1) gameSet2P1++;
            else gameSet2P2++;

            tvSet2P1.setText(String.valueOf(gameSet2P1));
            tvSet2P2.setText(String.valueOf(gameSet2P2));

            if (isSetFinished(gameSet2P1, gameSet2P2)) {
                if (gameSet2P1 > gameSet2P2) setsWonP1++;
                else setsWonP2++;
                currentSet = 3;
            }

        } else if (currentSet == 3) {
            if (winner == 1) gameSet3P1++;
            else gameSet3P2++;

            tvSet3P1.setText(String.valueOf(gameSet3P1));
            tvSet3P2.setText(String.valueOf(gameSet3P2));

            if (isSetFinished(gameSet3P1, gameSet3P2)) {
                if (gameSet3P1 > gameSet3P2) setsWonP1++;
                else setsWonP2++;
            }
        }

        scoreHistory.add(
                "Set " + currentSet + " : "
                        + player1 + " " + getCurrentGamesP1()
                        + " - " + getCurrentGamesP2() + " " + player2
        );

        // Fin du match
        if (setsWonP1 == 2 || setsWonP2 == 2) {
            btnStartGame.setEnabled(false);
            tvPlayers.setText(
                    (setsWonP1 == 2 ? player1 : player2) + " a gagné le match"
            );
        }
    }

    private boolean isSetFinished(int p1, int p2) {
        return (p1 >= 6 || p2 >= 6) && Math.abs(p1 - p2) >= 2;
    }

    private int getCurrentGamesP1() {
        return currentSet == 1 ? gameSet1P1 :
                currentSet == 2 ? gameSet2P1 : gameSet3P1;
    }

    private int getCurrentGamesP2() {
        return currentSet == 1 ? gameSet1P2 :
                currentSet == 2 ? gameSet2P2 : gameSet3P2;
    }

}