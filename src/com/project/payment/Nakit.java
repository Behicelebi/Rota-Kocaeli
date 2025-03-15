package com.project.payment;

public class Nakit implements Odeme {
    @Override
    public double getDiscountPrice() {
        return 1.0;
    }

    @Override
    public String getClassName() {
        return "Nakit";
    }
}
