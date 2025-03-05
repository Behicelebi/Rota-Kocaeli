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
    public String getType() {return type;}
    public double getLat() {return lat;}
    public double getLon() {return lon;}
    public boolean isSonDurak() {return sonDurak;}
    public List<Baglanti> getNextStops() {return nextStops;}
    public Transfer getTransfer() {return transfer;}
}
