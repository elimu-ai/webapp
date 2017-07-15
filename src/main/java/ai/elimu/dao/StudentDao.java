package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.Student;
import ai.elimu.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface StudentDao extends GenericDao<Student> {
	
    Student read(String uniqueId) throws DataAccessException;
    
    List<Student> readAll(Locale locale) throws DataAccessException;
}
