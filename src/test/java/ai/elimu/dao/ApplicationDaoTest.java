package ai.elimu.dao;

import ai.elimu.model.admin.Application;
import org.junit.jupiter.api.Test;

import org.apache.logging.log4j.Logger;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(locations = {
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
        assertTrue(applications.isEmpty());
    }
}
