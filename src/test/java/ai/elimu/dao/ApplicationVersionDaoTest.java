package ai.elimu.dao;

import ai.elimu.entity.admin.Application;
import ai.elimu.entity.admin.ApplicationVersion;
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
public class ApplicationVersionDaoTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;
    
    @Test
    public void testReadAll() {
        Application application = new Application();
        applicationDao.create(application);
        
        List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
        assertThat(applicationVersions.isEmpty(), is(true));
        
        applicationDao.delete(application);
    }
}
