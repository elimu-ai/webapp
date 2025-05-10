package ai.elimu.dao;

import org.springframework.dao.DataAccessException;
import ai.elimu.entity.analytics.students.Student;

public interface StudentDao extends GenericDao<Student> {
    
    Student read(String androidId) throws DataAccessException;
}
