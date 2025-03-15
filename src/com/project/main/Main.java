package com.project.main;

import com.project.model.*;
import com.project.passenger.*;
import com.project.payment.*;
import com.project.transportation.*;
import com.project.util.AutomaticClassAdder;
import com.project.view.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static AnaVeri anaVeri;
    public static ArrayList<Arac> Araclar = new ArrayList<>();
    public static ArrayList<Yolcu> Yolcular = new ArrayList<>();
    public static ArrayList<Odeme> OdemeYontemleri = new ArrayList<>();
    public static ArrayList<TopluTasima> TopluTasimaYontemleri = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // BU KISIMDAKİ SATIRLAR JSONDAN VERİNİN OKUNMASI İŞLEMİNİ YAPIYOR

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

        // CLASSLARIN OTOMATİK EKLENMESİ İÇİN GEREKLİ

        new AutomaticClassAdder();

        // GÖRSELLEŞTİRME İÇİN GEREKLİ

        new Frame();

        // DEBUG BÖLÜMÜ

        System.out.println("Şehir: " + anaVeri.getCity());
        System.out.println("Araclar: " + anaVeri.getArac());
        System.out.println("Yahyakaptan bustan trama mesafe " + anaVeri.getDuraklar().get(2).getTransfer().getTransferMesafe());
        System.out.println("Bu ne ?" + anaVeri.getDuraklar().get(2).getVehicle().getType());
        System.out.println(anaVeri.getDurakMap().get("bus_otogar").getName());
        System.out.println(Araclar.get(2).getName());
    }

    // NOTLAR (DAHA ÇOK TO-DO LIST)

    // En yakın durağı bulma fonksiyonu (YAPILDI)
    // Uzaklık bulma fonksiyonu (iki koordinat arasında) (YAPILDI)
    // Bütün olası yolları bulan fonksiyon (YAPILDI)
    // Oranların düzeltilmesi (YAPILDI)
    // Json dosyası içerisine araclar adında bir blok açılması. (YAPILDI)
    // En yakın durak bulma fonksiyonunu en yakın uygun durağı bulacak şekilde güncelle. (YAPILDI)
    // Otomatik oluşturma classı yapılacak ve bunun içerisine araçların otomatik oluşturulması da eklenecek (YAPILDI)
    // Yolların giderlerini bulma fonksiyonu (Süre, Yol, Maliyet) gibi gibi (YAPILDI)
    // Transfer verilerindeki transferMesafe kaldırılıp koordinatlar üzerinden hesaplama yapılması. (ŞÜPHELİ) (SORULMASI GEREK)
    // Görselleştirme ile olası rotaların kullanıcıya sunulması.

    // Classlarda SOLID e uygun olmayan durumları belirle ve çöz (ALT AÇIKLAMALARIN HEPSİ BUNUNLA İLGİLİ)
    // com.project.util.DistanceCalculator interface i eklenerek Açık/Kapalı prensibine zıt durum düzeltildi.
    // com.project.main içerisinde tercihlerin otomatik alınması eklendi.
    // com.project.transportation içerisindeki classların otomatik alınması eklendi.
}