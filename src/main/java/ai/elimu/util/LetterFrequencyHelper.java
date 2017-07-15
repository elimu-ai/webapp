package ai.elimu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import ai.elimu.model.content.StoryBook;

public class LetterFrequencyHelper {

    /**
     * Note: upper-case and lower-case letters are considered different letters.
     * E.g. 'A' and 'a'.
     */
    public static Map<String, Integer> getLetterFrequency(StoryBook storyBook) {
        Map<String, Integer> letterFrequencyMap = new HashMap<>();
        
        List<String> paragraphs = storyBook.getParagraphs();
        for (String paragraph : paragraphs) {
            List<String> letters = LetterExtractionHelper.getLetters(paragraph);
            for (String letter : letters) {
                if (!letterFrequencyMap.containsKey(letter)) {
                    letterFrequencyMap.put(letter, 1);
                } else {
                    letterFrequencyMap.put(letter, letterFrequencyMap.get(letter) + 1);
                }
            }
        }
        
        return sortByValue(letterFrequencyMap);
    }
    
    private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            
            public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });
    	
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
