package ai.elimu.util;

import ai.elimu.model.enums.Language;

public class IpaToLetterConverter {

    public static String getLetters(String ipaValue, Language language) {
        String letter = null;
        
        if (language == Language.EN) {
            if ("i".equals(ipaValue)) {
                return "i";
            } else if ("t".equals(ipaValue)) {
                return "t";
            } else if ("ð".equals(ipaValue)) {
                return "th";
            }
            // TODO
        } else if (language == Language.SW) {
            // TODO
        }
        
        return letter;
    }
}
