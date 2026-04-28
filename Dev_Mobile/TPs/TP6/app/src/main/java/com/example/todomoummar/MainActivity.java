package com.example.todomoummar;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvTaches;
    private FloatingActionButton btnAdd ;
    private ArrayList<String> listeTaches;
    private ArrayAdapter<String> adapter;
    private String FILENAME = "taches.txt";

    //laucher pour recuperer la nouvelle tache
    private ActivityResultLauncher<Intent> laucherAjout;
    private  String [] items = new String[]{
            "valeur01", "text02", "item03",
            "valeur01", "text02", "Mouammar",
            "valeur01", "Soulé", "item03",
            "Nicolas", "text02", "item03"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvTaches = findViewById(R.id.lvTaches);
        btnAdd = findViewById(R.id.btnAdd);

        //charger la liste si un fichier existe
        chargerFichier();

        //Init list dynamique
        listeTaches = new ArrayList<>();
        listeTaches.add("Valeur01");
        listeTaches.add("Item03");
        listeTaches.add("Mouammar");
        listeTaches.add("Soulé");
        listeTaches.add("item04");
        listeTaches.add("Valeur02");
        listeTaches.add("Nicolas");

       // Adapter
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_multiple_choice, listeTaches
        );
        lvTaches.setAdapter(adapter);

        // Recuperation des taches
        laucherAjout = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        String newTask = result.getData().getStringExtra("TACHE");
                        if(newTask != null && !newTask.isEmpty()){
                            listeTaches.add(newTask);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        btnAdd.setOnClickListener((v ->{
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            laucherAjout.launch(intent);
        }));
    }

    /**
     * Exo 3: Gerer le menu pour Effacer selectionnées
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_delete){
            SparseBooleanArray checked = lvTaches.getCheckedItemPositions();

            for(int i = lvTaches.getCount() - 1; i >=0; i--){
                if(checked.get(i)){
                    listeTaches.remove(i);
                }
            }
            lvTaches.clearChoices();
            adapter.notifyDataSetChanged();
            return true;
        }

        //EXO4 : Sauvegarder la liste dans un fichier
        if(item.getItemId() == R.id.action_sauve){
            try{
                FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
                for(String t : listeTaches){
                    fos.write((t + "\n").getBytes());
                }
                fos.close();
                Toast.makeText(this, "Liste sauvegardé", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "Erreur de sauvegarde", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Charger la liste enregistré si fichier existant
     */
    private void chargerFichier(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line = reader.readLine()) != null){
                listeTaches.add(line);
            }
            reader.close();
        }catch (Exception e){

        }
    }

    /**
     * EXO 1 : charger la liste via un tableau de chaine de caractere
     */
    void chargerList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                items
        );

        lvTaches.setAdapter(adapter);
    }



}