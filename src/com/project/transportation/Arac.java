package com.project.transportation;


public abstract class Arac {
    protected String name;
    protected double openingFee;
    protected double costPerKm;
    protected double timePerKm;

    public abstract void setOpeningFee(double openingFee);
    public abstract void setCostPerKm(double costPerKm);
    public abstract void setTimePerKm(double timePerKm);
    public abstract double getOpeningFee();
    public abstract double getCostPerKm();
    public abstract double getTimePerKm();
    public abstract String getName();
}
