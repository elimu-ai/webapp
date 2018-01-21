package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.admin.Application;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.admin.ApplicationStatus;

public class ApplicationDaoJpa extends GenericDaoJpa<Application> implements ApplicationDao {
    
    @Override
    public Application readByPackageName(Locale locale, String packageName) throws DataAccessException {
        try {
            return (Application) em.createQuery(
                "SELECT a " +
                "FROM Application a " +
                "WHERE a.locale = :locale " +
                "AND a.packageName = :packageName " +
                "AND a.project IS EMPTY")
                .setParameter("locale", locale)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Application with packageName \"" + packageName + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Application> readAll(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "WHERE a.locale = :locale " +
            "AND a.project IS EMPTY " +
            "ORDER BY a.packageName")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public List<Application> readAllByStatus(Locale locale, ApplicationStatus applicationStatus) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "WHERE a.locale = :locale " +
            "AND a.applicationStatus = :applicationStatus " +
            "AND a.project IS EMPTY " +
            "ORDER BY a.packageName")
            .setParameter("locale", locale)
            .setParameter("applicationStatus", applicationStatus)
            .getResultList();
    }
}
