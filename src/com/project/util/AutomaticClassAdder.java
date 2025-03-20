package com.project.util;

import com.project.main.Main;
import com.project.passenger.Yolcu;
import com.project.payment.Odeme;
import com.project.transportation.*;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AutomaticClassAdder {
    private static final Logger logger = Logger.getLogger(AutomaticClassAdder.class.getName());
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
                    logger.log(Level.SEVERE, "Error creating object", e);
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
                    logger.log(Level.SEVERE, "Error creating object", e);
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
                    logger.log(Level.SEVERE, "Error creating object", e);
                }
            }
        }

        // ARAC TİPLERİNİN OTOMATİK EKLENMESİ

        Main.Araclar.addAll(Main.anaVeri.getArac());
    }
}
