package ai.elimu.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.content.allophone.SoundType;
import ai.elimu.web.content.allophone.AllophoneListController;

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
    
    public static SoundType getSoundType(String ipaValue, Locale locale) {
        SoundType soundType = null;
        
        if (locale == Locale.AR) {
            // TODO
        } else if (locale == Locale.EN) {
            if ("i".equals(ipaValue)) {
                return soundType.VOWEL;
            } else if ("t".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ð".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("n".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("d".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ɪ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("æ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("u".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("ɛɪ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("s".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("r".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("m".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ʌ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("l".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ɔ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("z".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("h".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ɛ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("k".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ɑ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("g".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ɑɪ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("b".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ə".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("p".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ʊ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("f".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("aʊ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("j".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ʃ".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("v".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("əʊ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("ŋ".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("θ".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("dʒ".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ɔɪ".equals(ipaValue)) {
                return SoundType.VOWEL;
            } else if ("r̩".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("tʃ".equals(ipaValue)) {
                return SoundType.CONSONANT;
            } else if ("ʒ".equals(ipaValue)) {
                return SoundType.CONSONANT;
            }
        } else if (locale == Locale.ES) {
            // TODO
        } else if (locale == Locale.SW) {
            // TODO
        }
        
        return soundType;
    }
}
