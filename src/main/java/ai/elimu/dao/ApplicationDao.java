package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.admin.Application;

import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.admin.ApplicationStatus;

import org.springframework.dao.DataAccessException;

public interface ApplicationDao extends GenericDao<Application> {
    
    Application readByPackageName(Locale locale, String packageName) throws DataAccessException;

    List<Application> readAll(Locale locale) throws DataAccessException;
    
    List<Application> readAllByStatus(Locale locale, ApplicationStatus applicationStatus) throws DataAccessException;
}
