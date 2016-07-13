package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.admin.application.Application;

import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.admin.application.ApplicationStatus;

import org.springframework.dao.DataAccessException;

public interface ApplicationDao extends GenericDao<Application> {
    
    Application readByPackageName(Locale locale, String packageName) throws DataAccessException;

    List<Application> readAll(Locale locale) throws DataAccessException;
    
    List<Application> readAllByStatus(Locale locale, ApplicationStatus applicationStatus) throws DataAccessException;
}
