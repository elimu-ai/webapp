package ai.elimu.dao;

import ai.elimu.dao.ContributorDao;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class ContributorDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
	
    @Autowired
    private ContributorDao contributorDao;

    @Ignore
    @Test(expected=ConstraintViolationException.class)
    public void testConstraintViolation() {
        Contributor contributor = new Contributor();
        contributorDao.create(contributor);
        logger.info("contributor: " + contributor);
        assertThat(contributor, nullValue());
    }
}
