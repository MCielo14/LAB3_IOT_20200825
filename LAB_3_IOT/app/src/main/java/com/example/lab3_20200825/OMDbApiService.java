package com.example.lab3_20200825;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OMDbApiService {
    @GET("/")
    Call<Pelicula> obtenerPeliculaPorId(
            @Query("apikey") String apikey,
            @Query("i") String imdbId
    );
}

