package org.literacyapp.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.content.Word;

public class SyllableFrequencyHelper {

    /**
     * Note: upper-case and lower-case syllables are considered different syllables.
     * E.g. 'A' and 'a'.
     */
    public static Map<String, Integer> getSyllableFrequency(StoryBook storyBook) {
        Map<String, Integer> syllableFrequencyMap = new HashMap<>();
        
        List<String> paragraphs = storyBook.getParagraphs();
        for (String paragraph : paragraphs) {
            List<String> words = WordExtractionHelper.getWords(paragraph);
            for (String wordInParagraph : words) {
                Word word = new Word();
                word.setText(wordInParagraph);
                List<String> syllables = SyllableHelper.getSyllables(word);
                for (String syllable : syllables) {
                    if (!syllableFrequencyMap.containsKey(syllable)) {
                        syllableFrequencyMap.put(syllable, 1);
                    } else {
                        syllableFrequencyMap.put(syllable, syllableFrequencyMap.get(syllable) + 1);
                    }
                }
            }
        }
        
        return sortByValue(syllableFrequencyMap);
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
