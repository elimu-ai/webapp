package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.admin.Application;

import ai.elimu.model.v2.enums.admin.ApplicationStatus;

import org.springframework.dao.DataAccessException;

public interface ApplicationDao extends GenericDao<Application> {
    
    Application readByPackageName(String packageName) throws DataAccessException;

    List<Application> readAll() throws DataAccessException;
    
    List<Application> readAllByStatus(ApplicationStatus applicationStatus) throws DataAccessException;
}
