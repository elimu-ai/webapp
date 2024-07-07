package ai.elimu.dao;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Word;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class NumberDaoTest {
    
    private Logger logger = LogManager.getLogger();

    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Test
    public void testCreate_multipleWords() {
        Word wordTwenty = new Word();
        wordTwenty.setText("twenty");
        wordDao.create(wordTwenty);
        
        Word wordFour = new Word();
        wordFour.setText("four");
        wordDao.create(wordFour);
        
        List<Word> numberWords = new ArrayList<>();
        numberWords.add(wordTwenty);
        numberWords.add(wordFour);
        
        Number number24 = new Number();
        number24.setValue(24);
        number24.setWords(numberWords);
        numberDao.create(number24);
        
        assertThat(numberDao.read(number24.getId()).getWords().isEmpty(), is(false));
        assertThat(numberDao.read(number24.getId()).getWords().size(), is(2));
        assertThat(numberDao.read(number24.getId()).getWords().get(0).getText(), is("twenty"));
        assertThat(numberDao.read(number24.getId()).getWords().get(1).getText(), is("four"));
    }

    @Test
    public void testReadAllOrdered() {
        numberDao.create(getNumber(22));
        numberDao.create(getNumber(1));
        numberDao.create(getNumber(100));
        numberDao.create(getNumber(23));
        numberDao.create(getNumber(90));

        List<Number> allNumbers = numberDao.readAllOrdered();
        assertThat(allNumbers.isEmpty(), is(false));
        assertThat(allNumbers.size() >= 5, is(true));
        Number previousNumber = null;
        for (Number number: allNumbers) {
            logger.info("number.getValue(): " + number.getValue());
            if (previousNumber != null) {
                assertTrue(number.getValue() > previousNumber.getValue());
            }
            previousNumber = number;
        }
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
