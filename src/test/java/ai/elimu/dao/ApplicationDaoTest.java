package ai.elimu.dao;

import ai.elimu.entity.admin.Application;
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
public class ApplicationDaoTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Test
    public void testReadAll() {
        List<Application> applications = applicationDao.readAll();
        assertThat(applications.isEmpty(), is(true));
    }
}
