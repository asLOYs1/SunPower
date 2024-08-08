package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class RegistroUsuario extends AppCompatActivity {

    private Button registrar;
    private EditText Editnombre, Editcorreo, Editcontrasena, EditconfirContra, Editcelular;
    private UserManager userManager;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);


        Editnombre = findViewById(R.id.editTextText);
        Editcorreo = findViewById(R.id.editTextTextEmailAddress);
        Editcontrasena = findViewById(R.id.editTextTextPassword);
        EditconfirContra = findViewById(R.id.editTextTextPassword2);
        Editcelular = findViewById(R.id.editTextPhone);
        registrar = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkBox);

        userManager = new UserManager(this);  // Inicializar UserManager

        // Agregar un listener para el CheckBox
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Habilitar o deshabilitar el botón según el estado del CheckBox
            registrar.setEnabled(isChecked);
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = Editnombre.getText().toString();
                String correo = Editcorreo.getText().toString();
                String contra = Editcontrasena.getText().toString();
                String contra2 = EditconfirContra.getText().toString();
                String celular = Editcelular.getText().toString();

                if (TextUtils.isEmpty(nombre)) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa nombre", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(RegistroUsuario.this, "Ingresa un correo", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(correo)) {
                    Toast.makeText(RegistroUsuario.this, "ingrese un correo valido", Toast.LENGTH_SHORT).show();
                } else if (!contra.equals(contra2)) {
                    Toast.makeText(RegistroUsuario.this, "las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(celular)) {
                    Toast.makeText(RegistroUsuario.this, "ingrese un numero celular", Toast.LENGTH_SHORT).show();
                } else {
                    CrearUsuario(nombre, correo, contra, celular);
                    Intent intent = new Intent(RegistroUsuario.this, login.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void CrearUsuario(String nombre, String correo, String contra, String celular) {
        userManager.RegisterUser(nombre, correo, contra, celular);
        Toast.makeText(RegistroUsuario.this, "registro exitoso", Toast.LENGTH_SHORT).show();
        finish();
    }
}