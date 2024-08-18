package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class consejos extends AppCompatActivity {

    private ImageButton registroButton, estadisticasButton;
    private TextView consejo;
    private Button siguiente, anterior, exitButton;
    private TableLayout tableLayout;

    private String[] tips = {
            "Ahorra energía apagando las luces que no necesitas.",
            "Desconecta los electrodomésticos que no estás usando.",
            "Utiliza bombillas LED para reducir el consumo de energía.",
            "Mira cuál es tu excedente de energía solar.",
            "Aprovecha la luz natural durante el día.",
            "Incluye baterías a tu instalación fotovoltaica.",
            "Programa tus electrodomésticos y dispositivos para que funcionen durante las horas de mayor generación solar.",
            "Posición y orientación adecuadas. La ubicación y orientación de tus paneles solares son fundamentales para su rendimiento óptimo.",
            "Revisa tus aparatos eléctricos para evitar fugas de energía.",
            "Limpieza regular. Una buena limpieza permite un panel óptimo.",
            "Busca un buen ángulo para que la luz llegue directamente",
            "Una mala instalación, configuración incorrecta, fallo eléctrico o mecánico puede disminuir la eficiencia y rendimiento de este sistema.",
            "Al limpiar los paneles solares, no se aconseja usar productos químicos.",
            "Limpia los paneles con agua tibia de forma cuidadosa, si utilizas algún producto que sea uno que no deje restos."
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos);
        initializeUI();
        setupListeners();
        updateTable();
    }

    private void initializeTableHeaders() {
        TableRow headerRow = new TableRow(this);

        TextView headerNombre = new TextView(this);
        headerNombre.setText("Nombre");
        headerNombre.setBackgroundColor(Color.WHITE);
        headerNombre.setGravity(Gravity.CENTER); // Centra el texto
        headerNombre.setPadding(8, 8, 8, 8);
        headerRow.addView(headerNombre);

        TextView headerFecha = new TextView(this);
        headerFecha.setText("Fecha");
        headerFecha.setBackgroundColor(Color.WHITE);
        headerFecha.setGravity(Gravity.CENTER); // Centra el texto
        headerFecha.setPadding(8, 8, 8, 8);
        headerRow.addView(headerFecha);

        TextView headerNumeroPaneles = new TextView(this);
        headerNumeroPaneles.setText("#Celdas");
        headerNumeroPaneles.setBackgroundColor(Color.WHITE);
        headerNumeroPaneles.setGravity(Gravity.CENTER); // Centra el texto
        headerNumeroPaneles.setPadding(8, 8, 8, 8);
        headerRow.addView(headerNumeroPaneles);

        TextView headerEnergiaProducida = new TextView(this);
        headerEnergiaProducida.setText("Producción");
        headerEnergiaProducida.setBackgroundColor(Color.WHITE);
        headerEnergiaProducida.setGravity(Gravity.CENTER); // Centra el texto
        headerEnergiaProducida.setPadding(8, 8, 8, 8);
        headerRow.addView(headerEnergiaProducida);

        TextView headerEnergiaConsumida = new TextView(this);
        headerEnergiaConsumida.setText("Wh/p>");
        headerEnergiaConsumida.setBackgroundColor(Color.WHITE);
        headerEnergiaConsumida.setGravity(Gravity.CENTER); // Centra el texto
        headerEnergiaConsumida.setPadding(8, 8, 8, 8);
        headerRow.addView(headerEnergiaConsumida);

        TextView headerBalanceEnergetico = new TextView(this);
        headerBalanceEnergetico.setText("kW/h");
        headerBalanceEnergetico.setBackgroundColor(Color.WHITE);
        headerBalanceEnergetico.setGravity(Gravity.CENTER); // Centra el texto
        headerBalanceEnergetico.setPadding(8, 8, 8, 8);
        headerRow.addView(headerBalanceEnergetico);

        tableLayout.addView(headerRow, 0); // Agrega los encabezados en la primera fila
    }


    private void initializeUI() {
        exitButton = findViewById(R.id.button5);
        registroButton = findViewById(R.id.imageButton2);
        estadisticasButton = findViewById(R.id.imageButton);
        siguiente = findViewById(R.id.button7);
        anterior = findViewById(R.id.button6);
        consejo = findViewById(R.id.textView);
        tableLayout = findViewById(R.id.tableLayoutConsejos);

        consejo.setText(tips[currentIndex]);
    }

    private void setupListeners() {
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(consejos.this, login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        registroButton.setOnClickListener(v -> {
            Intent intent = new Intent(consejos.this, pantallaPrincipalCategorias.class);
            startActivity(intent);
        });

        estadisticasButton.setOnClickListener(v -> {
            Intent intent = new Intent(consejos.this, estadisticas.class);
            startActivity(intent);
        });

        siguiente.setOnClickListener(v -> {
            if (currentIndex < tips.length - 1) {
                currentIndex++;
                consejo.setText(tips[currentIndex]);
            }
        });

        anterior.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                consejo.setText(tips[currentIndex]);
            }
        });
    }

    private void updateTable() {
        tableLayout.removeAllViews(); // Limpia la tabla antes de agregar nuevas filas
        initializeTableHeaders(); // Agrega encabezados

        DataManager dataManager = new DataManager(this);
        List<Terraza> terraceDataList = dataManager.getTerraceData();

        // Ordena los datos de la lista por nombre de terraza (si fuera necesario)
        // Puedes ordenar por cualquier otro criterio utilizando un Comparator
        Collections.sort(terraceDataList, Comparator.comparing(Terraza::getNombre));

        for (Terraza terraza : terraceDataList) {
            TableRow row = new TableRow(this);

            // Agrega los datos de la terraza a la fila de la tabla
            TextView textViewNombre = new TextView(this);
            textViewNombre.setText(terraza.getNombre());
            textViewNombre.setTextColor(Color.BLACK);
            textViewNombre.setGravity(Gravity.CENTER); // Centra el texto
            textViewNombre.setPadding(8, 8, 8, 8);
            row.addView(textViewNombre);

            TextView textViewFecha = new TextView(this);
            textViewFecha.setText(terraza.getFechaProduccion());
            textViewFecha.setTextColor(Color.BLACK);
            textViewFecha.setGravity(Gravity.CENTER); // Centra el texto
            textViewFecha.setPadding(8, 8, 8, 8);
            row.addView(textViewFecha);

            TextView textViewNumeroPaneles = new TextView(this);
            textViewNumeroPaneles.setText(String.valueOf(terraza.getNumeroPaneles()));
            textViewNumeroPaneles.setTextColor(Color.BLACK);
            textViewNumeroPaneles.setGravity(Gravity.CENTER); // Centra el texto
            textViewNumeroPaneles.setPadding(8, 8, 8, 8);
            row.addView(textViewNumeroPaneles);

            TextView textViewEnergiaProducida = new TextView(this);
            textViewEnergiaProducida.setText(String.valueOf(terraza.getEnergiaProducida()));
            textViewEnergiaProducida.setTextColor(Color.BLACK);
            textViewEnergiaProducida.setGravity(Gravity.CENTER); // Centra el texto
            textViewEnergiaProducida.setPadding(8, 8, 8, 8);
            row.addView(textViewEnergiaProducida);

            TextView textViewEnergiaConsumida = new TextView(this);
            textViewEnergiaConsumida.setText(String.valueOf(terraza.getEnergiaConsumida()));
            textViewEnergiaConsumida.setTextColor(Color.BLACK);
            textViewEnergiaConsumida.setGravity(Gravity.CENTER); // Centra el texto
            textViewEnergiaConsumida.setPadding(8, 8, 8, 8);
            row.addView(textViewEnergiaConsumida);

            // Calcular el balance energético
            double balanceEnergetico = terraza.getEnergiaProducida() - terraza.getEnergiaConsumida();

            TextView textViewBalanceEnergetico = new TextView(this);
            textViewBalanceEnergetico.setText(String.valueOf(balanceEnergetico));
            textViewBalanceEnergetico.setTextColor(Color.BLACK);
            textViewBalanceEnergetico.setGravity(Gravity.CENTER); // Centra el texto
            textViewBalanceEnergetico.setPadding(8, 8, 8, 8);
            row.addView(textViewBalanceEnergetico);

            // Log para depuración
            Log.d("ConsejosActivity", "Añadiendo datos de terraza: " + terraza.getNombre());
            tableLayout.addView(row); // Añade la fila a la tabla
        }
    }

}
