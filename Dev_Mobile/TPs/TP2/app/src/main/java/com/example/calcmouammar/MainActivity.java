package com.example.calcmouammar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private EditText operand1, operand2;
    private RadioGroup groupOperations;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MaterialToolbar topbar = findViewById(R.id.topAppBar);
        setSupportActionBar(topbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recuperation des vues
        operand1 = findViewById(R.id.operand1);
        operand2 = findViewById(R.id.operand2);
        groupOperations = findViewById(R.id.group_operations);
        result = findViewById(R.id.result);

        Button btnEqual = findViewById(R.id.btn_equal);
        Button btnReset = findViewById(R.id.btn_reset);
        Button btnQuit = findViewById(R.id.btn_quit);


        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1.setText("");
                operand2.setText("");
                groupOperations.clearCheck();
                calculateResult();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void calculateResult() {
        String op1 = operand1.getText().toString();
        String op2 = operand2.getText().toString();

        Double nbr1 = null, nbr2 = null;

        try {
            nbr1 = Double.parseDouble(op1);
            nbr2 = Double.parseDouble(op2);
        } catch (NumberFormatException e) {
            result.setText("Erreur ; entrer deux nomvre valides");
            return;
        }

        int selectedId = groupOperations.getCheckedRadioButtonId();

        if (selectedId == -1) {
            result.setText("Erreur ; choisissez une operation");
            return;
        }

        double res;

        if (selectedId == R.id.rb_plus) {
            res = nbr1 + nbr2;
        } else if (selectedId == R.id.rb_minus) {
            res = nbr1 - nbr2;
        } else if (selectedId == R.id.rb_multiply) {
            res = nbr1 * nbr2;
        } else if (selectedId == R.id.rb_divide) {
            if (nbr2 == 0) {
                result.setText("Erreur : division par zero");
                return;
            }
            res = nbr1 / nbr2;
        } else {
            result.setText("Erreur : operation nconnue");
            return;
        }

        result.setText("Resultat : " + res);
    }
}