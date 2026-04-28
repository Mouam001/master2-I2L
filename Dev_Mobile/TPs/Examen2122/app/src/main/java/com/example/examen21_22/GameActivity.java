package com.example.examen21_22;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private TextView txtPlayer1, txtPlayer2;
    private TextView txtScorePlayer1, txtScorePlayer2;
    private TextView txtHint;
    private ImageButton btnPointPlayer1, btnPointPlayer2;
    private String player1, player2;

    private int pointsP1 = 0;
    private int pointsP2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Récupération des vues
        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);
        txtScorePlayer1 = findViewById(R.id.txtScorePlayer1);
        txtScorePlayer2 = findViewById(R.id.txtScorePlayer2);
        txtHint = findViewById(R.id.txtHint);

        btnPointPlayer1 = findViewById(R.id.btnPointPlayer1);
        btnPointPlayer2 = findViewById(R.id.btnPointPlayer2);

        // recuperation des jouers
        Intent intent = getIntent();
        player1 = intent.getStringExtra("PLAYER_1");
        player2 = intent.getStringExtra("PLAYER_2");

        txtPlayer1.setText(player1);
        txtPlayer2.setText(player2);

        updateScoreDisplay();

        // joueur 1 marque un point
        btnPointPlayer1.setOnClickListener(v->{
            pointsP1++;
            handleGameLogic();
        });

        // joueur 2 marque un point
        btnPointPlayer2.setOnClickListener(v->{
            pointsP2++;
            handleGameLogic();
        });

    }

    /**
     * Gestions des regles du jeu
     */
    private void handleGameLogic() {

        //cas victoir du jeu
        if(pointsP1 >=4 || pointsP2 >= 4){
            int diff = Math.abs(pointsP1 - pointsP2);
            if(diff >= 2){
                int winner = pointsP1 > pointsP2 ? 1 :2;

                Intent resultIntent = new Intent();
                resultIntent.putExtra("GAME_WINNER", winner);
                setResult(RESULT_OK, resultIntent);
                finish();
                return;
            }
            // égalité / avantage
            if(pointsP1 == pointsP2){
                txtHint.setText("Egalité");
            }else if(pointsP1 > pointsP2){
                txtHint.setText("Avantage " + player1);
            } else {
                txtHint.setText("Avantage " + player2);
            }
        }

        updateScoreDisplay();
    }

    /**
     * Mise à jour de l'affichage des scores
     */

    private void updateScoreDisplay(){
        txtScorePlayer1.setText(convertPoints(pointsP1));
        txtScorePlayer2.setText(convertPoints(pointsP2));
    }

    /**
     * COnversion des points en score tennis
     */

    private String convertPoints(int points) {
        switch (points) {
            case 0: return "0";
            case 1: return "15";
            case 2: return "30";
            case 3: return "40";
            default: return "40";
        }

    }

}