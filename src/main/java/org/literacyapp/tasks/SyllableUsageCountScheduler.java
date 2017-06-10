package org.literacyapp.tasks;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.literacyapp.dao.SyllableDao;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.model.content.Syllable;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.util.SyllableFrequencyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each syllable. Lower-case 
 * and upper-case variants are considered as two different syllables, e.g. 'a' and 'A'.
 */
@Service
public class SyllableUsageCountScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private SyllableDao syllableDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Scheduled(cron="00 30 07 * * *") // At 07:30 every morning
    public synchronized void execute() {
        logger.info("execute");
        
        for (Locale locale : Locale.values()) {
            logger.info("Calculating usage count for Syllables with locale " + locale);
            
            Map<String, Integer> syllableFrequencyMap = new HashMap<>();
            
            List<StoryBook> storyBooks = storyBookDao.readAllOrdered(locale);
            logger.info("storyBooks.size(): " + storyBooks.size());
            for (StoryBook storyBook : storyBooks) {
                logger.info("storyBook.getTitle(): " + storyBook.getTitle());
                
                Map<String, Integer> syllableFrequencyMapForBook = SyllableFrequencyHelper.getSyllableFrequency(storyBook);
                for (String key : syllableFrequencyMapForBook.keySet()) {
                    String syllableText = key;
                    int syllableFrequency = syllableFrequencyMapForBook.get(key);
                    if (!syllableFrequencyMap.containsKey(syllableText)) {
                        syllableFrequencyMap.put(syllableText, syllableFrequency);
                    } else {
                        syllableFrequencyMap.put(syllableText, syllableFrequencyMap.get(syllableText) + syllableFrequency);
                    }
                }
            }
            
            logger.info("syllableFrequencyMap: " + syllableFrequencyMap);
            
            for (String key : syllableFrequencyMap.keySet()) {
                String syllableText = key;
                Syllable existingSyllable = syllableDao.readByText(locale, syllableText);
                if (existingSyllable == null) {
                    Syllable syllable = new Syllable();
                    syllable.setLocale(locale);
                    syllable.setTimeLastUpdate(Calendar.getInstance());
                    syllable.setText(syllableText);
                    syllableDao.create(syllable);
                } else {
                    existingSyllable.setUsageCount(syllableFrequencyMap.get(syllableText));
                    syllableDao.update(existingSyllable);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
