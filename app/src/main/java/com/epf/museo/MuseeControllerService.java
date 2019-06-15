package com.epf.museo;

import com.epf.museo.models.Musee;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MuseeControllerService {

    //Musees Controller
    // indique à Retrofit que nous souhaitons effectuer une requete REST de type GET sur l'url:
    @GET("api/musees/{id}")
    // type retourné correspond au JSON désérialisé. Cette requête retourne une liste d'objet Musee.
    Call<Musee> getMusee(@Path("id") String id);

    @GET("api/musees/{id}/pictures")
    Call<List<String>> getMuseePics(@Path("id") String id);

    @POST("api/musees/{id}/pictures")
    Call<String> postMuseePics(@Path("file")File file, @Path("id") String id);

}

