package org.literacyapp.dao.jpa;

import javax.persistence.NoResultException;
import org.literacyapp.dao.ContributorDao;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Contributor;

public class ContributorDaoJpa extends GenericDaoJpa<Contributor> implements ContributorDao {

    @Override
    public Contributor read(String email) throws DataAccessException {
        try {
            return (Contributor) em.createQuery(
                "SELECT c " +
                "FROM Contributor c " +
                "WHERE c.email = :email")
                .setParameter("email", email)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Contributor \"" + email + "\" was not found");
            return null;
        }
    }
}
