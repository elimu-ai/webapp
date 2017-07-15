package ai.elimu.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class LetterExtractionHelper {

    public static List<String> getLetters(String paragraph) {
        if (StringUtils.isBlank(paragraph)) {
            throw new IllegalArgumentException("The paragraph cannot be empty");
        }
        
        List<String> letters = new ArrayList<>();
        
        List<String> words = WordExtractionHelper.getWords(paragraph);
        for (String word : words) {
            char[] letterCharacters = word.toCharArray();
            for (char letterCharacter : letterCharacters) {
                letters.add(String.valueOf(letterCharacter));
            }
        }
        
        return letters;
    }
}
