package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class consejos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos);
        Button exitButton = findViewById(R.id.button5);
        ImageButton registroButton = findViewById(R.id.imageButton2);
        ImageButton estadisticasButton = findViewById(R.id.imageButton);

        // Listener para salir a la pantalla de login
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consejos.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        // Listener para ir a la pantalla de registro
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consejos.this, pantallaPrincipalCategorias.class);
                startActivity(intent);
            }
        });
        // Listener para ir a la pantalla de monitoreo
        estadisticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(consejos.this, estadisticas.class);
                startActivity(intent);
            }
        });
    }
}