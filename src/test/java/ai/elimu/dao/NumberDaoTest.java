package ai.elimu.dao;

import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class NumberDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Test
    public void testStoreWithMultipleNumberWords() {
        Word word1 = new Word();
        wordDao.create(word1);
        
        Word word2 = new Word();
        wordDao.create(word2);
        
        List<Word> numberWords = new ArrayList<>();
        numberWords.add(word1);
        numberWords.add(word2);
        
        Number number = new Number();
        number.setWords(numberWords);
        numberDao.create(number);
        
        assertThat(number.getWords().size(), is(2));
    }
}
