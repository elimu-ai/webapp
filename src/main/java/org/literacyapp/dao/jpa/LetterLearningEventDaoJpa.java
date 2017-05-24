package org.literacyapp.dao.jpa;

import java.util.List;
import org.literacyapp.dao.LetterLearningEventDao;
import org.literacyapp.model.Device;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.analytics.LetterLearningEvent;
import org.springframework.dao.DataAccessException;

public class LetterLearningEventDaoJpa extends GenericDaoJpa<LetterLearningEvent> implements LetterLearningEventDao {

    @Override
    public List<LetterLearningEvent> readAll(Device device) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM LetterLearningEvent event " +
            "WHERE event.device = :device " +
            "ORDER BY event.calendar DESC")
            .setParameter("device", device)
            .getResultList();
    }

    @Override
    public List<LetterLearningEvent> readAll(Application application) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM LetterLearningEvent event " +
            "WHERE event.application = :application " +
            "ORDER BY event.calendar DESC")
            .setParameter("application", application)
            .getResultList();
    }

    @Override
    public List<LetterLearningEvent> readAll(Student student) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM LetterLearningEvent event " +
            "WHERE event.student = :student " +
            "ORDER BY event.calendar DESC")
            .setParameter("student", student)
            .getResultList();
    }
}
