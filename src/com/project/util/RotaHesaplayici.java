package com.project.util;

import com.project.main.*;
import com.project.model.*;

import java.util.*;

public class RotaHesaplayici {
    private DistanceCalculator distanceCalculator;

    public RotaHesaplayici(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public int findNearestStop(double lat, double lon, HashMap<String, Durak> visited) {
        int enKucukIndex = 0;
        int index = 0;
        for(Durak durak : Main.anaVeri.getDuraklar()) {
            if(distanceCalculator.calculateDistance(lat, lon, durak.getLat(), durak.getLon()) <
                    distanceCalculator.calculateDistance(lat, lon, Main.anaVeri.getDuraklar().get(enKucukIndex).getLat(),
                            Main.anaVeri.getDuraklar().get(enKucukIndex).getLon()) && !visited.containsKey(durak.getId())) {
                enKucukIndex = index;
            }
            index++;
        }
        return enKucukIndex;
    }

    public List<String> calculatePathDetails(double lat1, double lon1, double lat2, double lon2) {
        List<String> pathDetails = new ArrayList<>();
        List<List<String>> paths = findPaths(lat1, lon1, lat2, lon2);
        return pathDetails;
    }

    public List<List<String>> findPaths(double lat1, double lon1, double lat2, double lon2) {
        List<List<String>> paths = new ArrayList<>();
        HashMap<String, Durak> visited2 = new HashMap<>();
        String endId = Main.anaVeri.getDuraklar().get(findNearestStop(lat2, lon2, visited2)).getId();
        for (Durak durak : Main.anaVeri.getDuraklar()) {
            paths.clear();
            String startId = Main.anaVeri.getDuraklar().get(findNearestStop(lat1, lon1, visited2)).getId();
            if(Objects.equals(startId, endId)){break;}
            Set<String> visited = new HashSet<>();
            List<String> currentPath = new ArrayList<>();
            currentPath.add(startId);
            dfs(startId, endId, visited, currentPath, paths);
            if(!paths.isEmpty()){break;}
            visited2.put(durak.getId(),durak);
        }
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

    public void setDistanceCalculator(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }
}