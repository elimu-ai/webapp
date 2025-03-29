package ai.elimu.dao.jpa;

import java.util.List;
import jakarta.persistence.NoResultException;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.entity.admin.Application;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.v2.enums.admin.ApplicationStatus;

public class ApplicationDaoJpa extends GenericDaoJpa<Application> implements ApplicationDao {
    
    @Override
    public Application readByPackageName(String packageName) throws DataAccessException {
        try {
            return (Application) em.createQuery(
                "SELECT a " +
                "FROM Application a " +
                "WHERE a.packageName = :packageName")
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Application> readAll() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "ORDER BY a.packageName")
            .getResultList();
    }
    
    @Override
    public List<Application> readAllByStatus(ApplicationStatus applicationStatus) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Application a " +
            "WHERE a.applicationStatus = :applicationStatus " +
            "ORDER BY a.packageName")
            .setParameter("applicationStatus", applicationStatus)
            .getResultList();
    }
}
