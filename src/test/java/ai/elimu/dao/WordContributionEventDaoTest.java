package ai.elimu.dao;

import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class WordContributionEventDaoTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;

    @Test
    public void testReadAllOrderedByTimeDesc() {
        logger.info("testReadAllOrderedByTimeDesc");

        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAllOrderedByTimeDesc();
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());

        Contributor contributor = new Contributor();
        contributorDao.create(contributor);

        Word word1 = new Word();
        word1.setText("word1");
        wordDao.create(word1);

        WordContributionEvent wordContributionEvent1 = new WordContributionEvent();
        wordContributionEvent1.setContributor(contributor);
        wordContributionEvent1.setWord(word1);
        wordContributionEvent1.setRevisionNumber(word1.getRevisionNumber());
        wordContributionEvent1.setTime(Calendar.getInstance());
        wordContributionEvent1.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent1);

        Word word2 = new Word();
        word2.setText("word2");
        wordDao.create(word2);
        
        WordContributionEvent wordContributionEvent2 = new WordContributionEvent();
        wordContributionEvent2.setContributor(contributor);
        wordContributionEvent2.setWord(word2);
        wordContributionEvent2.setRevisionNumber(word2.getRevisionNumber());
        Calendar calendar1HourFromNow = Calendar.getInstance();
        calendar1HourFromNow.add(Calendar.HOUR, 1);
        wordContributionEvent2.setTime(calendar1HourFromNow);
        wordContributionEvent2.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent2);

        wordContributionEvents = wordContributionEventDao.readAllOrderedByTimeDesc();
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());

        WordContributionEvent event1stInList = wordContributionEvents.get(0);
        logger.info("event1stInList time: " + event1stInList.getTime().getTime());
        WordContributionEvent event2ndInList = wordContributionEvents.get(1);
        logger.info("event2ndInList time: " + event2ndInList.getTime().getTime());
        assertTrue(event1stInList.getTime().after(event2ndInList.getTime()));
    }

    @Test
    public void testReadAll_Word() {
        logger.info("testReadAll_Word");

        Word word1 = new Word();
        word1.setText("word1");
        wordDao.create(word1);
        logger.info("word1.getId(): " + word1.getId());

        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(word1);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            logger.info("wordContributionEvent.getWord().getId(): " + wordContributionEvent.getWord().getId());
        }

        Contributor contributor = new Contributor();
        contributorDao.create(contributor);

        WordContributionEvent wordContributionEvent1 = new WordContributionEvent();
        wordContributionEvent1.setContributor(contributor);
        wordContributionEvent1.setWord(word1);
        wordContributionEvent1.setRevisionNumber(word1.getRevisionNumber());
        wordContributionEvent1.setTime(Calendar.getInstance());
        wordContributionEvent1.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent1);

        wordContributionEvents = wordContributionEventDao.readAll(word1);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            logger.info("wordContributionEvent.getWord().getId(): " + wordContributionEvent.getWord().getId());
        }

        Word word2 = new Word();
        word2.setText("word2");
        wordDao.create(word2);
        logger.info("word2.getId(): " + word2.getId());
        
        WordContributionEvent wordContributionEvent2 = new WordContributionEvent();
        wordContributionEvent2.setContributor(contributor);
        wordContributionEvent2.setWord(word2);
        wordContributionEvent2.setRevisionNumber(word2.getRevisionNumber());
        wordContributionEvent2.setTime(Calendar.getInstance());
        wordContributionEvent2.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent2);

        wordContributionEvents = wordContributionEventDao.readAll(word1);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            logger.info("wordContributionEvent.getWord().getId(): " + wordContributionEvent.getWord().getId());
        }

        assertTrue(wordContributionEvents.size() == 1);
        Word word1stInList = wordContributionEvents.get(0).getWord();
        assertTrue(word1stInList.getId().equals(word1.getId()));
    }

    @Test
    public void testReadAll_Contributor() {
        logger.info("testReadAll_Contributor");

        Contributor contributor1 = new Contributor();
        contributorDao.create(contributor1);
        logger.info("contributor1.getId(): " + contributor1.getId());
        
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(contributor1);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            logger.info("wordContributionEvent.getContributor().getId(): " + wordContributionEvent.getContributor().getId());
        }
        
        Word word = new Word();
        word.setText("word1");
        wordDao.create(word);

        WordContributionEvent wordContributionEvent1 = new WordContributionEvent();
        wordContributionEvent1.setContributor(contributor1);
        wordContributionEvent1.setWord(word);
        wordContributionEvent1.setRevisionNumber(word.getRevisionNumber());
        wordContributionEvent1.setTime(Calendar.getInstance());
        wordContributionEvent1.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent1);

        wordContributionEvents = wordContributionEventDao.readAll(contributor1);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            logger.info("wordContributionEvent.getContributor().getId(): " + wordContributionEvent.getContributor().getId());
        }

        Contributor contributor2 = new Contributor();
        contributorDao.create(contributor2);
        logger.info("contributor2.getId(): " + contributor2.getId());
        
        WordContributionEvent wordContributionEvent2 = new WordContributionEvent();
        wordContributionEvent2.setContributor(contributor2);
        wordContributionEvent2.setWord(word);
        wordContributionEvent2.setRevisionNumber(word.getRevisionNumber());
        wordContributionEvent2.setTime(Calendar.getInstance());
        wordContributionEvent2.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent2);

        wordContributionEvents = wordContributionEventDao.readAll(contributor1);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            logger.info("wordContributionEvent.getContributor().getId(): " + wordContributionEvent.getContributor().getId());
        }

        assertTrue(wordContributionEvents.size() == 1);
        Contributor contributor1stInList = wordContributionEvents.get(0).getContributor();
        assertTrue(contributor1stInList.getId().equals(contributor1.getId()));
    }
    
    @Test
    public void testReadMostRecent() {
        logger.info("testReadMostRecent");

        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        int numberOfWordContributionEventsBefore = wordContributionEvents.size();
        logger.info("numberOfWordContributionEventsBefore: " + numberOfWordContributionEventsBefore);
        
        Contributor contributor = new Contributor();
        contributorDao.create(contributor);
        
        Word word1 = new Word();
        word1.setText("word1");
        wordDao.create(word1);
        
        WordContributionEvent wordContributionEvent1 = new WordContributionEvent();
        wordContributionEvent1.setContributor(contributor);
        wordContributionEvent1.setWord(word1);
        wordContributionEvent1.setRevisionNumber(word1.getRevisionNumber());
        wordContributionEvent1.setTime(Calendar.getInstance());
        wordContributionEvent1.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent1);
        
        wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        int numberOfWordContributionEventsAfter = wordContributionEvents.size();
        logger.info("numberOfWordContributionEventsAfter: " + numberOfWordContributionEventsAfter);

        if (numberOfWordContributionEventsBefore < 10) {
            assertEquals(numberOfWordContributionEventsBefore + 1,
                numberOfWordContributionEventsAfter);
        } else {
            assertEquals(10, numberOfWordContributionEventsAfter);
        }
    }

    @Test
    public void testReadCount_Contributor() {
        logger.info("testReadCount_Contributor");

        Contributor contributor1 = new Contributor();
        contributorDao.create(contributor1);
        logger.info("contributor1.getId(): " + contributor1.getId());
        
        Long wordContributionEventCount = wordContributionEventDao.readCount(contributor1);
        logger.info("wordContributionEventCount: " + wordContributionEventCount);
        
        Word word = new Word();
        word.setText("word1");
        wordDao.create(word);

        WordContributionEvent wordContributionEvent1 = new WordContributionEvent();
        wordContributionEvent1.setContributor(contributor1);
        wordContributionEvent1.setWord(word);
        wordContributionEvent1.setRevisionNumber(word.getRevisionNumber());
        wordContributionEvent1.setTime(Calendar.getInstance());
        wordContributionEvent1.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent1);

        wordContributionEventCount = wordContributionEventDao.readCount(contributor1);
        logger.info("wordContributionEventCount: " + wordContributionEventCount);

        Contributor contributor2 = new Contributor();
        contributorDao.create(contributor2);
        logger.info("contributor2.getId(): " + contributor2.getId());
        
        WordContributionEvent wordContributionEvent2 = new WordContributionEvent();
        wordContributionEvent2.setContributor(contributor2);
        wordContributionEvent2.setWord(word);
        wordContributionEvent2.setRevisionNumber(word.getRevisionNumber());
        wordContributionEvent2.setTime(Calendar.getInstance());
        wordContributionEvent2.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent2);

        wordContributionEventCount = wordContributionEventDao.readCount(contributor1);
        logger.info("wordContributionEventCount: " + wordContributionEventCount);

        assertTrue(wordContributionEventCount == 1);
    }
}
