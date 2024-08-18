package com.tutoriasdidier.sunpower;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final String PREFS_NAME = "TerrazaPrefs";
    private SharedPreferences sharedPreferences;

    public DataManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences("terraceData", Context.MODE_PRIVATE);

    }

    public List<Terraza> getTerraceData() {
        List<Terraza> terrazas = new ArrayList<>();
        ArrayList<String> terraceNames = obtenerListaTerrazas(); // Get sorted terrace names

        for (String terraceName : terraceNames) {
            Terraza terraza = cargarTerraza(terraceName);
            if (terraza != null) {
                terrazas.add(terraza);
            }
        }

        return terrazas;
    }

    public double calcularEnergiaSemanal(String nombreTerraza, boolean esProduccion) {
        double energiaSemanal = 0.0;

        for (int i = 0; i < 7; i++) {
            String key = nombreTerraza + "_dia" + i + (esProduccion ? "_energiaProducida" : "_energiaConsumida");
            energiaSemanal += sharedPreferences.getFloat(key, 0f);
        }

        return energiaSemanal;
    }

    public double obtenerPromedioProduccionSemanal(String nombreTerraza) {
        double totalProduccion = 0.0;

        for (int i = 0; i < 7; i++) {
            String key = nombreTerraza + "_dia" + i + "_energiaProducida";
            totalProduccion += sharedPreferences.getFloat(key, 0f);
        }

        return totalProduccion / 7; // Promedio de producción semanal
    }

    public List<String> obtenerDiasInferioresAlPromedio(String nombreTerraza, double promedio) {
        List<String> diasInferiores = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            String energiaKey = nombreTerraza + "_dia" + i + "_energiaProducida";
            String fechaKey = nombreTerraza + "_dia" + i + "_fecha";

            double energiaProducida = sharedPreferences.getFloat(energiaKey, 0f);
            String fecha = sharedPreferences.getString(fechaKey, "Fecha desconocida");

            if (energiaProducida < promedio) {
                diasInferiores.add(fecha);
            }
        }

        return diasInferiores;
    }

    public void guardarTerraza(Terraza terraza) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(terraza.getNombre() + "_fecha", terraza.getFechaProduccion());
        editor.putFloat(terraza.getNombre() + "_energiaProducida", (float) terraza.getEnergiaProducida());
        editor.putFloat(terraza.getNombre() + "_energiaConsumida", (float) terraza.getEnergiaConsumida());
        editor.putInt(terraza.getNombre() + "_numeroPaneles", terraza.getNumeroPaneles());
        editor.apply();
    }

    public Terraza cargarTerraza(String nombre) {
        String fechaProduccion = sharedPreferences.getString(nombre + "_fecha", "");
        double energiaProducida = sharedPreferences.getFloat(nombre + "_energiaProducida", 0f);
        double energiaConsumida = sharedPreferences.getFloat(nombre + "_energiaConsumida", 0f);
        int numeroPaneles = sharedPreferences.getInt(nombre + "_numeroPaneles", 0);

        if (!fechaProduccion.isEmpty()) {
            return new Terraza(nombre, fechaProduccion, energiaProducida, energiaConsumida, numeroPaneles);
        }
        return null;
    }

    public void borrarTerraza(String nombre) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(nombre + "_fecha");
        editor.remove(nombre + "_energiaProducida");
        editor.remove(nombre + "_energiaConsumida");
        editor.remove(nombre + "_numeroPaneles");
        editor.apply();
    }

    public int obtenerNumeroDeTerrazas() {
        ArrayList<String> terrazaNames = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains("_fecha")) {
                String terrazaName = entry.getKey().split("_fecha")[0];
                if (!terrazaNames.contains(terrazaName)) {
                    terrazaNames.add(terrazaName);
                }
            }
        }
        return terrazaNames.size();
    }

    public List<Terraza> obtenerTodasLasTerrazas() {
        List<Terraza> terrazas = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String nombre = entry.getKey().split("_")[0];
            if (!terrazas.contains(nombre)) {
                Terraza terraza = cargarTerraza(nombre);
                if (terraza != null) {
                    terrazas.add(terraza);
                }
            }
        }
        return terrazas;
    }

    public ArrayList<String> obtenerListaTerrazas() {
        ArrayList<String> terrazaNames = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains("_fecha")) {
                String terrazaName = entry.getKey().split("_fecha")[0];
                if (!terrazaNames.contains(terrazaName)) {
                    terrazaNames.add(terrazaName);
                }
            }
        }
        Collections.sort(terrazaNames);
        return terrazaNames;
    }
}

