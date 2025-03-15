package com.project.payment;

public class KrediKarti implements Odeme {
    @Override
    public double getDiscountPrice() {
        return 1.4;
    }

    @Override
    public String getClassName() {
        return "Kredi Kartı";
    }
}
