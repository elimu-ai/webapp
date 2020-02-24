package ai.elimu.util;

import ai.elimu.model.enums.Language;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordFrequencyHelper {

    /**
     * Note: upper-case and lower-case words are considered different words.
     * E.g. "Word" and "word".
     */
    public static Map<String, Integer> getWordFrequency(List<String> paragraphs, Language language) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        
        for (String paragraph : paragraphs) {
            List<String> words = WordExtractionHelper.getWords(paragraph, language);
            for (String word : words) {
                if (!wordFrequencyMap.containsKey(word)) {
                    wordFrequencyMap.put(word, 1);
                } else {
                    wordFrequencyMap.put(word, wordFrequencyMap.get(word) + 1);
                }
            }
        }
        
        return sortByValue(wordFrequencyMap);
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
