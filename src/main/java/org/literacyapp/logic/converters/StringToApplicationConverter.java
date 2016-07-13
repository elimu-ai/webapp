package org.literacyapp.logic.converters;

import org.apache.commons.lang.StringUtils;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.admin.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToApplicationConverter implements Converter<String, Application> {

    @Autowired
    private ApplicationDao applicationDao;
    
    /**
     * Convert Application id to Application
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
