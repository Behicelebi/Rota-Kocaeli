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

    public List<RotaBilgisi> calculatePathDetails(double lat1, double lon1, double lat2, double lon2, int aracIndex, int yolcuIndex, int odemeIndex) {
        List<RotaBilgisi> pathDetails = new ArrayList<>();
        List<List<String>> paths = findPaths(lat1, lon1, lat2, lon2);
        for(List<String> path : paths) {
            RotaBilgisi currentRoute = new RotaBilgisi();
            currentRoute.setAracId(aracIndex);
            currentRoute.setYolcuId(yolcuIndex);
            currentRoute.setOdemeId(odemeIndex);
            currentRoute.setBaslangicLatitude(lat1);
            currentRoute.setBaslangicLongitude(lon1);
            currentRoute.setBitisLatitude(lat2);
            currentRoute.setBitisLongitude(lon2);
            currentRoute.setYolDuraklari((ArrayList<String>) path);
            currentRoute.setYolUzunlugu(findPathLength(currentRoute));
            currentRoute.setYolUcreti(findPathCost(currentRoute));
            currentRoute.setYolSuresi(findPathTime(currentRoute));
            pathDetails.add(currentRoute);
        }
        return pathDetails;
    }

    private double findPathTime(RotaBilgisi currentRoute) {
        final double walkTimePerKm = Main.Yolcular.get(currentRoute.getYolcuId()).getWalkTimePerKm();
        if(findPathStartLength(currentRoute) > 3.0){
            currentRoute.setYolSuresi(currentRoute.getYolSuresi() + Main.Araclar.get(currentRoute.getAracId()).getTimePerKm() * findPathStartLength(currentRoute));
        } else {
            currentRoute.setYolSuresi(currentRoute.getYolSuresi() + walkTimePerKm * findPathStartLength(currentRoute));
        }
        for(int i = 0; i < currentRoute.getYolDuraklari().size() - 1; i++) {
            for(Baglanti durakId : Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getNextStops()){
                if(durakId.getStopId().equals(currentRoute.getYolDuraklari().get(i + 1))){
                    currentRoute.setYolSuresi(currentRoute.getYolSuresi() + durakId.getSure());
                    break;
                }
            }
            if(Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getTransfer().getTransferStopId().equals(currentRoute.getYolDuraklari().get(i + 1))){
                currentRoute.setYolSuresi(currentRoute.getYolSuresi() + Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getTransfer().getTransferSure());
            }
        }
        if(findPathEndLength(currentRoute) > 3.0){
            currentRoute.setYolSuresi(currentRoute.getYolSuresi() + Main.Araclar.get(currentRoute.getAracId()).getTimePerKm() * findPathEndLength(currentRoute));
        } else {
            currentRoute.setYolSuresi(currentRoute.getYolSuresi() + walkTimePerKm * findPathEndLength(currentRoute));
        }
        return currentRoute.getYolSuresi();
    }

    private double findPathCost(RotaBilgisi currentRoute) {
        for(int i = 0; i < currentRoute.getYolDuraklari().size() - 1; i++) {
            for(Baglanti durakId : Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getNextStops()){
                if(durakId.getStopId().equals(currentRoute.getYolDuraklari().get(i + 1))){
                    currentRoute.setYolUcreti(currentRoute.getYolUcreti() + durakId.getUcret());
                    break;
                }
            }
            if(Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getTransfer().getTransferStopId().equals(currentRoute.getYolDuraklari().get(i + 1))){
                currentRoute.setYolUcreti(currentRoute.getYolUcreti() + Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getTransfer().getTransferUcret());
            }
        }
        currentRoute.setYolUcreti(currentRoute.getYolUcreti() * Main.Yolcular.get(currentRoute.getYolcuId()).getDiscountPrice());
        if(findPathStartLength(currentRoute) > 3.0){
            currentRoute.setYolUcreti(currentRoute.getYolUcreti() + Main.Araclar.get(currentRoute.getAracId()).getOpeningFee() +
                    Main.Araclar.get(currentRoute.getAracId()).getCostPerKm() * findPathStartLength(currentRoute));
        }
        if(findPathEndLength(currentRoute) > 3.0){
            currentRoute.setYolUcreti(currentRoute.getYolUcreti() + Main.Araclar.get(currentRoute.getAracId()).getOpeningFee() +
                    Main.Araclar.get(currentRoute.getAracId()).getCostPerKm() * findPathEndLength(currentRoute));
        }
        return currentRoute.getYolUcreti();
    }

    private double findPathLength(RotaBilgisi currentRoute) {
        for(int i = 0; i < currentRoute.getYolDuraklari().size() - 1; i++) {
            for(Baglanti durakId : Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getNextStops()){
                if(durakId.getStopId().equals(currentRoute.getYolDuraklari().get(i + 1))){
                    currentRoute.setYolUzunlugu(currentRoute.getYolUzunlugu() + durakId.getMesafe());
                    break;
                }
            }
            if(Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getTransfer().getTransferStopId().equals(currentRoute.getYolDuraklari().get(i + 1))){
                currentRoute.setYolUzunlugu(currentRoute.getYolUzunlugu() + Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(i)).getTransfer().getTransferMesafe());
            }
        }
        currentRoute.setYolUzunlugu(currentRoute.getYolUzunlugu() + findPathStartLength(currentRoute) + findPathEndLength(currentRoute));
        return currentRoute.getYolUzunlugu();
    }

    private double findPathStartLength(RotaBilgisi currentRoute) {
        return distanceCalculator.calculateDistance(currentRoute.getBaslangicLatitude(), currentRoute.getBaslangicLongitude(),
                Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(0)).getLat(), Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(0)).getLon());
    }

    private double findPathEndLength(RotaBilgisi currentRoute) {
        return distanceCalculator.calculateDistance(currentRoute.getBitisLatitude(), currentRoute.getBitisLongitude(),
                Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(currentRoute.getYolDuraklari().size() - 1)).getLat(),
                Main.anaVeri.getDurakMap().get(currentRoute.getYolDuraklari().get(currentRoute.getYolDuraklari().size() - 1)).getLon());
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