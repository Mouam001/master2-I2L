package com.example.projetmouammar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button btnQCM, btnScore, btnAdmin, btnLeave, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associatins des vues de l'activité principale
        btnQCM = findViewById(R.id.btnQCM);
        btnScore = findViewById(R.id.btnScore);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnLeave = findViewById(R.id.btnQuitterHome);
        btnReset = findViewById(R.id.btnReset);
        btnAdmin = findViewById(R.id.btnAdmin);

        // J'actionne chaque bouton vers son actitivité ou son role
        btnQCM.setOnClickListener(v -> startActivity(new Intent(this, SelectQuizActivity.class)));
        btnScore.setOnClickListener(v -> startActivity(new Intent(this, ScoreActivity.class)));
        btnReset.setOnClickListener(v -> {
            ScoreManager.resetAll(this);
        });
        btnAdmin.setOnClickListener(v -> startActivity(new Intent(this, AdminLoginActivity.class)));
        btnLeave.setOnClickListener(v -> {
            finish();
        });

        // charge des QCM par défaut s'ils n'existent pas encore
        initDefaultQCM();

    }

    private void initDefaultQCM() {
        String[] defaultFiles = {"qcm01.txt", "qcm02.txt", "qcm03.txt"};

        for (String file : defaultFiles) {
            if (!fileExists(file)) {
                copyFileFromAssets(file);
            }
        }
    }

    private boolean fileExists(String filename) {
        for (String file : fileList()) {
            if (file.equals(filename)) {
                return true;
            }
        }
        return false;
    }

    private void copyFileFromAssets(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}