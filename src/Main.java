import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static AnaVeri anaVeri;
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

        new Frame();

        System.out.println("Şehir: " + anaVeri.getCity());
        System.out.println("Taksi Açılış Ücreti: " + anaVeri.getTaxi().getOpeningFee());
        System.out.println("Scotter Açılış Ücreti: " + anaVeri.getScotter().getOpeningFee());
        System.out.println("Martı Tag Açılış Ücreti: " + anaVeri.getMarti_tag().getOpeningFee());
        System.out.println(anaVeri.getDuraklar().get(0).getId() + " Durağının bir sonraki durağı " + anaVeri.getDuraklar().get(0).getNextStops().get(0).getStopId());
    }
}