package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.admin.Application;
import ai.elimu.entity.admin.ApplicationVersion;

public interface ApplicationVersionDao extends GenericDao<ApplicationVersion> {
    
    ApplicationVersion read(Application application, Integer versionCode) throws DataAccessException;
    
    List<ApplicationVersion> readAll(Application application) throws DataAccessException;
}
