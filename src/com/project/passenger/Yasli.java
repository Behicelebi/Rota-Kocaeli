package com.project.passenger;

public class Yasli implements Yolcu {
    @Override
    public double getDiscountPrice() {
        return 0.0;
    }

    @Override
    public String getClassName() {
        return "Yaşlı";
    }

    @Override
    public double getWalkTimePerKm() {
        return 15.0;
    }
}
