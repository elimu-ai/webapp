package ai.elimu.dao.jpa;

import java.util.List;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.NumberLearningEvent;
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
