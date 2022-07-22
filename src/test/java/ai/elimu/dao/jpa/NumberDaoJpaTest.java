package ai.elimu.dao.jpa;

import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Number;
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
public class NumberDaoJpaTest extends TestCase {

    @Autowired
    NumberDao numberDao;

    @Test
    public void testReadAllOrdered() {
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