package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordFrequencyHelper {

    /**
     * Note: upper-case and lower-case words are considered similar words.
     * E.g. "Word" and "word" are counted twice as "word".
     */
    public static Map<String, Integer> getWordFrequency(List<String> paragraphs, Language language) {
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        
        for (String paragraph : paragraphs) {
            List<String> words = WordExtractionHelper.getWords(paragraph, language);
            words.forEach(word -> wordFrequencyMap.put(word.toLowerCase(), wordFrequencyMap.getOrDefault(word.toLowerCase(), 0) + 1));
        }
        
        return sortByValue(wordFrequencyMap);
    }
    
    private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        list.sort((m1, m2) -> (m2.getValue()).compareTo(m1.getValue()));
        
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
