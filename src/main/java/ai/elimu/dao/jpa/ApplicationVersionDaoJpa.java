package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;

import org.springframework.dao.DataAccessException;

public class ApplicationVersionDaoJpa extends GenericDaoJpa<ApplicationVersion> implements ApplicationVersionDao {
    
    @Override
    public ApplicationVersion read(Application application, Integer versionCode) throws DataAccessException {
        try {
            return (ApplicationVersion) em.createQuery(
                "SELECT av " +
                "FROM ApplicationVersion av " +
                "WHERE av.application = :application " +
                "AND av.versionCode = :versionCode")
                .setParameter("application", application)
                .setParameter("versionCode", versionCode)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("ApplicationVersion \"" + versionCode + "\" was not found for Application " + application.getPackageName());
            return null;
        }
    }
    
    @Override
    public List<ApplicationVersion> readAll(Application application) throws DataAccessException {
        return em.createQuery(
            "SELECT av " +
            "FROM ApplicationVersion av " +
            "WHERE av.application = :application " +
            "ORDER BY av.versionCode DESC")
            .setParameter("application", application)
            .getResultList();
    }
}
