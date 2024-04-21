package com.example.lab3_20200825;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Visualizar extends AppCompatActivity {

    private TextView textViewPrimos;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        textViewPrimos = findViewById(R.id.textView16); // Asegúrate de que este es el ID correcto

        if (isConnectedToInternet()) {
            obtenerNumerosPrimos();
        } else {
            Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerNumerosPrimos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PrimeNumberService service = retrofit.create(PrimeNumberService.class);
        Call<List<Integer>> call = service.getPrimeNumbers();

        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> primeNumbers = response.body();
                    updateUI(primeNumbers);
                } else {
                    Toast.makeText(Visualizar.this, "Respuesta no exitosa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Toast.makeText(Visualizar.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(List<Integer> primeNumbers) {
        StringBuilder sb = new StringBuilder();
        for (Integer prime : primeNumbers) {
            sb.append(prime).append(", ");
        }
        textViewPrimos.setText(sb.toString());
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
