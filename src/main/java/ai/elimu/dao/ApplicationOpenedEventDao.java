package ai.elimu.dao;

import java.util.Calendar;
import java.util.List;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.ApplicationOpenedEvent;
import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface ApplicationOpenedEventDao extends GenericDao<ApplicationOpenedEvent> {
    
    ApplicationOpenedEvent read(Device device, Calendar time, String packageName) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Locale locale) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Device device) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Application application) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(String packageName) throws DataAccessException;
    
    List<ApplicationOpenedEvent> readAll(Student student) throws DataAccessException;
}
