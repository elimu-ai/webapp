package ai.elimu.dao;

import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import java.util.Calendar;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
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
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAllOrderedByTimeDesc();
        logger.info("wordContributionEvents.size():" + wordContributionEvents.size());

        // TODO
    }
    
    @Test
    public void testReadMostRecent() {
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        int numberOfWordContributionEventsBefore = wordContributionEvents.size();
        
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
        assertThat(numberOfWordContributionEventsAfter, is(numberOfWordContributionEventsBefore + 1));
    }
    
    @Test
    public void testReadMostRecentPerWord() {
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        int numberOfWordContributionEventsBefore = wordContributionEvents.size();
        
        Contributor contributor = new Contributor();
        contributorDao.create(contributor);
        
        Word word2 = new Word();
        word2.setText("word2");
        wordDao.create(word2);
        
        WordContributionEvent wordContributionEvent2 = new WordContributionEvent();
        wordContributionEvent2.setContributor(contributor);
        wordContributionEvent2.setWord(word2);
        wordContributionEvent2.setRevisionNumber(word2.getRevisionNumber());
        wordContributionEvent2.setTime(Calendar.getInstance());
        wordContributionEvent2.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent2);
        
        wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.size(), is(numberOfWordContributionEventsBefore + 1));
        
        Word word3 = new Word();
        word3.setText("word3");
        wordDao.create(word3);
        
        WordContributionEvent wordContributionEvent3 = new WordContributionEvent();
        wordContributionEvent3.setContributor(contributor);
        wordContributionEvent3.setWord(word3);
        wordContributionEvent3.setRevisionNumber(word3.getRevisionNumber());
        wordContributionEvent3.setTime(Calendar.getInstance());
        wordContributionEvent3.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent3);
        
        wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.size(), is(numberOfWordContributionEventsBefore + 2));
        
        // Re-use a word (word3) that was used in a previous contribution event
        WordContributionEvent wordContributionEvent4 = new WordContributionEvent();
        wordContributionEvent4.setContributor(contributor);
        wordContributionEvent4.setWord(word3);
        wordContributionEvent4.setRevisionNumber(word3.getRevisionNumber());
        wordContributionEvent4.setTime(Calendar.getInstance());
        wordContributionEvent4.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent4);
        
        // The number of contribution events returned should not increase
        wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.size(), is(numberOfWordContributionEventsBefore + 2));
    }
}
