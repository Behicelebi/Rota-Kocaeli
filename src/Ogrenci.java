public class Ogrenci implements Yolcu{

    @Override
    public double getDiscountPrice() {
        return 10.0;
    }

    @Override
    public String getClassName() {
        return "Öğrenci";
    }
}
