package util.price_adapter;

import util.enums.Language;

public class PriceAdapterFactory {
    public PriceAdapter getAdapter(Language language) {
        if (language.equals(Language.en))
            return new GBPriceAdapterImpl();
        else return (price) -> price;
    }
}
