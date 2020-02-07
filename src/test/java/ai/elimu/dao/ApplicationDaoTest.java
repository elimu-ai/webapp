package ai.elimu.dao;

import ai.elimu.model.admin.Application;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.enums.Language;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class ApplicationDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Test
    public void testCacheable() {
        Language language = Language.values()[(int) (Math.random() * Language.values().length)];
        logger.info("language: " + language);
        
        List<Application> applications = applicationDao.readAll(language);
        assertThat(applications.isEmpty(), is(true));
    }
}
