package com.example.tp8mouammar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView lattitude, longitude;
    private ImageButton imageButton;
    private Button btnCoord, btnPhots, btnKm;
    private Float currentLat = null, currentLong = null;
    private final ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imageView.setImageURI(uri);
                    readExif(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                    2
            );
        }

        //Recuperation des vues depuis le layout
        imageButton = findViewById(R.id.btnCharger);
        imageView = findViewById(R.id.imageView);
        lattitude = findViewById(R.id.lattitude);
        longitude = findViewById(R.id.longitude);
        btnCoord = findViewById(R.id.coordonees);
        btnPhots = findViewById(R.id.photoProches);
        btnKm = findViewById(R.id.btnExo4);

        imageButton.setOnClickListener(v -> selectImage());
        btnCoord.setOnClickListener(v -> afficherCoordones());

        btnPhots.setOnClickListener(v -> {
            if (currentLat == null || currentLong == null) {
                Toast.makeText(this, "Aucun coordonnées GPS pour cette photo", Toast.LENGTH_SHORT).show();
                return;
            }

            // ouvrir le SecondActivity en passant les coordonnées
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("refLat", currentLat);
            intent.putExtra("refLong", currentLong);
            startActivity(intent);
        });

        btnKm.setOnClickListener(v -> {
                    Intent intent = new Intent(this, ThirdActivity.class);
                    startActivity(intent);
                }
        );

    }

    private void selectImage() {
        imagePicker.launch("image/*");
    }

    private void readExif(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ExifInterface exif = new ExifInterface(inputStream);

            float[] latLong = new float[2];
            boolean hastLatLong = exif.getLatLong(latLong);

            if (hastLatLong) {
                currentLat = latLong[0];
                currentLong = latLong[1];
            } else {
                currentLat = null;
                currentLong = null;

            }
        } catch (Exception e) {
            e.printStackTrace();
            currentLat = null;
            currentLong = null;
        }

    }

    private void afficherCoordones() {
        if (currentLong != null && currentLat != null) {
            lattitude.setText("Latitude : +" + currentLat);
            longitude.setText("Longitude : " + currentLong);
        } else {
            lattitude.setText("Latitude : inconnue");
            longitude.setText("Longitude : inconnue ");
        }
    }
}