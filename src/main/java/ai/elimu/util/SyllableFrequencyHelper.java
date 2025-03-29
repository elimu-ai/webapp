package ai.elimu.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.Language;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyllableFrequencyHelper {

    public static Map<String, Integer> getSyllableFrequency(List<String> paragraphs, Language language) {
        Map<String, Integer> syllableFrequencyMap = new HashMap<>();
        
        for (String paragraph : paragraphs) {
            log.info("paragraph: " + paragraph);
            List<String> words = WordExtractionHelper.getWords(paragraph, language);
            for (String wordInParagraph : words) {
                log.info("wordInParagraph: " + wordInParagraph);
                Word word = new Word();
                word.setText(wordInParagraph);
                List<String> syllables = SyllableHelper.getSyllables(word, language);
                log.info("syllables.size(): " + syllables.size());
                for (String syllable : syllables) {
                    log.info("syllable: " + syllable);
                    syllableFrequencyMap.put(syllable, syllableFrequencyMap.getOrDefault(syllable, 0) + 1);
                }
            }
        }
        
        return sortByValue(syllableFrequencyMap);
    }
    
    private static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        list.sort((m1, m2) -> (m2.getValue()).compareTo(m1.getValue()));
        
        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
