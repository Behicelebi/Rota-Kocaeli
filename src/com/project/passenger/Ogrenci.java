package com.project.passenger;

public class Ogrenci implements Yolcu {

    @Override
    public double getDiscountPrice() {
        return 0.5;
    }

    @Override
    public String getClassName() {
        return "Öğrenci";
    }

    @Override
    public double getWalkTimePerKm() {
        return 10.0;
    }
}
