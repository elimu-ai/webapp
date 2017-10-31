package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToContributorConverter implements Converter<String, Contributor> {

    @Autowired
    private ContributorDao contributorDao;
    
    /**
     * Convert Contributor id to Contributor entity
     */
    public Contributor convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long contributorId = Long.parseLong(id);
            Contributor contributor = contributorDao.read(contributorId);
            return contributor;
        }
    }
}
