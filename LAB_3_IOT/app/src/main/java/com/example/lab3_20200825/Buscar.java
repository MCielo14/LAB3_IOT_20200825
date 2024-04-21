package com.example.lab3_20200825;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buscar extends AppCompatActivity {
    private EditText editTextImdbId;
    private Button buttonBuscar;
    String peliculaId = getIntent().getStringExtra("PELICULA_ID");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        editTextImdbId = findViewById(R.id.editTextImdbId);
        buttonBuscar = findViewById(R.id.buttonBuscar);

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imdbId = editTextImdbId.getText().toString();
                buscarPelicula(imdbId);
            }
        });
    }

    private void buscarPelicula(String peliculaId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDbApiService apiService = retrofit.create(OMDbApiService.class);
        Call<Pelicula> call = apiService.obtenerPeliculaPorId("tu_api_key", peliculaId);

        call.enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                if (response.isSuccessful()) {
                    Pelicula pelicula = response.body();
                    // Ahora puedes hacer algo con los datos de la película, por ejemplo, iniciar una nueva actividad para mostrarlos
                } else {
                    // Maneja la situación de error, por ejemplo, cuando el ID de IMDb no es válido
                }
            }

            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {
                // Maneja el error de red o la excepción lanzada durante la solicitud
            }
        });
    }
}