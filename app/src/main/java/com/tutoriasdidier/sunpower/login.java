package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerTextView = findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });

        // Add logic for login authentication here
        // If login is successful, navigate to pantallaPrincipalCategorias
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement login authentication
                // If successful:
                Intent intent = new Intent(login.this, pantallaPrincipalCategorias.class);
                startActivity(intent);
                // Optionally, finish the current activity to prevent going back to login
                finish();
            }
        });
    }
}
