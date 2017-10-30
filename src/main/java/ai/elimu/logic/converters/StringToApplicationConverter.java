package ai.elimu.logic.converters;

import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.admin.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToApplicationConverter implements Converter<String, Application> {

    @Autowired
    private ApplicationDao applicationDao;
    
    /**
     * Convert Application id to Application entity
     */
    public Application convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long applicationId = Long.parseLong(id);
            Application application = applicationDao.read(applicationId);
            return application;
        }
    }
}
