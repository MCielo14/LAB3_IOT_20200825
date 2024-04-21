package com.example.lab3_20200825;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
    private List<Integer> primeNumbersList; // Asumiendo que tienes una lista de enteros
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
        buttonToggleDirection = findViewById(R.id.button4); // Asegúrate de que este es el ID correcto en tu layout
        buttonPauseResume = findViewById(R.id.button5);
        updatePrimeRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused && primeNumbersList != null && !primeNumbersList.isEmpty()) {
                    textViewPrimos.setText("Su primo es: " + primeNumbersList.get(currentPrimeIndex));
                    if (isAscending) {
                        currentPrimeIndex = (currentPrimeIndex + 1) % primeNumbersList.size();
                    } else {
                        if (currentPrimeIndex == 0) {
                            currentPrimeIndex = primeNumbersList.size() - 1;
                        } else {
                            currentPrimeIndex = (currentPrimeIndex - 1) % primeNumbersList.size();
                        }
                    }
                    handler.postDelayed(this, 1000);
                }
            }
        };
        primeNumbersList = new ArrayList<>(); // Inicialización de la lista
        Log.d("Visualizar", "Dirección cambiada. Es ascendente: " + isAscending);
        buttonToggleDirection.setText(isAscending ? "Descender" : "Ascender");

        // Establecer OnClickListener para el botón
        buttonToggleDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar la dirección
                cambiarDireccion();
            }
        });
        buttonPauseResume.setText(isPaused ? "Reiniciar" : "Pausar");
        buttonPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused = !isPaused;
                buttonPauseResume.setText(isPaused ? "Reiniciar" : "Pausar");
                if (!isPaused) {
                    // Reinicia la actualización de los números primos
                    handler.post(updatePrimeRunnable);
                }
            }
        });

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
        Call<List<PrimeNumber>> call = service.getPrimeNumbers(999, 1);

        // Callback para Retrofit
        call.enqueue(new Callback<List<PrimeNumber>>() {
            @Override
            public void onResponse(Call<List<PrimeNumber>> call, Response<List<PrimeNumber>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PrimeNumber> primeNumbers = response.body();
                    // Aquí manejas la lista de objetos PrimeNumber
                    // Ejemplo: comenzar a mostrar los números primos uno por uno
                    currentPrimeIndex = 0;
                    mostrarPrimos(primeNumbers); // Llamar al método que muestra los primos
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
                    currentPrimeIndex = 0; // Reiniciar si llegamos al final de la lista
                }
            }
        });
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null); // Cancelar todos los callbacks y mensajes
        super.onDestroy();
    }
    private void cambiarDireccion() {
        // Cambiar el valor de la dirección
        isAscending = !isAscending;
        // Actualizar el mensaje mostrado
        String direction = isAscending ? "ascendente" : "descendente";
        // Mostrar el mensaje con la nueva dirección
        Toast.makeText(this, "Dirección cambiada. Ahora es " + direction, Toast.LENGTH_SHORT).show();
        // Asegurarse de que el índice esté correctamente posicionado
        buttonToggleDirection.setText(isAscending ? "Descender" : "Ascender");
    }



}
