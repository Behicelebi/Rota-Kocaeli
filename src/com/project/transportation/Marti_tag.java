package com.project.transportation;

import java.awt.*;

public class Marti_tag extends Arac{
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
        return "Marti Tag";
    }

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }
}
