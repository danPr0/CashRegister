package util.enums;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Language support enum
 */
public enum Language {
    ua {
        public String getLocalPrice(double price) {
            return NumberFormat.getCurrencyInstance(new Locale("uk", "UA")).format(price);
        }
    },
    en {
        public String getLocalPrice(double price) {
            return NumberFormat.getCurrencyInstance(new Locale("en", "GB")).format(price);
        }
    };

    public abstract String getLocalPrice(double price);

    public static Language getLanguage(HttpServletRequest request) {
        return Language.valueOf(request.getSession().getAttribute("lang").toString());
    }
}
