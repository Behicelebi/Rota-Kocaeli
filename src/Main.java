import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static AnaVeri anaVeri;
    static ArrayList<Yolcu> Yolcular = new ArrayList<>();
    static ArrayList<Odeme> OdemeYontemleri = new ArrayList<>();
    static ArrayList<TopluTasima> TopluTasimaYontemleri = new ArrayList<>();
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

        TopluTasimaYontemleri.add(new Bus());
        TopluTasimaYontemleri.add(new Tram());
        Yolcular.add(new Ogrenci());
        Yolcular.add(new Genel());
        Yolcular.add(new Yasli());
        OdemeYontemleri.add(new Kentkart());
        OdemeYontemleri.add(new KrediKarti());
        OdemeYontemleri.add(new Nakit());

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
    // DistanceCalculator interface i eklenerek Açık/Kapalı prensibine zıt durum düzeltildi.
    //
}