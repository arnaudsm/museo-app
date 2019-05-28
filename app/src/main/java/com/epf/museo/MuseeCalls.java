package com.epf.museo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuseeCalls extends AppCompatActivity {

        Intent intent = getIntent();
        String id = intent.getStringExtra("museeId");

    //1 -  Interface de callback a été créée afin que le controleur puisse récupérer certains moments de l'exécution de la requete
    public interface Callbacks {
        void onResponse(@Nullable List<Musee> id);
        void onFailure();
    }

    //2- methode statique publique nous permettra de lancer depuis le controleur notre requete reseau
    public static void fetchUserFollowing(Callbacks callbacks, String id){

        //2.1 - Création d'une reference faible pour permettre au Garbage Collectore de le supprimer au besoin
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        //2.2 - Récupérer une instance de Retrofit et récupération de la liste des endpoints de l'interface MuseeControllerService
        MuseeControllerService museeControllerService = MuseeControllerService.retrofit.create(MuseeControllerService.class);

        //2.3 -Création de l'appel Call de notre EndPoint getMusee
        Call<List<Musee>> call = museeControllerService.getMusee(id);

        //2.4 -
        call.enqueue(new Callback<List<Musee>>() {
            @Override
            public void onResponse(Call<List<Musee>> call, Response<List<Musee>> response) {
                //2.5 - On appel les méthodes de Callback au bon moment
                if(callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Musee>> call, Throwable t) {
                //2.5 - On appel les méthodes de Callback au bon moment
                if(callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onFailure();
            }
        });
    }
}
