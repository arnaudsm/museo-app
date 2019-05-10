package com.epf.museo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String url = intent.getStringExtra("result");


        if( urls.contains(url)){
            Toast.makeText(getApplicationContext(),"Ce musé a déjà été téléchargé", Toast.LENGTH_SHORT).show();
        }
        // TODO : Sinon, télécharger ses informations
        else {
            Toast.makeText(getApplicationContext(),"Téléchargement du musé", Toast.LENGTH_SHORT).show();
        }


        ProgressDialog loading = new ProgressDialog(MuseeActivity.this);
        loading.setMessage("Loading Museum");
        loading.show();
        // loading.cancel();

        // TODO : Charger le musée et le stocker dans l'apppli
        // TODO Afficher les infos du musée


    }
}
