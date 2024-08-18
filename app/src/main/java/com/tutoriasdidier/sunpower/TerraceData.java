package com.tutoriasdidier.sunpower;

public class TerraceData {
    private String terraceName;
    private int numberOfPanels;
    private double energyProduced;
    private double energyConsumed;
    private String productionDate;

    // Constructor, getters, and setters
    public TerraceData(String terraceName, int numberOfPanels, double energyProduced, double energyConsumed, String productionDate) {
        this.terraceName = terraceName;
        this.numberOfPanels = numberOfPanels;
        this.energyProduced = energyProduced;
        this.energyConsumed = energyConsumed;
        this.productionDate = productionDate;
    }

    public String getTerraceName() {
        return terraceName;
    }

    public int getNumberOfPanels() {
        return numberOfPanels;
    }

    public double getEnergyProduced() {
        return energyProduced;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public String getProductionDate() {
        return productionDate;
    }
}

