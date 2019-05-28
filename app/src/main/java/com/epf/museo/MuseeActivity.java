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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> museeIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String museeId = intent.getStringExtra("result");

        museeIds = new ArrayList<String>();

        if(museeIds.contains(museeId)){
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

            launchActivity(MuseeCalls.class);
            Intent MuseeCalls = new Intent(MuseeActivity.this, MuseeCalls.class);
            getIntent().putExtra("museeId",museeId);
            startActivity(MuseeCalls);

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
