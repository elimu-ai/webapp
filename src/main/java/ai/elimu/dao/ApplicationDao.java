package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.admin.Application;

import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.admin.ApplicationStatus;

import org.springframework.dao.DataAccessException;

public interface ApplicationDao extends GenericDao<Application> {
    
    Application readByPackageName(Language language, String packageName) throws DataAccessException;

    List<Application> readAll(Language language) throws DataAccessException;
    
    List<Application> readAllByStatus(Language language, ApplicationStatus applicationStatus) throws DataAccessException;
}
