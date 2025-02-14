package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertNull;

import ai.elimu.model.contributor.Contributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class ContributorDaoTest {

  private Logger logger = LogManager.getLogger();

  private final ContributorDao contributorDao;

  @Autowired
  public ContributorDaoTest(ContributorDao contributorDao) {
    this.contributorDao = contributorDao;
  }

  @Disabled
  @Test
  public void testConstraintViolation() {
    Contributor contributor = new Contributor();
    contributorDao.create(contributor);
    logger.info("contributor: " + contributor);
    assertNull(contributor);
  }
}
