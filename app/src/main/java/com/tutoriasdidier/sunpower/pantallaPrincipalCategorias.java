package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class pantallaPrincipalCategorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_categorias);

        Button exitButton = findViewById(R.id.button5);
        ImageButton estadisticasButton = findViewById(R.id.imageButton3);
        ImageButton monitoreoButton = findViewById(R.id.imageButton4);

        // Listener para salir a la pantalla de login
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pantallaPrincipalCategorias.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Listener para ir a la pantalla de estadísticas
        estadisticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pantallaPrincipalCategorias.this, estadisticas.class);
                startActivity(intent);
            }
        });

        // Listener para ir a la pantalla de monitoreo (consejos)
        monitoreoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pantallaPrincipalCategorias.this, consejos.class);
                startActivity(intent);
            }
        });
    }
}
