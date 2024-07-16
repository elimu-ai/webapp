package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class WordExtractionHelper {

    public static List<String> getWords(String paragraph, Language language) {
        if (StringUtils.isBlank(paragraph)) {
            throw new IllegalArgumentException("The paragraph cannot be empty");
        }
        
        List<String> words = new ArrayList<>();
        
        paragraph = paragraph.trim();
        String[] paragraphParts = paragraph.split(" ");
        for (int i = 0; i < paragraphParts.length; i++) {
            String paragraphPart = paragraphParts[i];
            
            // Remove characters that are not Letters
            String word = paragraphPart
                    .replace(",", "")
                    .replace("\"", "")
                    .replace("“", "")
                    .replace("”", "")
                    .replace(".", "")
                    .replace("!", "")
                    .replace("?", "")
                    .replace(":", "")
                    .replace("(", "")
                    .replace(")", "");
            if (language == Language.HIN) {
                word = word
                        .replace("।", ""); // See https://en.wikipedia.org/wiki/Danda
            }
            
            if (StringUtils.isNotBlank(word)) {
                words.add(word);
            }
        }
        
        return words;
    }
}
