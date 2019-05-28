package com.epf.museo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MuseeControllerService {

    //Musees Controller
    // indique à Retrofit que nous souhaitons effectuer une requete REST de type GET sur l'url:
    @GET("/api/musees/{id}")
    // type retourné correspond au JSON désérialisé. Cette requête retourne une liste d'objet Musee.
    Call<List<Musee>> getMusee(@Path("id") String id);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://vps449928.ovh.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

