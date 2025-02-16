package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ai.elimu.model.admin.Application;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class ApplicationDaoTest {

  private Logger logger = LogManager.getLogger();

  private final ApplicationDao applicationDao;

  @Autowired
  public ApplicationDaoTest(ApplicationDao applicationDao) {
    this.applicationDao = applicationDao;
  }

  @Test
  public void testReadAll() {
    List<Application> applications = applicationDao.readAll();
    assertTrue(applications.isEmpty());
  }
}
