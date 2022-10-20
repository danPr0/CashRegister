package exception;

import util.enums.Language;

public class ProductTranslationException extends Exception {
    Language errorTranslationLang;

    public ProductTranslationException(String msg, Language lang) {
        super(msg);
        this.errorTranslationLang = lang;
    }

    public Language getErrorTranslationLang() {
        return errorTranslationLang;
    }
}
