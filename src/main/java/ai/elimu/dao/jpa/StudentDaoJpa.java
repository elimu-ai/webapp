package ai.elimu.dao.jpa;

import jakarta.persistence.NoResultException;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.students.Student;
import org.springframework.dao.DataAccessException;

public class StudentDaoJpa extends GenericDaoJpa<Student> implements StudentDao {

    @Override
    public Student read(String androidId) throws DataAccessException {
        try {
            return (Student) em.createQuery(
                "SELECT s " +
                "FROM Student s " +
                "WHERE s.androidId = :androidId")
                .setParameter("androidId", androidId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
