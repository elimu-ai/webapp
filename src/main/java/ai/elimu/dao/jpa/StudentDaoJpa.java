package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.StudentDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.Student;
import ai.elimu.model.enums.Locale;

public class StudentDaoJpa extends GenericDaoJpa<Student> implements StudentDao {

    @Override
    public Student read(String uniqueId) throws DataAccessException {
        try {
            return (Student) em.createQuery(
                "SELECT s " +
                "FROM Student s " +
                "WHERE s.uniqueId = :uniqueId")
                .setParameter("uniqueId", uniqueId)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Student \"" + uniqueId + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Student> readAll(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Student s " +
            "WHERE s.locale = :locale")
            .setParameter("locale", locale)
            .getResultList();
    }
}
