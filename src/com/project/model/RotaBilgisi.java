package com.project.model;

import java.util.ArrayList;

public class RotaBilgisi {
    private int aracId;
    private int yolcuId;
    private int odemeId;
    private double baslangicLongitude;
    private double baslangicLatitude;
    private double bitisLongitude;
    private double bitisLatitude;
    private double yolUzunlugu;
    private double yolUcreti;
    private double yolSuresi;
    private ArrayList<String> yolDuraklari = new ArrayList<>();
    public RotaBilgisi() {
        yolUzunlugu = 0;
        yolUcreti = 0;
        yolSuresi = 0;
    }

    public int getAracId() {
        return aracId;
    }

    public void setAracId(int aracId) {
        this.aracId = aracId;
    }

    public double getBaslangicLongitude() {
        return baslangicLongitude;
    }

    public void setBaslangicLongitude(double baslangicLongitude) {
        this.baslangicLongitude = baslangicLongitude;
    }

    public double getBaslangicLatitude() {
        return baslangicLatitude;
    }

    public void setBaslangicLatitude(double baslangicLatitude) {
        this.baslangicLatitude = baslangicLatitude;
    }

    public double getBitisLongitude() {
        return bitisLongitude;
    }

    public void setBitisLongitude(double bitisLongitude) {
        this.bitisLongitude = bitisLongitude;
    }

    public double getBitisLatitude() {
        return bitisLatitude;
    }

    public void setBitisLatitude(double bitisLatitude) {
        this.bitisLatitude = bitisLatitude;
    }

    public double getYolUcreti() {
        return yolUcreti;
    }

    public void setYolUcreti(double yolUcreti) {
        this.yolUcreti = yolUcreti;
    }

    public double getYolUzunlugu() {
        return yolUzunlugu;
    }

    public void setYolUzunlugu(double yolUzunlugu) {
        this.yolUzunlugu = yolUzunlugu;
    }

    public double getYolSuresi() {
        return yolSuresi;
    }

    public void setYolSuresi(double yolSuresi) {
        this.yolSuresi = yolSuresi;
    }

    public ArrayList<String> getYolDuraklari() {
        return yolDuraklari;
    }

    public void setYolDuraklari(ArrayList<String> yolDuraklari) {
        this.yolDuraklari = yolDuraklari;
    }

    public int getYolcuId() {
        return yolcuId;
    }

    public void setYolcuId(int yolcuId) {
        this.yolcuId = yolcuId;
    }

    public int getOdemeId() {
        return odemeId;
    }

    public void setOdemeId(int odemeId) {
        this.odemeId = odemeId;
    }
}
