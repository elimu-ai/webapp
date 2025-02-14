package ai.elimu.logic.converters;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToContributorConverter implements Converter<String, Contributor> {

  private final ContributorDao contributorDao;

  /**
   * Convert Contributor id to Contributor entity
   */
  public Contributor convert(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    } else {
      Long contributorId = Long.parseLong(id);
      return contributorDao.read(contributorId);
    }
  }
}
