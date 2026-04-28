package com.example.tp4mouammar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SmsActivity extends AppCompatActivity {
    private EditText etNumSMS, etMessage;
    private Button btnOK, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        etNumSMS = findViewById(R.id.etNumSMS);
        etMessage = findViewById(R.id.etMessage);
        btnOK = findViewById(R.id.btnValiderSMS);
        btnCancel = findViewById(R.id.btnAnnulerSMS);

        //Action pour l'activité message avec un umero destinée
        btnOK.setOnClickListener(v ->{
            String numero = etNumSMS.getText().toString();
            String messageBody = etMessage.getText().toString();

            if(!numero.isEmpty() && !messageBody.isEmpty()){
                Uri uri = Uri.parse("smsto:" + numero);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body",messageBody);

                //ON lance l'action pour envoyer le sms
                startActivity(intent);
            }
        });
        // On annule l'operation et on retourne à l'activité principale
        btnCancel.setOnClickListener(v -> finish());
    }
}