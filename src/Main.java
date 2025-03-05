import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String jsonContent = null;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get("veriseti.json")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson = new Gson();
        AnaVeri anaVeri = gson.fromJson(jsonContent, AnaVeri.class);

        System.out.println("Şehir: " + anaVeri.getCity());
        System.out.println("Taksi Açılış Ücreti: " + anaVeri.getTaxi().getOpeningFee());
        System.out.println("Scotter Açılış Ücreti: " + anaVeri.getScotter().getOpeningFee());
        System.out.println("Martı Tag Açılış Ücreti: " + anaVeri.getMarti_tag().getOpeningFee());
        System.out.println(anaVeri.getDuraklar().get(0).getId() + " Durağının bir sonraki durağı " + anaVeri.getDuraklar().get(0).getNextStops().get(0).getStopId());
    }
}