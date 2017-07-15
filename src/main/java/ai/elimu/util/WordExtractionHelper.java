package ai.elimu.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class WordExtractionHelper {

    public static List<String> getWords(String paragraph) {
        if (StringUtils.isBlank(paragraph)) {
            throw new IllegalArgumentException("The paragraph cannot be empty");
        }
        
        List<String> words = new ArrayList<>();
        
        paragraph = paragraph.trim();
        String[] paragraphParts = paragraph.split(" ");
        for (int i = 0; i < paragraphParts.length; i++) {
            String paragraphPart = paragraphParts[i];
            String word = paragraphPart
                    .replace(",", "")
                    .replace("\"", "")
                    .replace("“", "")
                    .replace("”", "")
                    .replace(".", "")
                    .replace("!", "")
                    .replace("?", "");
            if (StringUtils.isNotBlank(word)) {
                words.add(word);
            }
        }
        
        return words;
    }
}
