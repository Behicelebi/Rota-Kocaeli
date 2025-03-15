package com.project.transportation;

public class Scooter extends Arac {
    @Override
    public void setOpeningFee(double openingFee) {
        this.openingFee = openingFee;
    }

    @Override
    public void setCostPerKm(double costPerKm) {
        this.costPerKm = costPerKm;
    }

    @Override
    public void setTimePerKm(double timePerKm) {
        this.timePerKm = timePerKm;
    }

    @Override
    public double getOpeningFee() {
        return openingFee;
    }

    @Override
    public double getCostPerKm() {
        return costPerKm;
    }

    @Override
    public double getTimePerKm() {
        return timePerKm;
    }

    @Override
    public String getName() {
        return "Scooter";
    }
}
