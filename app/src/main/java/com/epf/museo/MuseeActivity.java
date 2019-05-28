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
//            new ListMuseeTask().execute("5c637e3c61e55c808b31e1ae12a57fc5c4842b4b");
            loading.cancel();
        }


        // TODO : Charger le musée et le stocker dans l'apppli

        // TODO Afficher les infos du musée


    }

//    public void afficherNombreMusee(List<Musee> musees){
//        Toast.makeText(this, "nombre de musées : "+musees.size(), Toast.LENGTH_SHORT).show();
//    }
//
//    Class ListMuseeTask extends AsyncTask<String, Void, List<Musee>>{
//
//    }
//    MuseeControllerService museeControllerService = new RestAdapter.Builder()
//            .setEndpoint(MuseeControllerService.ENDPOINT)
//            .build()
//            .create(MuseeControllerService.class);
}
