import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AnaVeri {
    //Yeni araç eklemelerinde sadece json içerisindeki değişken ismi ile bir Arac nesnesi oluştur.
    //Sonrasında bu araç nesnesi için extra bir getter fonksiyonu ata.
    private String city;
    private Arac taxi ;
    private Arac scotter;
    private Arac marti_tag;
    private List<Durak> duraklar;
    private Map<String, Durak> durakMap = new HashMap<>();

    public String getCity() {return city;}
    public Arac getTaxi() {return taxi;}
    public Arac getScotter() {return scotter;}
    public Arac getMarti_tag() {return marti_tag;}
    public List<Durak> getDuraklar() {return duraklar;}
    public Map<String, Durak> getDurakMap() {return durakMap;}

    public void putDurakMap(Durak durak) {this.durakMap.put(durak.getId(), durak);}
}
