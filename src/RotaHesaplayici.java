import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class RotaHesaplayici {

    public static int findNearestStop(double lat, double lon) {
        int enKucukIndex = 0;
        int index = 0;
        for(Durak durak : Main.anaVeri.getDuraklar()) {
            if(calculateDistance(lat, lon, durak.getLat(), durak.getLon()) < calculateDistance(lat, lon, Main.anaVeri.getDuraklar().get(enKucukIndex).getLat(), Main.anaVeri.getDuraklar().get(enKucukIndex).getLon())) {
                enKucukIndex = index;
            }
            index++;
        }
        return enKucukIndex;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(deltaPhi / 2), 2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(deltaLambda / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R*c;
    }

    public static void calculateShortestPath(double lat1, double lon1, double lat2, double lon2) {
        calculateDistance(lat1,lon1,lat2,lon2);
        findNearestStop(lat1,lon1);
        findNearestStop(lat2,lon2);
    }

    private static int isItStop(double lat, double lon) {
        int index = 0;
        for(Durak durak : Main.anaVeri.getDuraklar()) {
            if(durak.getLat() == lat && durak.getLon() == lon) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static List<List<String>> findPaths(double lat1, double lon1, double lat2, double lon2) {
        List<List<String>> paths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        String startId, endId;

        if(isItStop(lat1, lon1) != -1){
            startId = Main.anaVeri.getDuraklar().get(isItStop(lat1, lon1)).getId();
        } else {
            startId = Main.anaVeri.getDuraklar().get(findNearestStop(lat1, lon1)).getId();
        }

        if(isItStop(lat2, lon2) != -1){
            endId = Main.anaVeri.getDuraklar().get(isItStop(lat2, lon2)).getId();
        } else {
            endId = Main.anaVeri.getDuraklar().get(findNearestStop(lat2, lon2)).getId();
        }

        currentPath.add(startId);
        dfs(startId, endId, visited, currentPath, paths);
        return paths;
    }

    private static void dfs(String currentId, String endId, Set<String> visited, List<String> currentPath, List<List<String>> paths) {
        if (currentId.equals(endId)) {
            paths.add(new ArrayList<>(currentPath));
            return;
        }

        Durak currentDurak = Main.anaVeri.getDurakMap().get(currentId);
        if (currentDurak == null) return;

        visited.add(currentId);


        for (Baglanti baglanti : currentDurak.getNextStops()) {
            String nextStopId = baglanti.getStopId();
            if (!visited.contains(nextStopId)) {
                currentPath.add(nextStopId);
                dfs(nextStopId, endId, visited, currentPath, paths);
                currentPath.remove(currentPath.size() - 1);
            }
        }

        Transfer transfer = currentDurak.getTransfer();
        if (transfer != null && !visited.contains(transfer.getTransferStopId())) {
            String transferStopId = transfer.getTransferStopId();
            currentPath.add(transferStopId);
            dfs(transferStopId, endId, visited, currentPath, paths);
            currentPath.remove(currentPath.size() - 1);
        }

        visited.remove(currentId);
    }
}

