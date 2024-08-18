package com.tutoriasdidier.sunpower;

public class Terraza {
    private String nombre;
    private String fechaProduccion;
    private double energiaProducida;
    private double energiaConsumida;
    private int numeroPaneles;

    public Terraza(String nombre, String fechaProduccion, double energiaProducida, double energiaConsumida, int numeroPaneles) {
        this.nombre = nombre;
        this.fechaProduccion = fechaProduccion;
        this.energiaProducida = energiaProducida;
        this.energiaConsumida = energiaConsumida;
        this.numeroPaneles = numeroPaneles;
    }

    public String getNombre() { return nombre; }
    public String getFechaProduccion() { return fechaProduccion; }
    public double getEnergiaProducida() { return energiaProducida; }
    public double getEnergiaConsumida() { return energiaConsumida; }
    public int getNumeroPaneles() { return numeroPaneles; }

    public double calcularBalanceEnergetico() {
        return energiaProducida - energiaConsumida;
    }
}
