package exception;

import util.enums.Language;

/**
 * Used for getting language of the wrong product translation
 */
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
