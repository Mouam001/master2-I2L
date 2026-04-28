package com.example.tpimagemouammar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageButton btnCharger, btnAnnuler;
    private TextView tuUri;
    private Bitmap currentBitmap = null; // pour garder l'image courante
    private Bitmap originalBitmap = null; // image d'origine


    // Mécanisme pour recuperer un résultat d'intent
    private ActivityResultLauncher<String> launcherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Recuperation des vues depuis le layout
        btnCharger = findViewById(R.id.btnCharger);
        imageView = findViewById(R.id.imageView);
        tuUri = findViewById(R.id.tvUri);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        // enregistrer le menu contextuel
        registerForContextMenu(imageView);

        // Configuration du launcher pour selectionner une image
        launcherImage = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ChargerImage(uri);
                        tuUri.setText(uri.toString());
                    } else {
                        tuUri.setText("Aucun image selectionnée" + uri.toString());
                    }
                }
        );

        // Laucherdossier pour charger une image
        btnCharger.setOnClickListener(v -> {
            // Ouvre la galerie pour choisir une image
            launcherImage.launch("image/*");
        });

        // Revenir à l'image initiale
        btnAnnuler.setOnClickListener(v ->{
            if(originalBitmap != null){
                currentBitmap = originalBitmap.copy(originalBitmap.getConfig(), true);
                imageView.setImageBitmap(currentBitmap);
                Toast.makeText(this, "Image reinitialisée", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Aucun image à restaurer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Methode pour charger et afficher l'image depuis une URI
     */
    private void ChargerImage(Uri imageUri) {
        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inMutable = true;

            Bitmap bm = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(imageUri),
                    null,
                    option
            );

            //Afficher le bitmap dans l'imageView
            if (bm != null) {
                currentBitmap = bm;
                originalBitmap = bm.copy(bm.getConfig(), true);
                imageView.setImageBitmap(bm);
                Toast.makeText(this, "Image chargée", Toast.LENGTH_SHORT).show();
            } else {
                tuUri.setText("Erreur : imposible de charger l'image");
            }
        } catch (Exception e) {
            e.printStackTrace();
            tuUri.setText("Erreur de chargement " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    /**
     * Methode pour gerer les differents options du menu dans
     * le toolbar : inversion, rotation
     * @param item The menu item that was selected.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (currentBitmap == null) {
            Toast.makeText(this, "Aucun image chargée", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_miroir_h) {
            currentBitmap = miroirHorizontal(currentBitmap);
            imageView.setImageBitmap(currentBitmap);
            Toast.makeText(this, "Miroir horizontal", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_miroir_v) {
            currentBitmap = miroirVertical(currentBitmap);
            imageView.setImageBitmap(currentBitmap);
            Toast.makeText(this, "Miroir vertical", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if( id == R.id.action_horaire) {
            currentBitmap = rotation90(currentBitmap, true);
            imageView.setImageBitmap(currentBitmap);
            Toast.makeText(this, "Image en rotation horaire à 90°", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_antiHoraire) {
            currentBitmap = rotation90(currentBitmap, false);
            imageView.setImageBitmap(currentBitmap);
            Toast.makeText(this, "Image en rotation anti-horaire à 90°", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * EXO2
     * Miroir horizontal : inversion gauche => droite
     */
    private Bitmap miroirHorizontal(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap mirrored = Bitmap.createBitmap(width, height, src.getConfig());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = src.getPixel(x, y);
                mirrored.setPixel(width - 1 - x, y, pixel);
            }
        }
        return mirrored;
    }

    /**
     * Miroir vertical : droite => gauche
     */
    private Bitmap miroirVertical(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap mirrored = Bitmap.createBitmap(width, height, src.getConfig());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = src.getPixel(x, y);
                mirrored.setPixel(x, height - 1 - y, pixel);
            }
        }
        return mirrored;
    }

    // EXO3
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextuel, menu); // la ou je met mon menu contexuel et l'afficher
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (currentBitmap == null) {
            Toast.makeText(this, "Aucun image chargée", Toast.LENGTH_SHORT).show();
            return true;
        }

        if( id == R.id.action_inverser) {
            currentBitmap = inverserCouleur(currentBitmap);
            imageView.setImageBitmap(currentBitmap);
            Toast.makeText(this, "Couleur inversé", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.action_gris) {
            currentBitmap = convertirGris(currentBitmap);
            imageView.setImageBitmap(currentBitmap);
            Toast.makeText(this, "Image en niveau gris", Toast.LENGTH_SHORT).show();
            return true;
        }
                return super.onOptionsItemSelected(item);
    }

    /**
     * Inverser les couleurs de l'image
     */
    private Bitmap inverserCouleur(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int pixel = src.getPixel(x, y);

                int a = android.graphics.Color.alpha(pixel);
                int r = 255 - android.graphics.Color.red(pixel);
                int g = 255 - android.graphics.Color.green(pixel);
                int b = 255 - android.graphics.Color.blue(pixel);

                result.setPixel(x, y, android.graphics.Color.argb(a, r, g, b));
            }
        }
        return result;
    }

    /**
     * Convertir l'image en niveaux de gris
     */
    private Bitmap convertirGris(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int pixel = src.getPixel(x, y);

                int a = android.graphics.Color.alpha(pixel);
                int r = android.graphics.Color.red(pixel);
                int g = android.graphics.Color.green(pixel);
                int b = android.graphics.Color.blue(pixel);

                int m = ( r + g + b)/ 3;

                result.setPixel(x, y, android.graphics.Color.argb(a, m, m, m));
            }
        }
        return result;
    }

    /** EXO3
     *  Rotation À 90° horaire et anti-horaire
      */
    private Bitmap rotation90(Bitmap src, boolean horaire) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap result = Bitmap.createBitmap(height, width, src.getConfig());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = src.getPixel(x, y);
                if (horaire) {
                    result.setPixel(y, width - 1 - x, pixel);
                } else {
                    result.setPixel(height - 1 - y, x, pixel);
                }
            }
        }
        return result;
    }

}