package ai.elimu.util;

import ai.elimu.model.enums.Locale;

public class IpaToLetterConverter {

    public static String getLetters(String ipaValue, Locale locale) {
        String letter = null;
        
        if (locale == Locale.AR) {
            // TODO
        } else if (locale == Locale.EN) {
            if ("i".equals(ipaValue)) {
                return "i";
            } else if ("t".equals(ipaValue)) {
                return "t";
            } else if ("ð".equals(ipaValue)) {
                return "th";
            }
            // TODO
        } else if (locale == Locale.ES) {
            // TODO
        } else if (locale == Locale.SW) {
            // TODO
        }
        
        return letter;
    }
}
