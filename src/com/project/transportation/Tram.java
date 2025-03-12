package com.project.transportation;

public class Tram extends TopluTasima {
    public Tram(){
        type = "tram";
    }

    @Override
    public String getType() {
        return type;
    }
}
