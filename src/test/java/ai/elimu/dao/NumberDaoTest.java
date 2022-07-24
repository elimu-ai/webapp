package ai.elimu.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import org.apache.logging.log4j.LogManager;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NumberDaoTest {
    
    private Logger logger = LogManager.getLogger();
    
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

    @Test
    public void aTestReadAllOrdered() {
        List<Number> expectedNumbers = new ArrayList<>();
        expectedNumbers.add(getNumber(1));
        expectedNumbers.add(getNumber(22));
        expectedNumbers.add(getNumber(23));
        expectedNumbers.add(getNumber(90));
        expectedNumbers.add(getNumber(100));

        numberDao.create(getNumber(22));
        numberDao.create(getNumber(1));
        numberDao.create(getNumber(100));
        numberDao.create(getNumber(23));
        numberDao.create(getNumber(90));

        List<Number> actualNumbers = numberDao.readAllOrdered();

        Assert.assertArrayEquals(expectedNumbers.stream().map(Number::getValue).toArray(), actualNumbers.stream().map(Number::getValue).toArray());
    }

    @Test
    public void testReadByValue() {
        numberDao.create(getNumber(25));

        assertTrue(numberDao.readByValue(25).getValue() == 25);
        assertNull(numberDao.readByValue(11));
    }

    private Number getNumber(Integer value) {
        Number number = new Number();
        number.setValue(value);
        return number;
    }

}
