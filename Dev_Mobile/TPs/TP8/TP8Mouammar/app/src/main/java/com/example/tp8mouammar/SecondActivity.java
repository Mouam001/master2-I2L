package com.example.tp8mouammar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private double refLat, refLong;
    private ArrayList<PhotoInfo> photos = new ArrayList<>();
    private ListView listView;
    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = findViewById(R.id.listDistPhotos);
        btnRetour = findViewById(R.id.btnRetour);

        //Retour vers MainActivity
        btnRetour.setOnClickListener(v -> finish());

        //Recuperation des coordonnées GPS envoyées depuis le MainActivity
        refLat = getIntent().getFloatExtra("refLat", 0f);
        refLong = getIntent().getFloatExtra("refLong", 0f);

        //charger les photos referent
        loadPhotos();

        //affiche chemin + distances
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                convertToDisplayList()
        );
        listView.setAdapter(adapter);
    }

    private static class PhotoInfo{
        String path;
        double distance;
        PhotoInfo(String p, double d){
            path = p;
            distance = d;
        }
    }

    private void loadPhotos(){
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null
        );

        if(cursor != null){
            int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

            while (cursor.moveToNext()){
                String path = cursor.getString(index);
                try{
                    ExifInterface exif = new ExifInterface(path);
                    float[] latLong = new float[2];

                    boolean hasGPS = exif.getLatLong(latLong);

                    // on inclue pas les photos sans coordonnées
                    if(!hasGPS) continue;

                    //calcul de la distance entre photo courent et references photos
                    double dist = haversine(refLat, refLong, latLong[0], latLong[1]);
                    photos.add(new PhotoInfo(path, dist));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        // Tri croissant
        Collections.sort(photos, (a, b) -> Double.compare(a.distance, b.distance));
    }

    // Convertir la liste vers du text pour l'affichage
    private ArrayList<String> convertToDisplayList(){
        ArrayList<String> list = new ArrayList<>();

        for(PhotoInfo p : photos){
            list.add(
                    "image : " + p.path + "\nDistance : "
                    + String.format("%.2f km", p.distance)
            );
        }
        return list;
    }

    // Fonction qui calcule la distance orthodynamique
    private double haversine(double lat1, double long1, double lat2, double long2){
        double R = 6371; // Rayon de la Terre en KM

        double dLat = Math.toRadians(lat2 - lat1);
        double dLong = Math.toRadians(long2 - long1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(lat1) * Math.cos(lat2)
                + Math.sin(dLong/2) * Math.sin(dLong/2);

        double c = 2 *Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }
}