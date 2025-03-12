package com.project.model;

import com.project.transportation.TopluTasima;
import java.util.List;

public class Durak {
    private String id;
    private String name;
    private String type;
    private double lat;
    private double lon;
    private boolean sonDurak;
    private List<Baglanti> nextStops;
    private Transfer transfer;

    public String getId() {return id;}
    public String getName() {return name;}
    public double getLat() {return lat;}
    public double getLon() {return lon;}
    public boolean isSonDurak() {return sonDurak;}
    public List<Baglanti> getNextStops() {return nextStops;}
    public Transfer getTransfer() {return transfer;}
    public TopluTasima getVehicle() {
        String className = "com.project.transportation." + type.substring(0, 1).toUpperCase() + type.substring(1);
        try {
            Class<?> clazz = Class.forName(className);
            return (TopluTasima) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        } catch (Exception e) {
            throw new RuntimeException("Could not create an instance of class: " + className, e);
        }
    }
}
