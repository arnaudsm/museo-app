package com.epf.museo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> museeIds;

    private static final String TAG ="NomMusee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String id = intent.getStringExtra("result");

        Toast.makeText(this, "L'id du musé téléchargé est : "+ id, Toast.LENGTH_SHORT).show();

        museeIds = new ArrayList<String>();

        if(museeIds.contains(id)){
            Toast.makeText(getApplicationContext(),"Ce musé a déjà été téléchargé", Toast.LENGTH_SHORT).show();
        }
        // TODO : Sinon, télécharger ses informations
        else {
            Toast.makeText(getApplicationContext(),"Téléchargement du musée", Toast.LENGTH_SHORT).show();
            ProgressDialog loading = new ProgressDialog(MuseeActivity.this);
            loading.setMessage("Loading Museum");
            loading.show();

            // TODO : Charger le musée et le stocker dans l'apppli
            // TODO Afficher les infos du musée

            //1 - déclaration de Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vps449928.ovh.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //2 - Récupérer une instance de Retrofit et récupération de la liste des endpoints de l'interface MuseeControllerService
            MuseeControllerService museeControllerService = retrofit.create(MuseeControllerService.class);

            //3 -Création de l'appel Call de notre EndPoint getMusee
            Call<Musee> call = museeControllerService.getMusee(id);

            //4 -
            call.enqueue(new Callback<Musee>() {
                @Override
                public void onResponse(Call<Musee> call, Response<Musee> response) {
                    //2.5 - On appel les méthodes de Callback au bon moment
                    Musee body = response.body();
                    Log.d(TAG, body.getNom());
                    Toast.makeText(MuseeActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Musee> call, Throwable t) {
                    //2.5 - On appel les méthodes de Callback au bon moment
                    Toast.makeText(MuseeActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            });

            loading.cancel();
        }
        // TODO : Charger le musée et le stocker dans l'apppli

        // TODO Afficher les infos du musée
    }

    public void launchActivity(Class<?> clss) {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
    }

}
