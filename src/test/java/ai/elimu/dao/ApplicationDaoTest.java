package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.application.Application;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@Slf4j
public class ApplicationDaoTest {

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
