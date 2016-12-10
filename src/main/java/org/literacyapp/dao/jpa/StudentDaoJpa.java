package org.literacyapp.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import org.literacyapp.dao.StudentDao;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Student;
import org.literacyapp.model.enums.Locale;

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
