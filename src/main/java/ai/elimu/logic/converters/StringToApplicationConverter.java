package ai.elimu.logic.converters;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.entity.application.Application;

import org.springframework.core.convert.converter.Converter;

@AllArgsConstructor
public class StringToApplicationConverter implements Converter<String, Application> {

    private final ApplicationDao applicationDao;

    /**
     * Convert Application id to Application entity
     */
    public Application convert(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        } else {
            Long applicationId = Long.parseLong(id);
            return applicationDao.read(applicationId);
        }
    }
}
