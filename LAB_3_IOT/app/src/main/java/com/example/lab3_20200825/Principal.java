package com.example.lab3_20200825;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Principal extends AppCompatActivity {
    private TextInputLayout textInputLayoutPeliculaId;
    private TextInputEditText editTextPeliculaId;
    private Button buttonVisualizar;
    private Button buttonBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        EdgeToEdge.enable(this);
        textInputLayoutPeliculaId = findViewById(R.id.textInputLayout);
        editTextPeliculaId = (TextInputEditText) textInputLayoutPeliculaId.getEditText();
        buttonVisualizar = findViewById(R.id.button);
        buttonBuscar = findViewById(R.id.button2);

        buttonVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se hace uso de un Intent para iniciar la actividad de visualización
                Intent intent = new Intent(Principal.this, Visualizar.class);
                startActivity(intent);
            }
        });
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String peliculaId = editTextPeliculaId.getText().toString().trim();
                if (!peliculaId.isEmpty()) {
                    // Intent para iniciar la actividad de búsqueda
                    Intent intent = new Intent(Principal.this, Buscar.class);
                    // Pasamos el ID a la actividad de búsqueda
                    intent.putExtra("PELICULA_ID", peliculaId);
                    startActivity(intent);
                } else {
                    // Mostrar un error si no se ingresó un ID
                    textInputLayoutPeliculaId.setError("Por favor, ingresa un ID de película válido.");
                }
            }
        });
    }


}