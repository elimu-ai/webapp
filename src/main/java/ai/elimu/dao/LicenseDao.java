package ai.elimu.dao;

import ai.elimu.model.project.License;

import org.springframework.dao.DataAccessException;

public interface LicenseDao extends GenericDao<License> {
    
    License read(String licenseEmail, String licenseNumber) throws DataAccessException;
}
