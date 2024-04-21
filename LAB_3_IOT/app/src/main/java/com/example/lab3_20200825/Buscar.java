package com.example.lab3_20200825;
import com.example.lab3_20200825.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buscar extends AppCompatActivity {
    private CheckBox checkBoxConforme;
    private Button buttonRegresar;
    private TextView textViewTitulo;
    private AutoCompleteTextView autoCompleteDirector, autoCompleteActores, autoCompleteFechaEstreno,
            autoCompleteGeneros, autoCompleteEscritores, autoCompleteTrama, autoCompleteIMDBRating,
            autoCompleteRottenTomatoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        // Inicializando los AutoCompleteTextView
        textViewTitulo = findViewById(R.id.textView4);
        autoCompleteDirector = findViewById(R.id.autoCompleteTextView3);
        autoCompleteActores = findViewById(R.id.autoCompleteTextView4);
        autoCompleteFechaEstreno = findViewById(R.id.autoCompleteTextView5);
        autoCompleteGeneros = findViewById(R.id.autoCompleteTextView6);
        autoCompleteEscritores = findViewById(R.id.autoCompleteTextView8);
        autoCompleteTrama = findViewById(R.id.autoCompleteTextView);
        autoCompleteIMDBRating = findViewById(R.id.autoCompleteTextView9);
        autoCompleteRottenTomatoes = findViewById(R.id.autoCompleteTextView10);
        checkBoxConforme = findViewById(R.id.checkBox);
        buttonRegresar = findViewById(R.id.button3);



        // Inicialmente el botón está deshabilitado
        buttonRegresar.setEnabled(false);

        //  Para el CheckBox
        checkBoxConforme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Habilita o deshabilita el botón Regresar según si el CheckBox está marcado
            buttonRegresar.setEnabled(isChecked);
        });

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Al hacer clic en el botón, regresa a la actividad principal
                Intent intent = new Intent(Buscar.this, Principal.class);
                // La siguiente línea asegura que se limpie la pila de actividades y se comience de nuevo en Principal.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // Cierra la actividad actual
                finish();
            }
        });

        String peliculaId = getIntent().getStringExtra("PELICULA_ID");
        if (peliculaId != null && !peliculaId.isEmpty()) {
            buscarPelicula(peliculaId);
        }
    }


    private void buscarPelicula(String peliculaId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDbApiService apiService = retrofit.create(OMDbApiService.class);

        Call<Pelicula> call = apiService.obtenerPeliculaPorId("bf81d461", peliculaId);

        call.enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Pelicula pelicula = response.body();
                    // Actualiza la UI con los datos de la película
                    textViewTitulo.setText(pelicula.getTitle());
                    autoCompleteDirector.setText(pelicula.getDirector());
                    autoCompleteActores.setText(pelicula.getActors());
                    autoCompleteFechaEstreno.setText(pelicula.getReleased());
                    autoCompleteGeneros.setText(pelicula.getGenre());
                    autoCompleteEscritores.setText(pelicula.getWriter());
                    autoCompleteTrama.setText(pelicula.getPlot());

                    if (pelicula.getRatings() != null) {
                        for (Pelicula.Rating rating : pelicula.getRatings()) {
                            switch (rating.getSource()) {
                                case "Internet Movie Database":
                                    autoCompleteIMDBRating.setText(rating.getValue());
                                    break;
                                case "Rotten Tomatoes":
                                    autoCompleteRottenTomatoes.setText(rating.getValue());
                                    break;

                            }
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {
               
            }
        });

    }
}
