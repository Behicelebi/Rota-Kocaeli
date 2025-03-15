package com.project.passenger;

public class Genel implements Yolcu {
    @Override
    public double getDiscountPrice() {
        return 1.0;
    }

    @Override
    public String getClassName() {
        return "Genel";
    }

    @Override
    public double getWalkTimePerKm() {
        return 12.0;
    }
}
