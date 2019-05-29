package com.epf.museo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MuseeActivity extends AppCompatActivity {

    ArrayList<String> museeIds;
    private static final String TAG ="NomMusee";
    public static final String MUSEE_FILE= "monFichierMusee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musee);

        Intent intent = getIntent();
        String museeId = intent.getStringExtra("result");

        //Toast.makeText(this, "L'id du musé téléchargé est : "+ id, Toast.LENGTH_SHORT).show();

        museeIds = new ArrayList<String>();

        if(museeIds.contains(museeId)){
            Toast.makeText(getApplicationContext(),"Ce musé a déjà été téléchargé", Toast.LENGTH_SHORT).show();
        } else {
            // TODO : charger le musée

            Toast.makeText(getApplicationContext(),"Téléchargement du musée", Toast.LENGTH_SHORT).show();
            ProgressDialog loading = new ProgressDialog(MuseeActivity.this);
            loading.setMessage("Loading Museum");
            loading.show();

            try {
                chargerMusee(museeId);
            } catch (IOException e) {
                e.printStackTrace();
            }

            loading.cancel();

            // TODO : Afficher les infos du musée

            try {
                getFromInterne();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
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
                //2.5 - On appel les méthodes de Callback au bon moment

                Musee musee = response.body();
                Log.d(TAG, musee.getNom());
                Toast.makeText(MuseeActivity.this, "API response OK", Toast.LENGTH_SHORT).show();

                // TODO: stocker le musée dans un fichier

                try {
                    saveInterne(musee);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Musee> call, Throwable t) {
                //2.5 - On appel les méthodes de Callback au bon moment
                Toast.makeText(MuseeActivity.this, "L'API response Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveInterne(Musee musee) throws IOException {

//        SharedPreferences preferences = getSharedPreferences(FICHIER_MUSEE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("nomMusee", musee.getNom());
//        editor.commit();

        // creation du fichier
        FileOutputStream outputStream = openFileOutput(MUSEE_FILE, MODE_PRIVATE);

        String id = musee.getId();
        String nom =  musee.getNom();
        String periode_ouverture = musee.getPeriode_ouverture();
        String adresse = musee.getAdresse();
        String ville = musee.getVille();
        boolean ferme = musee.isFerme();
        String fermeture_annuelle = musee.getFermeture_annuelle();
        String site_web = musee.getSite_web();
        String cp = Integer.toString(musee.getCp());
        String region = musee.getRegion();
        String dept = musee.getDept();

        // ecriture

        outputStream.write(id.getBytes());
        outputStream.write(nom.getBytes());
        outputStream.write(periode_ouverture.getBytes());
        outputStream.write(adresse.getBytes());
        outputStream.write(ville.getBytes());
        outputStream.write((ferme ? 1 : 0));
        outputStream.write(fermeture_annuelle.getBytes());
        outputStream.write(site_web.getBytes());
        outputStream.write(cp.getBytes());
        outputStream.write(region.getBytes());
        outputStream.write(dept.getBytes());

        outputStream.close();

    }

    private String getFromInterne() throws IOException {

        String value = null;

        FileInputStream inputStream = openFileInput(MUSEE_FILE);
        StringBuilder stringBuilder = new StringBuilder();
        int content;
        while((content=inputStream.read())!=-1){
            value = String.valueOf(stringBuilder.append((char)content));
        }

        return value;
    }
}
