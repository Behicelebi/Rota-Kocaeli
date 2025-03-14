package com.project.util;

import com.project.main.Main;
import com.project.passenger.Yolcu;
import com.project.payment.Odeme;
import com.project.transportation.*;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class AutomaticClassAdder {
    public AutomaticClassAdder() {
        // YOLCU TİPLERİNİN OTOMATİK EKLENMESİ

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.project.passenger").scan()) {
            ClassInfoList yolcuClasses = scanResult.getClassesImplementing(Yolcu.class.getName());
            for(ClassInfo classInfo : yolcuClasses){
                try {
                    if (!classInfo.isInterface()) {
                        Yolcu yolcu = (Yolcu) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        Main.Yolcular.add(yolcu);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating object: " + classInfo.getSimpleName());
                    e.printStackTrace();
                }
            }
        }

        // ÖDEME YÖNTEMLERİNİN OTOMATİK EKLENEMESİ

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.project.payment").scan()) {
            ClassInfoList odemeClasses = scanResult.getClassesImplementing(Odeme.class.getName());
            for(ClassInfo classInfo : odemeClasses){
                try {
                    if (!classInfo.isInterface()) {
                        Odeme odeme = (Odeme) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        Main.OdemeYontemleri.add(odeme);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating object: " + classInfo.getSimpleName());
                    e.printStackTrace();
                }
            }
        }

        // TOPLU TAŞIMA YÖNTEMLERİNİN OTOMATİK EKLENMESİ

        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("com.project.transportation").scan()) {
            ClassInfoList topluTasimaClasses = scanResult.getSubclasses(TopluTasima.class.getName());
            for(ClassInfo classInfo : topluTasimaClasses){
                try {
                    if (!classInfo.isAbstract()) {
                        TopluTasima topluTasima = (TopluTasima) classInfo.loadClass().getDeclaredConstructor().newInstance();
                        Main.TopluTasimaYontemleri.add(topluTasima);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating object: " + classInfo.getSimpleName());
                    e.printStackTrace();
                }
            }
        }

        // ARAC TİPLERİNİN OTOMATİK EKLENMESİ

        Main.Araclar.addAll(Main.anaVeri.getArac());
    }
}
