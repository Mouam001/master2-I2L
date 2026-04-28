package com.example.tp8mouammar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import java.io.IOException;
import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private EditText inputDistanceX;
    private Button btnFilterX, btnAcceuil;
    private GridView gridPhotos;
    private Location userLocation;
    private final ArrayList<String> filterPhotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Recuperation des layout
        inputDistanceX = findViewById(R.id.inputDistanceX);
        btnFilterX = findViewById(R.id.btnFiltrerX);
        gridPhotos = findViewById(R.id.gridPhotosX);
        btnAcceuil = findViewById(R.id.btnRetour);

        //obtenir la postion de l'utilisateur
        getUserLocation();

        btnFilterX.setOnClickListener(v -> filterPhotos());
        btnAcceuil.setOnClickListener(v -> finish());

    }

    /*
    * Cette fonction recupere la derniere position connue de l'user
    * utilise la locationManager avec les permissions necessaire
    * si aucun position n'est dispo, un message d'erreur est affiché
    * */
    private void getUserLocation() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            return;
        }
        userLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (userLocation == null) {
            userLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (userLocation == null) {
            Toast.makeText(this, "Impossible de recuperer la position utilisateur", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * Lis la distance x choisis par le user
    * parcourt toutes les photos du peripheriques
    * calcul la distance avec le point GPS de User
    * prend les photos à mois de x Km
    * affiche les resultats dans une GridView
    * */
    private void filterPhotos() {
        if (userLocation == null) {
            Toast.makeText(this, "Position utilisateur inconnue", Toast.LENGTH_SHORT).show();
            return;
        }

        String val = inputDistanceX.getText().toString();

        if (val.isEmpty()) {
            Toast.makeText(this, "Entrez une distance X", Toast.LENGTH_SHORT).show();
            return;
        }

        double maxDistance = Double.parseDouble(val);

        filterPhotos.clear();

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA},
                null, null, null
        );
        if (cursor != null) {
            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            while (cursor.moveToNext()) {
                String path = cursor.getString(index);
                try {
                    ExifInterface exif = new ExifInterface(path);
                    float[] latLong = new float[2];
                    boolean hasGPS = exif.getLatLong(latLong);

                    if (!hasGPS) continue;
                    double dist = haversine(
                            userLocation.getLatitude(),
                            userLocation.getLongitude(),
                            latLong[0],
                            latLong[1]
                    );

                    if (dist <= maxDistance) {
                        filterPhotos.add(path);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                filterPhotos
        );
        gridPhotos.setAdapter(adapter);

    }

    // Fonction qui calcule la distance orthodynamique
    private double haversine(double lat1, double long1, double lat2, double long2) {
        double R = 6371; // Rayon de la Terre en KM

        double dLat = Math.toRadians(lat2 - lat1);
        double dLong = Math.toRadians(long2 - long1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                + Math.sin(dLong / 2) * Math.sin(dLong / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}