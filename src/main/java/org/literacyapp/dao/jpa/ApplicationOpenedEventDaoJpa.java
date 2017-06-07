package org.literacyapp.dao.jpa;

import java.util.Calendar;
import java.util.List;
import javax.persistence.NoResultException;
import org.literacyapp.dao.ApplicationOpenedEventDao;
import org.literacyapp.model.Device;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.analytics.ApplicationOpenedEvent;
import org.literacyapp.model.enums.Locale;
import org.springframework.dao.DataAccessException;

public class ApplicationOpenedEventDaoJpa extends GenericDaoJpa<ApplicationOpenedEvent> implements ApplicationOpenedEventDao {
    
    @Override
    public ApplicationOpenedEvent read(Device device, Calendar time, String packageName) throws DataAccessException {
        try {
            return (ApplicationOpenedEvent) em.createQuery(
                "SELECT event " +
                "FROM ApplicationOpenedEvent event " +
                "WHERE event.device = :device " +
                "AND event.calendar = :time " + 
                "AND event.packageName = :packageName")
                .setParameter("device", device)
                .setParameter("time", time)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("ApplicationOpenedEvent on device " + device.getDeviceId() + " (" + device.getLocale() + "), at " + time.getTime() + " for " + packageName + " was not found");
            return null;
        }
    }

    @Override
    public List<ApplicationOpenedEvent> readAll(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ApplicationOpenedEvent event " +
            "WHERE event.device.locale = :locale " +
            "ORDER BY event.calendar DESC")
            .setParameter("locale", locale)
            .getResultList();
    }

    @Override
    public List<ApplicationOpenedEvent> readAll(Device device) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ApplicationOpenedEvent event " +
            "WHERE event.device = :device " +
            "ORDER BY event.calendar DESC")
            .setParameter("device", device)
            .getResultList();
    }

    @Override
    public List<ApplicationOpenedEvent> readAll(Application application) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ApplicationOpenedEvent event " +
            "WHERE event.application = :application " +
            "ORDER BY event.calendar DESC")
            .setParameter("application", application)
            .getResultList();
    }
    
    @Override
    public List<ApplicationOpenedEvent> readAll(String packageName) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ApplicationOpenedEvent event " +
            "WHERE event.packageName = :packageName " +
            "ORDER BY event.calendar DESC")
            .setParameter("packageName", packageName)
            .getResultList();
    }
    
    @Override
    public List<ApplicationOpenedEvent> readAll(Student student) throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM ApplicationOpenedEvent event " +
            "WHERE event.student = :student " +
            "ORDER BY event.calendar DESC")
            .setParameter("student", student)
            .getResultList();
    }
}
