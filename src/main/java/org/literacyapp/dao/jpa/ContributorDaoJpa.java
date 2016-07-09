package org.literacyapp.dao.jpa;

import java.util.List;
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
            logger.warn("Contributor with e-mail \"" + email + "\" was not found");
            return null;
        }
    }
    
    @Override
    public Contributor readByProviderIdGitHub(String id) throws DataAccessException {
        try {
            return (Contributor) em.createQuery(
                "SELECT c " +
                "FROM Contributor c " +
                "WHERE c.providerIdGitHub = :id")
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Contributor with GitHub id \"" + id + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Contributor> readAllOrderedDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT c " +
            "FROM Contributor c " +
            "ORDER BY c.registrationTime DESC")
            .getResultList();
    }
}
