package com.project.main;

import com.project.model.*;
import com.project.passenger.*;
import com.project.payment.*;
import com.project.transportation.*;
import com.project.view.*;
import com.google.gson.Gson;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static AnaVeri anaVeri;
    public static ArrayList<Yolcu> Yolcular = new ArrayList<>();
    public static ArrayList<Odeme> OdemeYontemleri = new ArrayList<>();
    public static ArrayList<TopluTasima> TopluTasimaYontemleri = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //BU KISIMDAKİ SATIRLAR JSONDAN VERİNİN OKUNMASI İŞLEMİNİ YAPIYOR

        String jsonContent;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get("veriseti.json")));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: veriseti.json", e);
            return;
        }

        Gson gson = new Gson();
        anaVeri = gson.fromJson(jsonContent, AnaVeri.class);
        for(Durak durak : anaVeri.getDuraklar()){
            anaVeri.putDurakMap(durak);
        }

        //BU KISIMDAKİ SATIRLAR TERCİHLERİN EKLENMESİNİ İÇERİYOR

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.project.passenger").scan()) {
            ClassInfoList yolcuClasses = scanResult.getClassesImplementing(Yolcu.class.getName());
            for(ClassInfo classInfo : yolcuClasses){
                try {
                    if (!classInfo.isInterface()) {
                        Yolcu yolcu = (Yolcu) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        Yolcular.add(yolcu);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating object: " + classInfo.getSimpleName());
                    e.printStackTrace();
                }
            }
        }

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.project.payment").scan()) {
            ClassInfoList odemeClasses = scanResult.getClassesImplementing(Odeme.class.getName());
            for(ClassInfo classInfo : odemeClasses){
                try {
                    if (!classInfo.isInterface()) {
                        Odeme odeme = (Odeme) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        OdemeYontemleri.add(odeme);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating object: " + classInfo.getSimpleName());
                    e.printStackTrace();
                }
            }
        }

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.project.transportation").scan()) {
            ClassInfoList topluTasimaClasses = scanResult.getClassesWithAnnotation(TopluTasima.class.getName());
            for(ClassInfo classInfo : topluTasimaClasses){
                try {
                    if (!classInfo.isAbstract()) {
                        TopluTasima topluTasima = (TopluTasima) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        TopluTasimaYontemleri.add(topluTasima);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating object: " + classInfo.getSimpleName());
                    e.printStackTrace();
                }
            }
        }

        //GÖRSELLEŞTİRME İÇİN GEREKLİ

        new Frame();

        //DEBUG BÖLÜMÜ

        System.out.println("Şehir: " + anaVeri.getCity());
        System.out.println("Taksi Açılış Ücreti: " + anaVeri.getTaxi().getOpeningFee());
        System.out.println("Scotter Açılış Ücreti: " + anaVeri.getScotter().getOpeningFee());
        System.out.println("Martı Tag Açılış Ücreti: " + anaVeri.getMarti_tag().getOpeningFee());
        System.out.println("Taksinin bir kilometreyi gitmesi için geçen dakika: " + anaVeri.getTaxi().getTimePerKm());
        System.out.println("Yahyakaptan bustan trama mesafe " + anaVeri.getDuraklar().get(2).getTransfer().getTransferMesafe());
        System.out.println(anaVeri.getDurakMap().get("bus_otogar").getName());
    }

    // NOTLAR (DAHA ÇOK TO-DO LIST)

    // En yakın durağı bulma fonksiyonu (YAPILDI)
    // Uzaklık bulma fonksiyonu (iki koordinat arasında) (YAPILDI)
    // Bütün olası yolları bulan fonksiyon (YAPILDI)
    // Oranların düzeltilmesi (YAPILDI)
    // Yolların giderlerini bulma fonksiyonu (Süre, Yol, Maliyet) gibi gibi

    // Classlarda SOLID e uygun olmayan durumları belirle ve çöz (ALT AÇIKLAMALARIN HEPSİ BUNUNLA İLGİLİ)
    // com.project.util.DistanceCalculator interface i eklenerek Açık/Kapalı prensibine zıt durum düzeltildi.
    //
}