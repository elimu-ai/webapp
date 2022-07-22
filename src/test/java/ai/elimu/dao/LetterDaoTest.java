package ai.elimu.dao;

import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Letter;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LetterDaoTest extends TestCase {

    @Autowired
    LetterDao letterDao;

    @Test
    public void testReadAllOrdered() {
        List<Letter> lettersExpected = new ArrayList<>();
        lettersExpected.add(getLetter("A"));
        lettersExpected.add(getLetter("B"));
        lettersExpected.add(getLetter("D"));
        lettersExpected.add(getLetter("M"));

        letterDao.create(getLetter("M"));
        letterDao.create(getLetter("B"));
        letterDao.create(getLetter("D"));
        letterDao.create(getLetter("A"));

        List<Letter> lettersActual = letterDao.readAllOrdered();

        Assert.assertArrayEquals(lettersExpected.stream().map(Letter::getText).toArray(), lettersActual.stream().map(Letter::getText).toArray());
    }

    @Test
    public void testReadAllOrderedByUsage() {
        List<Letter> lettersExpected = new ArrayList<>();
        lettersExpected.add(getLetter("R"));
        lettersExpected.add(getLetter("Z"));
        lettersExpected.add(getLetter("P"));
        lettersExpected.add(getLetter("N"));

        letterDao.create(getLetterWithUsageCount("Z", 50));
        letterDao.create(getLetterWithUsageCount("P", 30));
        letterDao.create(getLetterWithUsageCount("R", 60));
        letterDao.create(getLetterWithUsageCount("N", 20));

        List<Letter> lettersActual = letterDao.readAllOrderedByUsage();

        Assert.assertArrayEquals(lettersExpected.stream().map(Letter::getText).toArray(), lettersActual.stream().filter(i -> i.getUsageCount() != 0).map(Letter::getText).toArray());
    }

    @Test
    public void testReadByText() {
        letterDao.create(getLetter("T"));

        assertTrue("T".equals(letterDao.readByText("T").getText()));
        assertNull(letterDao.readByText(""));
    }

    private Letter getLetter(String text) {
        Letter letter = new Letter();
        letter.setText(text);
        return letter;
    }

    private Letter getLetterWithUsageCount(String text, Integer usageCount) {
       Letter letter = getLetter(text);
       letter.setUsageCount(usageCount);
       return letter;
    }

}