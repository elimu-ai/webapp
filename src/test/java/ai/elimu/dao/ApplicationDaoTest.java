package ai.elimu.dao;

import ai.elimu.model.admin.Application;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.enums.Locale;
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
        Locale locale = Locale.values()[(int) (Math.random() * Locale.values().length)];
        logger.info("locale: " + locale);
        
        List<Application> applications = applicationDao.readAll(locale);
        
        assertThat(applications.isEmpty(), is(true));
    }
}
