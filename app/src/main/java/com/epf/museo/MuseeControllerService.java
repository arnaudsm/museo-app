package com.epf.museo;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MuseeControllerService {
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://vps449928.ovh.net/api/musees/5c637e3c61e55c808b31e1ae12a57fc5c4842b4b")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Musees Controller
    @GET("/api/musees/{id]")
    List<Musee> getMusee(@Path("id") String id);

}

