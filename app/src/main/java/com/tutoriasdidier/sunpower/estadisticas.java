package com.tutoriasdidier.sunpower;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class estadisticas extends AppCompatActivity {

    // Variables para almacenar los valores calculados
    private double consumoTotal = 0;
    private double produccionTotal = 0;
    private int numeroTerrazas = 0;
    private double energiaObtenida = 0;
    private double impactoAmbiental = 0;
    private double porcentajeEnergiaSolar = 0;
    private int diasInferiores = 0;
    private double consumoTotalSemanal = 0;
    private double produccionTotalSemanal = 0;
    private StringBuilder fechasDiasInferiores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        // Inicialización de botones y textviews
        Button exitButton = findViewById(R.id.button5);
        ImageButton registroButton = findViewById(R.id.imageButton2);
        ImageButton monitoreoButton = findViewById(R.id.imageButton7);

        TextView tvConsumo = findViewById(R.id.Tv_consumodos);
        TextView tvNumero = findViewById(R.id.Tv_numerodos);
        TextView tvEnergia = findViewById(R.id.Tv_energiados);
        TextView tvImpacto = findViewById(R.id.Tv_impactodos);
        TextView tvPorcentaje = findViewById(R.id.Tv_porcentajedos);
        TextView tvDias = findViewById(R.id.Tv_producciondos);

        // Obtener los datos de las terrazas registradas
        List<Terraza> terrazas = obtenerDatosDeLasTerrazas();

        // Instanciar DataManager
        DataManager dataManager = new DataManager(this);
        fechasDiasInferiores = new StringBuilder();

        // Calcular los valores basados en las terrazas registradas
        for (Terraza terraza : terrazas) {
            consumoTotal += terraza.getEnergiaConsumida();
            produccionTotal += terraza.getEnergiaProducida();
            numeroTerrazas++;
            energiaObtenida += terraza.getEnergiaProducida();
            impactoAmbiental += calcularImpactoAmbiental(terraza.getEnergiaProducida());
            porcentajeEnergiaSolar += calcularPorcentajeEnergiaSolar(terraza);
        }

        // Calcular los valores semanales y las fechas con producción inferior al promedio
        for (Terraza terraza : terrazas) {
            produccionTotalSemanal += dataManager.calcularEnergiaSemanal(terraza.getNombre(), true);
            consumoTotalSemanal += dataManager.calcularEnergiaSemanal(terraza.getNombre(), false);
            double promedioProduccion = dataManager.obtenerPromedioProduccionSemanal(terraza.getNombre());
            List<String> diasInferioresList = dataManager.obtenerDiasInferioresAlPromedio(terraza.getNombre(), promedioProduccion);

            diasInferiores += diasInferioresList.size();
            for (String fecha : diasInferioresList) {
                fechasDiasInferiores.append(fecha).append("\n");
            }
        }

        // Asignar los valores calculados a los TextView
        tvConsumo.setText(String.format("%.2f kWh", consumoTotal));
        tvNumero.setText(String.valueOf(numeroTerrazas));
        tvEnergia.setText(String.format("%.2f kWh", energiaObtenida));
        tvImpacto.setText(String.format("%.2f kg CO2 ahorrados", impactoAmbiental));
        tvPorcentaje.setText(String.format("%.2f %%", porcentajeEnergiaSolar / numeroTerrazas));
        tvDias.setText(fechasDiasInferiores.length() > 0 ? fechasDiasInferiores.toString() : "Ningún día inferior al promedio");

        // Listener para salir a la pantalla de login
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(estadisticas.this, login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Listener para ir a la pantalla de registro
        registroButton.setOnClickListener(v -> {
            Intent intent = new Intent(estadisticas.this, pantallaPrincipalCategorias.class);
            startActivity(intent);
        });

        // Listener para ir a la pantalla de monitoreo
        monitoreoButton.setOnClickListener(v -> {
            Intent intent = new Intent(estadisticas.this, consejos.class);
            startActivity(intent);
        });
    }

    // Obtener datos de las terrazas registradas
    private List<Terraza> obtenerDatosDeLasTerrazas() {
        DataManager dataManager = new DataManager(this);
        ArrayList<String> terrazaNames = dataManager.obtenerListaTerrazas();
        List<Terraza> terrazas = new ArrayList<>();

        for (String nombre : terrazaNames) {
            Terraza terraza = dataManager.cargarTerraza(nombre);
            if (terraza != null) {
                terrazas.add(terraza);
            }
        }
        return terrazas;
    }

    // Calcular el impacto ambiental (ahorro de CO2)
    private double calcularImpactoAmbiental(double energiaProducida) {
        return energiaProducida * 0.5; // Ejemplo: 1 kWh producido = 0.5 kg de CO2 ahorrado
    }

    // Calcular el porcentaje de energía solar usada
    private double calcularPorcentajeEnergiaSolar(Terraza terraza) {
        if (terraza.getEnergiaConsumida() == 0) {
            return 0;
        }
        return (terraza.getEnergiaProducida() / terraza.getEnergiaConsumida()) * 100;
    }
}
