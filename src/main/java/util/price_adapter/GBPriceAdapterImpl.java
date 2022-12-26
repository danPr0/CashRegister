package util.price_adapter;

public class GBPriceAdapterImpl implements PriceAdapter {
    private static final int EXCHANGE_RATE = 40;

    @Override
    public double convertPrice(double priceInUAH) {
        return priceInUAH / EXCHANGE_RATE;
    }
}
