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

        //BU KISMA KADAR OLAN SATIRLAR JSONDAN VERİNİN OKUNMASI İŞLEMİNİ YAPIYOR

        TopluTasimaYontemleri.add(new Bus());
        TopluTasimaYontemleri.add(new Tram());
        Yolcular.add(new Ogrenci());
        Yolcular.add(new Genel());
        Yolcular.add(new Yasli());
        OdemeYontemleri.add(new Kentkart());
        OdemeYontemleri.add(new KrediKarti());
        OdemeYontemleri.add(new Nakit());

        //BU KISIMA KADAR OLAN SATIRLAR TERCİHLERİN EKLENMESİNİ İÇERİYOR

        new Frame();

        //GÖRSELLEŞTİRME İÇİN GEREKLİ

        System.out.println("Şehir: " + anaVeri.getCity());
        System.out.println("Taksi Açılış Ücreti: " + anaVeri.getTaxi().getOpeningFee());
        System.out.println("Scotter Açılış Ücreti: " + anaVeri.getScotter().getOpeningFee());
        System.out.println("Martı Tag Açılış Ücreti: " + anaVeri.getMarti_tag().getOpeningFee());
        System.out.println(anaVeri.getDurakMap().get("bus_otogar").getName());

    }
    // En yakın durağı bulma fonksiyonu (YAPILDI)
    // Uzaklık bulma fonksiyonu (iki koordinat arasında) (YAPILDI)
    // Bütün olası yolları bulan fonksiyon (YAPILDI)
    // Oranların düzeltilmesi
    // Yolların giderlerini bulma fonksiyonu (Süre, Yol, Maliyet) gibi gibi
    //
}