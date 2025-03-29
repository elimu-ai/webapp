package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertNull;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.contributor.Contributor;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@Slf4j
public class ContributorDaoTest {

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
    log.info("contributor: " + contributor);
    assertNull(contributor);
  }
}
