package com.example.contentProviderMouammar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.ArrayList;


public class VisualiserActivity extends AppCompatActivity {

    private static final int REQ_CALENDAR = 100; // code de  requette pour la permission READ_CALENDAR
    private ListView listView; // Listes graphiques des éléments
    private MaterialSwitch switchOrder;
    private boolean reverseOrder = false;
    private Button btnRetour ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser);

        // INitialisation des composants graphiques
        listView = findViewById(R.id.listEvents);
        switchOrder = findViewById(R.id.switchOrder);
        btnRetour = findViewById(R.id.btnRetour);

        // Gestion du changement d'etat du switch de tri
        switchOrder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            reverseOrder = isChecked;
            loadEvents();
        });

        // Verification de la permission du READ_CALENDAR
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALENDAR
        ) != PackageManager.PERMISSION_GRANTED){
            // Demande de permission à l'utilisateur
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CALENDAR},
                    REQ_CALENDAR
            );
        } else {
            // Permission accordée
            loadEvents();
        }

        btnRetour.setOnClickListener (v -> finish() );

    }

    /**
     * Recupere les évenements de l'agenda via le contentProvider
     * et les affiches dans la listView
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults){
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);

        if(requestCode == REQ_CALENDAR && grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadEvents();
        }
    }

    private void loadEvents(){
        ArrayList<Event> events = new ArrayList<>();

        // Definition de l'ordre de tri selon l'état du switch
        String order = reverseOrder
                ? CalendarContract.Events.TITLE + " DESC"
                : CalendarContract.Events.TITLE + " ASC";

        Cursor cursor = getContentResolver().query(
                CalendarContract.Events.CONTENT_URI, new String[]{CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND},
                null, null, order
        );
        // parcours les resultats
        if (cursor != null){
            while (cursor.moveToNext()){
                String title = cursor.getString(0);
                long start = cursor.getLong(1);
                long end = cursor.getLong(2);
                events.add(new Event(title, start, end));
            }
            cursor.close();
        }

        // assocaiton de la liste d'evenement à l'adapter personnalisé
        EventAdapter adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);
    }
}