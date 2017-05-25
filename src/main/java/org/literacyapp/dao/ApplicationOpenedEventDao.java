package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Device;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.analytics.ApplicationOpenedEvent;
import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface ApplicationOpenedEventDao extends GenericDao<ApplicationOpenedEvent> {
	
    List<ApplicationOpenedEvent> readAll(Locale locale) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Device device) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Application application) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(String packageName) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Student student) throws DataAccessException;
}
