package com.example.lab3_20200825;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Visualizar extends AppCompatActivity {

    private TextView textViewPrimos;
    private int currentPrimeIndex = 0;
    private List<Integer> primeNumbersList;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updatePrimeRunnable;
    private Button buttonToggleDirection;
    private Button buttonPauseResume;
    private boolean isAscending = true;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        textViewPrimos = findViewById(R.id.textView16);
        buttonToggleDirection = findViewById(R.id.button4);
        buttonPauseResume = findViewById(R.id.button5);
        primeNumbersList = new ArrayList<>();

        setupUpdatePrimeRunnable();

        buttonToggleDirection.setText(isAscending ? "Descender" : "Ascender");
        buttonToggleDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarDireccion();
            }
        });

        buttonPauseResume.setText(isPaused ? "Reiniciar" : "Pausar");
        buttonPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePauseResume();
            }
        });

        if (isConnectedToInternet()) {
            obtenerNumerosPrimos();
        } else {
            Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupUpdatePrimeRunnable() {
        updatePrimeRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused && primeNumbersList != null && !primeNumbersList.isEmpty()) {
                    textViewPrimos.setText("Su primo es: " + primeNumbersList.get(currentPrimeIndex));
                    currentPrimeIndex = isAscending ?
                            (currentPrimeIndex + 1) % primeNumbersList.size() :
                            (currentPrimeIndex - 1 + primeNumbersList.size()) % primeNumbersList.size();
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void cambiarDireccion() {
        isAscending = !isAscending;
        buttonToggleDirection.setText(isAscending ? "Descender" : "Ascender");
        Toast.makeText(this, "Dirección cambiada a " + (isAscending ? "ascendente" : "descendente"), Toast.LENGTH_SHORT).show();
    }

    private void togglePauseResume() {
        isPaused = !isPaused;
        if (!isPaused) {
            handler.post(updatePrimeRunnable);
            buttonPauseResume.setText("Pausar");
        } else {
            handler.removeCallbacks(updatePrimeRunnable);
            buttonPauseResume.setText("Reiniciar");
        }
    }

    private void obtenerNumerosPrimos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PrimeNumberService service = retrofit.create(PrimeNumberService.class);
        Call<List<PrimeNumber>> call = service.getPrimeNumbers(999, 1);


        call.enqueue(new Callback<List<PrimeNumber>>() {
            @Override
            public void onResponse(Call<List<PrimeNumber>> call, Response<List<PrimeNumber>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PrimeNumber> primeNumbers = response.body();

                    currentPrimeIndex = 0;
                    mostrarPrimos(primeNumbers);
                } else {
                    Toast.makeText(Visualizar.this, "Respuesta no exitosa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PrimeNumber>> call, Throwable t) {
                Toast.makeText(Visualizar.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void mostrarPrimos(List<PrimeNumber> primeNumbers) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (currentPrimeIndex < primeNumbers.size()) {
                    PrimeNumber currentPrime = primeNumbers.get(currentPrimeIndex++);
                    textViewPrimos.setText(String.format("Su primo es: %d", currentPrime.getNumber()));
                    handler.postDelayed(this, 1000);
                } else {
                    // Reiniciar si llegamos al final de la lista
                    currentPrimeIndex = 0;
                }
            }
        });
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
