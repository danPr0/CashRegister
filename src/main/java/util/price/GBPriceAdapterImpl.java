package util.price;

public class GBPriceAdapterImpl implements PriceAdapter {
    @Override
    public double convertPrice(double priceInUAH) {
        return priceInUAH / 40;
    }
}
