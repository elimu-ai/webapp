package org.literacyapp.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.web.content.allophone.AllophoneListController;

public class PhoneticsHelper {

    /**
     * Extract individual speech sounds (Allophones) from the phonetics of a Word.
     * </ p>
     * Example: /kæt/ --> /k/, /æ/, /t/
     */
    public static List<String> getAllophones(Word word) {
        List<String> allophones = new ArrayList<>();
        
        // E.g. "kæt"
        String phonetics = word.getPhonetics();
        
        // Ignore diacritics
        phonetics = phonetics
                .replace("ˈ", "")
                .replace("ˌ", "");
        
        // Extract speech sounds
        while (StringUtils.isNotBlank(phonetics)) {
            int phoneticsLengthBeforeExtraction = phonetics.length();
            
            if (word.getLocale() == Locale.AR) {
                // TODO
            } else if (word.getLocale() == Locale.EN) {
                String[][] allophonesArrayEN = AllophoneListController.allophonesArrayEN;
                for (String[] allophoneRow : allophonesArrayEN) {
                    String allophoneIpa = allophoneRow[0];
                    if (phonetics.startsWith(allophoneIpa)) {
                        allophones.add(allophoneIpa);
                        phonetics = phonetics.substring(allophoneIpa.length());
                        break;
                    }
                }
            } else if (word.getLocale() == Locale.ES) {
                // TODO
            } else if (word.getLocale() == Locale.SW) {
                String[][] allophonesArraySW = AllophoneListController.allophonesArraySW;
                for (String[] allophoneRow : allophonesArraySW) {
                    String allophoneIpa = allophoneRow[0];
                    if (phonetics.startsWith(allophoneIpa)) {
                        allophones.add(allophoneIpa);
                        phonetics = phonetics.substring(allophoneIpa.length());
                        break;
                    }
                }
            }
            
            if (phoneticsLengthBeforeExtraction == phonetics.length()) {
                break;
            }
        }
        
        return allophones;
    }
}
