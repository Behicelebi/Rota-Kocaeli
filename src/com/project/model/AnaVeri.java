package com.project.model;

import com.project.transportation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AnaVeri {
    //Yeni araç eklemelerinde sadece json içerisindeki değişken ismi ile bir com.project.transportation.Arac nesnesi oluştur.
    //Sonrasında bu araç nesnesi için extra bir getter fonksiyonu ata.
    private String city;
    private List<AracTransfer> araclar;
    private List<Durak> duraklar;
    private Map<String, Durak> durakMap = new HashMap<>();

    public String getCity() {return city;}
    public List<Durak> getDuraklar() {return duraklar;}
    public Map<String, Durak> getDurakMap() {return durakMap;}
    public void putDurakMap(Durak durak) {this.durakMap.put(durak.getId(), durak);}
    public List<Arac> getArac() {
        List<Arac> aracList = new ArrayList<>();
        for(AracTransfer arac : this.araclar) {
            String className = "com.project.transportation." + arac.getName().substring(0, 1).toUpperCase() + arac.getName().substring(1);
            try {
                Class<?> alinan = Class.forName(className);
                Arac aracAlinan = (Arac) alinan.getDeclaredConstructor().newInstance();
                aracAlinan.setCostPerKm(arac.getCostPerKm());
                aracAlinan.setOpeningFee(arac.getOpeningFee());
                aracAlinan.setTimePerKm(arac.getTimePerKm());
                aracList.add(aracAlinan);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Class not found: " + className, e);
            } catch (Exception e) {
                throw new RuntimeException("Could not create an instance of class: " + className, e);
            }
        }
        return aracList;
    }
}
