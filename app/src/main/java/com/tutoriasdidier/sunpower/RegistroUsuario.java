package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        Button registerButton = findViewById(R.id.button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement registration logic
                // If registration is successful:
                Intent intent = new Intent(RegistroUsuario.this, login.class);
                startActivity(intent);
                // Optionally, finish the current activity to prevent going back to registration
                finish();
            }
        });
    }
}
