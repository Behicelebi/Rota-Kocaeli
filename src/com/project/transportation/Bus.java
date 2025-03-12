package com.project.transportation;


public class Bus extends TopluTasima {
    public Bus(){
        type = "bus";
    }

    @Override
    public String getType() {
        return type;
    }
}
