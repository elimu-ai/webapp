package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.admin.Application;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.admin.ApplicationStatus;

public class ApplicationDaoJpa extends GenericDaoJpa<Application> implements ApplicationDao {
    
    @Override
    public Application readByPackageName(Language language, String packageName) throws DataAccessException {
        try {
            return (Application) em.createQuery(
                "SELECT a " +
                "FROM Application a " +
                "WHERE a.language = :language " +
                "AND a.packageName = :packageName")
                .setParameter("language", language)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Application with packageName \"" + packageName + "\" was not found for language " + language, e);
            return null;
        }
    }

    @Override
    public List<Application> readAll(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "WHERE a.language = :language " +
            "ORDER BY a.packageName")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<Application> readAllByStatus(Language language, ApplicationStatus applicationStatus) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "WHERE a.language = :language " +
            "AND a.applicationStatus = :applicationStatus " +
            "ORDER BY a.packageName")
            .setParameter("language", language)
            .setParameter("applicationStatus", applicationStatus)
            .getResultList();
    }
}
