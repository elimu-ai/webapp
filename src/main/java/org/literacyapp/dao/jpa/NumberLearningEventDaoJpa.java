package org.literacyapp.dao.jpa;

import java.util.List;
import org.literacyapp.dao.NumberLearningEventDao;
import org.literacyapp.model.Device;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.analytics.NumberLearningEvent;
import org.springframework.dao.DataAccessException;

public class NumberLearningEventDaoJpa extends GenericDaoJpa<NumberLearningEvent> implements NumberLearningEventDao {

    @Override
    public List<NumberLearningEvent> readAll(Device device) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM NumberLearningEvent event " +
            "WHERE event.device = :device " +
            "ORDER BY event.calendar DESC")
            .setParameter("device", device)
            .getResultList();
    }

    @Override
    public List<NumberLearningEvent> readAll(Application application) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM NumberLearningEvent event " +
            "WHERE event.application = :application " +
            "ORDER BY event.calendar DESC")
            .setParameter("application", application)
            .getResultList();
    }

    @Override
    public List<NumberLearningEvent> readAll(Student student) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM NumberLearningEvent event " +
            "WHERE event.student = :student " +
            "ORDER BY event.calendar DESC")
            .setParameter("student", student)
            .getResultList();
    }
}
