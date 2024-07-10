package ai.elimu.dao;

import java.util.List;
import ai.elimu.entity.admin.Application;
import ai.elimu.entity.admin.ApplicationVersion;

import org.springframework.dao.DataAccessException;

public interface ApplicationVersionDao extends GenericDao<ApplicationVersion> {
    
    ApplicationVersion read(Application application, Integer versionCode) throws DataAccessException;
    
    List<ApplicationVersion> readAll(Application application) throws DataAccessException;
}
