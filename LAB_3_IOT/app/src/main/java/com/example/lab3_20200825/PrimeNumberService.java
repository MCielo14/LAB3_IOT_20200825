package com.example.lab3_20200825;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrimeNumberService {
        @GET("/primeNumbers")
        Call<List<PrimeNumber>> getPrimeNumbers(@Query("len") int length, @Query("order") int order);
}


