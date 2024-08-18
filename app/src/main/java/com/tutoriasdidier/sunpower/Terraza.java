package com.tutoriasdidier.sunpower;

public class Terraza {
    private String nombre;
    private String fechaProduccion;  // Formato: día/mes/año
    private double energiaProducida; // En kWh
    private double energiaConsumida; // En kWh
    private int numeroPaneles;
    private double balance;

    // Constructor
    public Terraza(String nombre, String fechaProduccion, double energiaProducida, double energiaConsumida, int numeroPaneles) {
        this.nombre = nombre;
        this.fechaProduccion = fechaProduccion;
        this.energiaProducida = energiaProducida;
        this.energiaConsumida = energiaConsumida;
        this.numeroPaneles = numeroPaneles;
        this.balance = energiaProducida - energiaConsumida;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFechaProduccion() { return fechaProduccion; }
    public void setFechaProduccion(String fechaProduccion) { this.fechaProduccion = fechaProduccion; }

    public double getEnergiaProducida() { return energiaProducida; }
    public void setEnergiaProducida(double energiaProducida) { this.energiaProducida = energiaProducida; }

    public double getEnergiaConsumida() { return energiaConsumida; }
    public void setEnergiaConsumida(double energiaConsumida) { this.energiaConsumida = energiaConsumida; }

    public int getNumeroPaneles() { return numeroPaneles; }
    public void setNumeroPaneles(int numeroPaneles) { this.numeroPaneles = numeroPaneles; }

    // Método para calcular el balance energético
    public double calcularBalanceEnergetico() {
        return balance;
    }
}

