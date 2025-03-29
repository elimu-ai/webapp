package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.admin.Application;
import ai.elimu.entity.admin.ApplicationVersion;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@Slf4j
public class ApplicationVersionDaoTest {

  private final ApplicationDao applicationDao;

  private final ApplicationVersionDao applicationVersionDao;

  @Autowired
  public ApplicationVersionDaoTest(ApplicationDao applicationDao, ApplicationVersionDao applicationVersionDao) {
    this.applicationDao = applicationDao;
    this.applicationVersionDao = applicationVersionDao;
  }

  @Test
  public void testReadAll() {
    Application application = new Application();
    applicationDao.create(application);

    List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
    assertTrue(applicationVersions.isEmpty());

    applicationDao.delete(application);
  }
}
