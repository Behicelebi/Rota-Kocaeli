package com.project.main;

import com.project.model.*;
import com.project.passenger.*;
import com.project.payment.*;
import com.project.transportation.*;
import com.project.util.AutomaticClassAdder;
import com.project.util.JsonReader;
import com.project.view.*;
import java.util.ArrayList;

public class Main {
    public static AnaVeri anaVeri;
    public static ArrayList<Arac> Araclar = new ArrayList<>();
    public static ArrayList<Yolcu> Yolcular = new ArrayList<>();
    public static ArrayList<Odeme> OdemeYontemleri = new ArrayList<>();
    public static ArrayList<TopluTasima> TopluTasimaYontemleri = new ArrayList<>();

    public static void main(String[] args) {
        // JSONUN OKUNMASI İÇİN GEREKLİ

        new JsonReader();

        // CLASSLARIN OTOMATİK EKLENMESİ İÇİN GEREKLİ

        new AutomaticClassAdder();

        // GÖRSELLEŞTİRME VE HESAPLAMALAR İÇİN GEREKLİ

        new Frame();
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
    // Görselleştirme ile olası rotaların kullanıcıya sunulması. (YAPILDI)
    // rotaHesaplayici getter ile kullanılması. (YAPILDI)
    // Görselleştirme için dinamik renklendirme eklenmesi. (YAPILDI)
    // Transfer verilerindeki transferMesafe kaldırılıp koordinatlar üzerinden hesaplama yapılması. (ŞÜPHELİ DEĞİL SORULDU)

    // Classlarda SOLID e uygun olmayan durumları belirle ve çöz (ALT AÇIKLAMALARIN HEPSİ BUNUNLA İLGİLİ)
    // com.project.util.DistanceCalculator interface i eklenerek Açık/Kapalı prensibine zıt durum düzeltildi.
    // com.project.main içerisinde tercihlerin otomatik alınması eklendi.
    // com.project.transportation içerisindeki classların otomatik alınması eklendi.
    // com.project.viev.Panel 210 SOLID'e uygun değil.
}