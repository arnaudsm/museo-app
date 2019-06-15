package com.epf.museo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.epf.museo.adapter.HorizontalAdapter;
import com.epf.museo.database.MuseumDatabase;
import com.epf.museo.database.database;
import com.epf.museo.interfaces.ImageDownloader;
import com.epf.museo.models.Musee;
import com.epf.museo.models.MuseeImage;
import com.epf.museo.models.MuseePhoto;
import com.squareup.picasso.Picasso;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> museeIds;
    private static final String TAG ="NomMusee";
    public static final String MUSEE_FILE= "monFichierMusee";
    private static database database;
    private static Musee musee;
    private static ActionBar menu;
    private List<MuseeImage> images;
    private File filePhoto;
    private MuseePhoto museePhoto;

    private RecyclerView recyclerView;
    private HorizontalAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String museeId = intent.getStringExtra("result");

        images = new ArrayList<>();
        // Action Bar
        menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setIcon(R.drawable.ic_museum_alone);
        menu.setTitle("  ");

        FloatingActionButton fab_map = findViewById(R.id.map_button);
        fab_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+musee.getAdresse()+" - "+musee.getVille());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        FloatingActionButton fab_camera = findViewById(R.id.buttonCamera);
        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Camera Stuff
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        // Photos
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photos);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new HorizontalAdapter(images);
        recyclerView.setAdapter(adapter);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmapPhoto = (Bitmap) data.getExtras().get("data");

        File filePhoto = new File(this.getCacheDir(), "temporary_file.jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(filePhoto);
            bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            museePhoto.setFilePhoto(filePhoto);

            posterPhotos();

        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            Toast.makeText(this, "Impossible d'uploader la photo prise", Toast.LENGTH_LONG).show();
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
                    load_photos(museeId);
                }
            });
        }  catch (Exception e) {
            load_photos(museeId);
        }
    }

    private void posterPhotos(){
        final String museeId = musee.getId();
        final File filePhotoPost = museePhoto.getFilePhoto();

        //1 - déclaration de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vps449928.ovh.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //2 - Récupérer une instance de Retrofit et récupération de la liste des endpoints de l'interface MuseeControllerService
        MuseeControllerService museeControllerService = retrofit.create(MuseeControllerService.class);

        //3 - Création de l'appel Call de notre EndPoint getMusee
        Call<String> call = museeControllerService.postMuseePics(filePhotoPost, museeId);

        //4 - démarrage de l'appel
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "resultat envoie de la photo situé en : " + filePhotoPost.getAbsolutePath()+" OK");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "resultat envoie de la photo situé en : " + filePhotoPost.getAbsolutePath()+" ERROR");
            }
        });
    }

    private void load_photos(String museeId){
        images = database.getMuseumImages(museeId);
        adapter.notifyItemInserted(images.size());
    }

    public void download_picture(String pic_url, String museum_id) {
        try {
            URL url = new URL(pic_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MuseeImage picture = database.getImage(pic_url);

        if (picture!= null) {
            Log.e("Good", "image exists");
            display_picture(picture);
        } else {
            Picasso.get()
                    .load(pic_url)
                    .into(new ImageDownloader(pic_url, museum_id, database, this));
        }
    }

    public void display_picture(MuseeImage picture){
        Bitmap bitmap = picture.getImage();
        Log.e("Good", "Image Loaded");
        images.add(picture);
        adapter.notifyItemInserted(images.size());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
