package ai.elimu.dao.jpa;

import javax.persistence.NoResultException;
import ai.elimu.dao.LicenseDao;
import ai.elimu.model.project.AppCollection;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.project.License;
import java.util.List;

public class LicenseDaoJpa extends GenericDaoJpa<License> implements LicenseDao {
    
    @Override
    public License read(String licenseEmail, String licenseNumber) throws DataAccessException {
        try {
            return (License) em.createQuery(
                "SELECT l " +
                "FROM License l " +
                "WHERE l.licenseEmail = :licenseEmail " +
                "AND l.licenseNumber = :licenseNumber")
                .setParameter("licenseEmail", licenseEmail)
                .setParameter("licenseNumber", licenseNumber)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("License for " + licenseEmail + " was not found");
            return null;
        }
    }

    @Override
    public List<License> readAll(AppCollection appCollection) throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM License l " +
            "WHERE l.appCollection = :appCollection")
            .setParameter("appCollection", appCollection)
            .getResultList();
    }
}
