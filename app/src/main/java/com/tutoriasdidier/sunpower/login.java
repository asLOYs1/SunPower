package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private EditText l_correo,l_contra;
    private Button  ingresar;
    private TextView registrar;
    private UserManager userManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registrar = findViewById(R.id.registerTextView);
        l_correo = findViewById(R.id.correo);
        l_contra = findViewById(R.id.editTextText);
        ingresar = findViewById(R.id.loginButton);

        userManager= new UserManager(this);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, RegistroUsuario.class);
                startActivity(intent);
            }
        });

        // Add logic for login authentication here
        // If login is successful, navigate to pantallaPrincipalCategorias
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement login authentication
                String correo = l_correo.getText().toString();
                String contra = l_contra.getText().toString();
                // If successful:
                if(userManager.LoginUser(correo,contra)){
                    Intent intent = new Intent(login.this, pantallaPrincipalCategorias.class);
                    startActivity(intent);
                    finish();
                // Optionally, finish the current activity to prevent going back to login
                }else {
                    Toast.makeText(login.this,"Correo o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
