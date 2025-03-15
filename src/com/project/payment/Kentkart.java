package com.project.payment;

public class Kentkart implements Odeme {
    @Override
    public double getDiscountPrice() {
        return 0.7;
    }

    @Override
    public String getClassName() {
        return "Kentkart";
    }
}
