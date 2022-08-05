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
    public void testReadMostRecent() {
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        assertThat(wordContributionEvents.isEmpty(), is(true));
        
        Contributor contributor = new Contributor();
        contributorDao.create(contributor);
        
        Word word = new Word();
        wordDao.create(word);
        
        WordContributionEvent wordContributionEvent = new WordContributionEvent();
        wordContributionEvent.setContributor(contributor);
        wordContributionEvent.setWord(word);
        wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
        wordContributionEvent.setTime(Calendar.getInstance());
        wordContributionEvent.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent);
        
        wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        assertThat(wordContributionEvents.isEmpty(), is(false));
        assertThat(wordContributionEvents.size(), is(1));
        
        // Clean up content stored in the database, so that it won't affect the other tests
        wordContributionEventDao.delete(wordContributionEvent);
    }
    
    @Test
    public void testReadMostRecentPerWord() {
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.isEmpty(), is(true));
        
        Contributor contributor1 = new Contributor();
        contributorDao.create(contributor1);
        
        Word word1 = new Word();
        wordDao.create(word1);
        
        WordContributionEvent wordContributionEvent1 = new WordContributionEvent();
        wordContributionEvent1.setContributor(contributor1);
        wordContributionEvent1.setWord(word1);
        wordContributionEvent1.setRevisionNumber(word1.getRevisionNumber());
        wordContributionEvent1.setTime(Calendar.getInstance());
        wordContributionEvent1.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent1);
        
        wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.isEmpty(), is(false));
        assertThat(wordContributionEvents.size(), is(1));
        assertThat(wordContributionEvents.get(0).getWord().getId(), is(wordContributionEvent1.getWord().getId()));
        
        Word word2 = new Word();
        wordDao.create(word2);
        
        WordContributionEvent wordContributionEvent2 = new WordContributionEvent();
        wordContributionEvent2.setContributor(contributor1);
        wordContributionEvent2.setWord(word2);
        wordContributionEvent2.setRevisionNumber(word2.getRevisionNumber());
        wordContributionEvent2.setTime(Calendar.getInstance());
        wordContributionEvent2.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent2);
        
        wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.isEmpty(), is(false));
        assertThat(wordContributionEvents.size(), is(2));
        assertThat(wordContributionEvents.get(0).getWord().getId(), is(wordContributionEvent1.getWord().getId()));
        assertThat(wordContributionEvents.get(1).getWord().getId(), is(wordContributionEvent2.getWord().getId()));
        
        // Re-use a word (word2) that was used in a previous contribution event
        WordContributionEvent wordContributionEvent3 = new WordContributionEvent();
        wordContributionEvent3.setContributor(contributor1);
        wordContributionEvent3.setWord(word2);
        wordContributionEvent3.setRevisionNumber(word2.getRevisionNumber());
        wordContributionEvent3.setTime(Calendar.getInstance());
        wordContributionEvent3.setTimeSpentMs(10_000L);
        wordContributionEventDao.create(wordContributionEvent3);
        
        // The number of contribution events returned should not increase
        wordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        assertThat(wordContributionEvents.isEmpty(), is(false));
        assertThat(wordContributionEvents.size(), is(2));
        assertThat(wordContributionEvents.get(0).getWord().getId(), is(wordContributionEvent1.getWord().getId()));
        assertThat(wordContributionEvents.get(1).getWord().getId(), is(wordContributionEvent3.getWord().getId()));
        
        // Clean up content stored in the database, so that it won't affect the other tests
        wordContributionEventDao.delete(wordContributionEvent1);
        wordContributionEventDao.delete(wordContributionEvent2);
        wordContributionEventDao.delete(wordContributionEvent3);
    }
}
