package com.project.util;

public class HaversineDistance implements DistanceCalculator {
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int dunyaYaricap = 6371;

        double fi1 = Math.toRadians(lat1);
        double fi2 = Math.toRadians(lat2);
        double deltaFi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(deltaFi / 2), 2) + Math.cos(fi1) * Math.cos(fi2) * Math.pow(Math.sin(deltaLambda / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return dunyaYaricap*c;
    }
}
