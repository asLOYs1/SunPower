package com.tutoriasdidier.sunpower;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static final String PREFS_NAME = "TerrazaPrefs";
    private static final String KEY_TERRAZAS = "terrazas";
    private static SharedPreferences sharedPreferences;

    public DataManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("TerrazasData", Context.MODE_PRIVATE);
    }

    // Método para calcular la energía producida o consumida en la última semana
    public double calcularEnergiaSemanal(String nombreTerraza, boolean esProduccion) {
        double energiaSemanal = 0.0;

        for (int i = 0; i < 7; i++) { // Suponiendo que los datos diarios están guardados
            String key = nombreTerraza + "_dia" + i + (esProduccion ? "_energiaProducida" : "_energiaConsumida");
            energiaSemanal += sharedPreferences.getFloat(key, 0); // Obtiene el valor de cada día
        }

        return energiaSemanal; // Devuelve el total semanal
    }

    // Método para calcular el promedio de producción semanal
    public double obtenerPromedioProduccionSemanal(String nombreTerraza) {
        double totalProduccion = 0.0;
        int diasRegistrados = 7; // Suponiendo que tenemos datos para 7 días

        for (int i = 0; i < diasRegistrados; i++) {
            String key = nombreTerraza + "_dia" + i + "_energiaProducida";
            totalProduccion += sharedPreferences.getFloat(key, 0);
        }

        return totalProduccion / diasRegistrados; // Promedio de producción semanal
    }

    // Método para obtener las fechas de los días con producción inferior al promedio
    public List<String> obtenerDiasInferioresAlPromedio(String nombreTerraza, double promedio) {
        List<String> diasInferiores = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            String energiaKey = nombreTerraza + "_dia" + i + "_energiaProducida";
            String fechaKey = nombreTerraza + "_dia" + i + "_fecha";

            double energiaProducida = sharedPreferences.getFloat(energiaKey, 0);
            String fecha = sharedPreferences.getString(fechaKey, "Fecha desconocida");

            if (energiaProducida < promedio) {
                diasInferiores.add(fecha); // Agrega la fecha si la producción es inferior al promedio
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

    public static Terraza cargarTerraza(String nombre) {
        String fechaProduccion = sharedPreferences.getString(nombre + "_fecha", "");
        double energiaProducida = sharedPreferences.getFloat(nombre + "_energiaProducida", 0);
        double energiaConsumida = sharedPreferences.getFloat(nombre + "_energiaConsumida", 0);
        int numeroPaneles = sharedPreferences.getInt(nombre + "_numeroPaneles", 0);

        if (!fechaProduccion.isEmpty()) {
            return new Terraza(nombre, fechaProduccion, energiaProducida, energiaConsumida, numeroPaneles);
        } else {
            return null;
        }
    }

    public void borrarTerraza(String nombre) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(nombre + "_fecha");
        editor.remove(nombre + "_energiaProducida");
        editor.remove(nombre + "_energiaConsumida");
        editor.remove(nombre + "_numeroPaneles");
        editor.apply();
    }

    //Método para obtener todas las terrazas
    public static List<Terraza> obtenerTodasLasTerrazas(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TerrazaPrefs", Context.MODE_PRIVATE);
        List<Terraza> terrazas = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            // Suponiendo que la clave es el nombre de la terraza
            String nombre = entry.getKey();
            Terraza terraza = cargarTerraza(nombre);
            if (terraza != null) {
                terrazas.add(terraza);
            }
        }
        return terrazas;
    }

    public ArrayList<String> obtenerListaTerrazas() {
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
        Collections.sort(terrazaNames);
        return terrazaNames;
    }
    //Método para obtener todas las terrazas
    /*public static List<Terraza> obtenerTodasLasTerrazas(Context context) {
        DataManager dataManager = new DataManager(context);
        ArrayList<String> terrazaNames = dataManager.obtenerListaTerrazas();
        List<Terraza> terrazas = new ArrayList<>();

        for (String nombre : terrazaNames) {
            Terraza terraza = dataManager.cargarTerraza(nombre);
            if (terraza != null) {
                terrazas.add(terraza);
            }
        }
        return terrazas;
    }*/

}