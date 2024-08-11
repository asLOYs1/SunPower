package com.tutoriasdidier.sunpower;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class pantallaPrincipalCategorias extends AppCompatActivity {

    // Nuevas variables para la funcionalidad de terrazas
    private EditText nombreTerrazaEditText;
    private EditText fechaProduccionEditText;
    private EditText energiaProducidaEditText;
    private EditText energiaConsumidaEditText;
    private EditText numeroPanelesEditText;
    private ImageView terrazaImageView1;
    private ImageView terrazaImageView2;
    private ImageView terrazaImageView3;
    private Button ingresarButton;
    private Button borrarButton;
    private TextView balanceEnergeticoTextView;
    private String nombreTerraza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_categorias);

        // Inicializar nuevos campos
        nombreTerrazaEditText = findViewById(R.id.editTextText5);
        fechaProduccionEditText = findViewById(R.id.editTextDate2);
        energiaProducidaEditText = findViewById(R.id.editTextText4);
        energiaConsumidaEditText = findViewById(R.id.editTextText3);
        numeroPanelesEditText = findViewById(R.id.editTextText2);
        terrazaImageView1 = findViewById(R.id.imageView4);
        terrazaImageView2 = findViewById(R.id.imageView5);
        terrazaImageView3 = findViewById(R.id.imageView6);
        ingresarButton = findViewById(R.id.button6);
        borrarButton = findViewById(R.id.button7);
        balanceEnergeticoTextView = findViewById(R.id.textView10);

        // Cargar datos de la terraza si ya están guardados
        String nombreTerraza = nombreTerrazaEditText.getText().toString();
        if (!nombreTerraza.isEmpty()) {
            Terraza terraza = cargarTerraza(nombreTerraza);
            if (terraza != null) {
                mostrarDatosTerraza(terraza);
            }
        }

        // Listener para guardar los datos al presionar el botón "Ingresar"
        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Terraza terraza = obtenerDatosTerraza();
                guardarTerraza(terraza);
                mostrarConfirmacionYActualizarUI(terraza);
            }
        });
        // Listener para borrar la última terraza registrada
        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarUltimaTerraza();
            }
        });

        // Botones ya existentes
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
    private void borrarUltimaTerraza() {
        SharedPreferences sharedPreferences = getSharedPreferences("TerrazasData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Obtener todas las llaves
        Map<String, ?> allEntries = sharedPreferences.getAll();
        ArrayList<String> terrazaNames = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains("_fecha")) {
                String terrazaName = entry.getKey().split("_fecha")[0];
                if (!terrazaNames.contains(terrazaName)) {
                    terrazaNames.add(terrazaName);
                }
            }
        }

        if (!terrazaNames.isEmpty()) {
            // Ordenar en orden descendente para borrar el último
            Collections.sort(terrazaNames, Collections.reverseOrder());

            // Tomar la última terraza
            String lastTerrazaName = terrazaNames.get(0);

            // Mostrar detalles de la terraza a borrar
            Terraza terraza = cargarTerraza(lastTerrazaName);
            if (terraza != null) {
                mostrarDatosTerraza(terraza);
                // Confirmar borrado
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmar borrado")
                        .setMessage("¿Está seguro de que desea borrar la terraza " + lastTerrazaName + "?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Borrar la terraza
                                editor.remove(lastTerrazaName + "_fecha");
                                editor.remove(lastTerrazaName + "_energiaProducida");
                                editor.remove(lastTerrazaName + "_energiaConsumida");
                                editor.remove(lastTerrazaName + "_numeroPaneles");
                                editor.apply();

                                // Limpiar la UI
                                nombreTerrazaEditText.setText("");
                                fechaProduccionEditText.setText("");
                                energiaProducidaEditText.setText("");
                                energiaConsumidaEditText.setText("");
                                numeroPanelesEditText.setText("");
                                terrazaImageView1.setVisibility(View.GONE);
                                terrazaImageView2.setVisibility(View.GONE);
                                terrazaImageView3.setVisibility(View.GONE);
                                balanceEnergeticoTextView.setText("kW/h");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else {
                // No hay terrazas para borrar
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No hay terrazas")
                        .setMessage("No hay terrazas registradas para borrar.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }



    private void guardarTerraza(Terraza terraza) {
        SharedPreferences sharedPreferences = getSharedPreferences("TerrazasData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Serializar datos de la terraza
        editor.putString(terraza.getNombre() + "_fecha", terraza.getFechaProduccion());
        editor.putFloat(terraza.getNombre() + "_energiaProducida", (float) terraza.getEnergiaProducida());
        editor.putFloat(terraza.getNombre() + "_energiaConsumida", (float) terraza.getEnergiaConsumida());
        editor.putInt(terraza.getNombre() + "_numeroPaneles", terraza.getNumeroPaneles());

        editor.apply();
    }

    private Terraza cargarTerraza(String nombre) {
        SharedPreferences sharedPreferences = getSharedPreferences("TerrazasData", MODE_PRIVATE);

        String fechaProduccion = sharedPreferences.getString(nombre + "_fecha", "");
        double energiaProducida = sharedPreferences.getFloat(nombre + "_energiaProducida", 0);
        double energiaConsumida = sharedPreferences.getFloat(nombre + "_energiaConsumida", 0);
        int numeroPaneles = sharedPreferences.getInt(nombre + "_numeroPaneles", 0);

        return new Terraza(nombre, fechaProduccion, energiaProducida, energiaConsumida, numeroPaneles);
    }

    private Terraza obtenerDatosTerraza() {
        String nombre = nombreTerrazaEditText.getText().toString();
        String fecha = fechaProduccionEditText.getText().toString();
        double energiaProducida = Double.parseDouble(energiaProducidaEditText.getText().toString());
        double energiaConsumida = Double.parseDouble(energiaConsumidaEditText.getText().toString());
        int numeroPaneles = Integer.parseInt(numeroPanelesEditText.getText().toString());

        return new Terraza(nombre, fecha, energiaProducida, energiaConsumida, numeroPaneles);
    }

    private void mostrarDatosTerraza(Terraza terraza) {
        nombreTerrazaEditText.setText(terraza.getNombre());
        fechaProduccionEditText.setText(terraza.getFechaProduccion());
        energiaProducidaEditText.setText(String.valueOf(terraza.getEnergiaProducida()));
        energiaConsumidaEditText.setText(String.valueOf(terraza.getEnergiaConsumida()));
        numeroPanelesEditText.setText(String.valueOf(terraza.getNumeroPaneles()));

        // Mostrar imagen basada en el nombre de la terraza
        if (terraza.getNombre().equalsIgnoreCase("Terraza 1")) {
            terrazaImageView1.setVisibility(View.VISIBLE);
            terrazaImageView1.setImageResource(R.drawable.terraza1);
            terrazaImageView2.setVisibility(View.GONE);
            terrazaImageView3.setVisibility(View.GONE);
        } else if (terraza.getNombre().equalsIgnoreCase("Terraza 2")) {
            terrazaImageView1.setVisibility(View.GONE);
            terrazaImageView2.setVisibility(View.VISIBLE);
            terrazaImageView2.setImageResource(R.drawable.terraza2);
            terrazaImageView3.setVisibility(View.GONE);
        } else if (terraza.getNombre().equalsIgnoreCase("Terraza 3")) {
            terrazaImageView1.setVisibility(View.GONE);
            terrazaImageView2.setVisibility(View.GONE);
            terrazaImageView3.setVisibility(View.VISIBLE);
            terrazaImageView3.setImageResource(R.drawable.terraza3);
        } else {
            terrazaImageView1.setVisibility(View.GONE);
            terrazaImageView2.setVisibility(View.GONE);
            terrazaImageView3.setVisibility(View.GONE);
        }
    }

    private void mostrarConfirmacionYActualizarUI(Terraza terraza) {
        // Mostrar mensaje de confirmación
        Toast.makeText(this, "Datos guardados con éxito", Toast.LENGTH_SHORT).show();

        // Actualizar UI con la imagen y el balance energético
        mostrarDatosTerraza(terraza);

        // Calcular y mostrar el balance energético
        double balanceEnergetico = terraza.getEnergiaProducida() - terraza.getEnergiaConsumida();
        balanceEnergeticoTextView.setText(String.format("Balance Energético: %.2f kWh", balanceEnergetico));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Obtener datos de los campos
        String nombreTerraza = nombreTerrazaEditText.getText().toString();
        String fechaProduccion = fechaProduccionEditText.getText().toString();
        String energiaProducida = energiaProducidaEditText.getText().toString();
        String energiaConsumida = energiaConsumidaEditText.getText().toString();
        String numeroPaneles = numeroPanelesEditText.getText().toString();

        // Guardar los datos en el Bundle
        outState.putString("nombreTerraza", nombreTerraza);
        outState.putString("fechaProduccion", fechaProduccion);
        outState.putString("energiaProducida", energiaProducida);
        outState.putString("energiaConsumida", energiaConsumida);
        outState.putString("numeroPaneles", numeroPaneles);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            // Recuperar los datos del Bundle
            String nombreTerraza = savedInstanceState.getString("nombreTerraza", "");
            String fechaProduccion = savedInstanceState.getString("fechaProduccion", "");
            String energiaProducida = savedInstanceState.getString("energiaProducida", "");
            String energiaConsumida = savedInstanceState.getString("energiaConsumida", "");
            String numeroPaneles = savedInstanceState.getString("numeroPaneles", "");

            // Crear una instancia de Terraza y actualizar la UI
            Terraza terraza = new Terraza(nombreTerraza, fechaProduccion,
                    Double.parseDouble(energiaProducida),
                    Double.parseDouble(energiaConsumida),
                    Integer.parseInt(numeroPaneles));

            // Mostrar los datos de la terraza
            mostrarDatosTerraza(terraza);

            // Calcular y mostrar el balance energético
            double balanceEnergetico = terraza.getEnergiaProducida() - terraza.getEnergiaConsumida();
            balanceEnergeticoTextView.setText(String.format("Balance Energético: %.2f kWh", balanceEnergetico));
        }
    }

}