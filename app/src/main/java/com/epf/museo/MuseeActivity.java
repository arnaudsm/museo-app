package com.epf.museo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> urls;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String url = intent.getStringExtra("result");

        urls = new ArrayList<String>();

        if(urls.contains(url)){
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
            this.configureAndShowMainFragment();
            loading.cancel();
        }


        // TODO : Charger le musée et le stocker dans l'apppli

        // TODO Afficher les infos du musée


    }

    // CONFIGURATION

    private void configureAndShowMainFragment(){

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_musee_frame_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_musee_frame_layout, mainFragment)
                    .commit();
        }
    }
}
