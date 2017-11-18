package ai.elimu.dao;

import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.License;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface LicenseDao extends GenericDao<License> {
    
    License read(String licenseEmail, String licenseNumber) throws DataAccessException;
    
    List<License> readAll(AppCollection appCollection) throws DataAccessException;
}
