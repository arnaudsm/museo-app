package com.epf.museo;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.epf.museo.database.MuseumDatabase;
import com.epf.museo.database.database;
import com.epf.museo.interfaces.ImageDownloader;
import com.epf.museo.models.Musee;
import com.epf.museo.models.MuseeImage;
import com.squareup.picasso.Picasso;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> museeIds;
    private static final String TAG ="NomMusee";
    public static final String MUSEE_FILE= "monFichierMusee";
    private static database database;
    private static Musee musee;
    private static ActionBar menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String museeId = intent.getStringExtra("result");

        // Action Bar
        menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setIcon(R.drawable.ic_museum_alone);
        menu.setTitle("  ");


        FloatingActionButton fab = findViewById(R.id.map_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+musee.getAdresse()+" - "+musee.getVille());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        // BDD
        MuseumDatabase databaseBuilder = Room.databaseBuilder(this, MuseumDatabase .class, "mydb")
                .allowMainThreadQueries()
                .build();
        database = databaseBuilder.getDatabase();

        musee = database.getItemById(museeId);

        if(musee != null){
            afficherMusee();
        } else {
            try {
                chargerMusee(museeId);
            } catch (Exception e) {
                errorMusee();
            }
        }
    }

    public void errorMusee(){
        Snackbar.make(findViewById(android.R.id.content), "Error Loading Museum", Snackbar.LENGTH_LONG).show();
        finish();
    }

    public void afficherMusee() {
        Snackbar.make(findViewById(android.R.id.content), "Museum Loaded", Snackbar.LENGTH_LONG).show();

        menu.setTitle(" "+musee.getNom());

        ((TextView) findViewById(R.id.ouverture)).setText(musee.getPeriode_ouverture());
        ((TextView) findViewById(R.id.fermeture)).setText("Fermeture le "+musee.getFermeture_annuelle());





        chargerPhotos();
    }

    public void saveMusee(){
        database.insert(musee);
        Snackbar.make(findViewById(android.R.id.content), "Museum Saved", Snackbar.LENGTH_LONG).show();
    }

    public void chargerMusee(String museeId) throws IOException {

        //1 - déclaration de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vps449928.ovh.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2 - Récupérer une instance de Retrofit et récupération de la liste des endpoints de l'interface MuseeControllerService
        MuseeControllerService museeControllerService = retrofit.create(MuseeControllerService.class);

        //3 - Création de l'appel Call de notre EndPoint getMusee
        Call<Musee> call = museeControllerService.getMusee(museeId);

        //4 - Démarrage de l'appel
        call.enqueue(new Callback<Musee>() {
            @Override
            public void onResponse(Call<Musee> call, Response<Musee> response) {
                musee = response.body();
                response.body().getNom();
                try {
                    afficherMusee();
                    saveMusee();
                }  catch (Exception e) {
                    errorMusee();
                }
            }
            @Override
            public void onFailure(Call<Musee> call, Throwable t) {
                errorMusee();
            }
        });
    }


    private void chargerPhotos(){
        final String museeId = musee.getId();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vps449928.ovh.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //2 - Récupérer une instance de Retrofit et récupération de la liste des endpoints de l'interface MuseeControllerService
            MuseeControllerService museeControllerService = retrofit.create(MuseeControllerService.class);

            //3 - Création de l'appel Call de notre EndPoint getMusee
            Call<List<String>> call = museeControllerService.getMuseePics(museeId);

            //4 - Démarrage de l'appel
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    List<String> museePics = response.body();
                    for(String pic_url : museePics){
                        download_picture(pic_url, museeId);
                    }

                }
                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {

                }
            });
        }  catch (Exception e) {
            List<MuseeImage> images = database.getMuseumImages(museeId);
            Log.e("Good", "Images loaded:"+images.size());

            for(MuseeImage image: images){
                display_picture(image);
            }
        }
    }

    public void download_picture(String pic_url, String museum_id) {
        try {
            URL url = new URL(pic_url);
            MuseeImage museeImage = database.getImage(pic_url);

            if (museeImage != null) {
                Log.e("Good", "image exists");
                display_picture(museeImage);
            } else {
                Picasso.get()
                .load(pic_url)
                .into(new ImageDownloader(pic_url, museum_id, database, this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display_picture(MuseeImage picture){
        Bitmap bitmap = picture.getImage();
        Log.e("Good", "Image Loaded");
    }
}
