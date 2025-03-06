public class KrediKarti implements Odeme{
    @Override
    public double getDiscountPrice() {
        return 10.0;
    }

    @Override
    public String getClassName() {
        return "Kredi Kartı";
    }
}
