package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LetterFrequencyHelper {

    /**
     * Note: upper-case and lower-case letters are considered different letters.
     * E.g. 'A' and 'a'.
     */
    public static Map<String, Integer> getLetterFrequency(List<String> paragraphs, Language language) {
        Map<String, Integer> letterFrequencyMap = new HashMap<>();
        
        for (String paragraph : paragraphs) {
            List<String> letters = LetterExtractionHelper.getLetters(paragraph, language);
            letters.forEach(letter -> letterFrequencyMap.put(letter, letterFrequencyMap.getOrDefault(letter, 0) + 1));
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
