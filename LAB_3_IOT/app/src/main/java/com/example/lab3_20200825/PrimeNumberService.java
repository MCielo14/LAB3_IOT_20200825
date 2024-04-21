package com.example.lab3_20200825;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrimeNumberService {

        @GET("/primeNumbers?len=999&order=1")
        Call<List<Integer>> getPrimeNumbers();

}
