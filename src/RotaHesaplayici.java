public class RotaHesaplayici {
    public static int findNearestStop(double lat, double lon) {
        int enKucukIndex = 0;
        int index = 0;
        for(Durak durak : Main.anaVeri.getDuraklar()){
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

        double a = Math.pow(Math.sin(deltaPhi / 2), 2)
                + Math.cos(phi1) * Math.cos(phi2)
                * Math.pow(Math.sin(deltaLambda / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R*c;
    }
}
