package com.epf.museo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MuseeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        // TODO : Vérifier si ce musée est déjà téléchargé
        // TODO : Sinon, télécharger ses informations


        ProgressDialog loading = new ProgressDialog(MuseeActivity.this);
        loading.setMessage("Loading Museum");
        loading.show();
        // loading.cancel();

        // TODO : Charger le musée et le stocker dans l'apppli
        // TODO Afficher les infos du musée


    }
}
