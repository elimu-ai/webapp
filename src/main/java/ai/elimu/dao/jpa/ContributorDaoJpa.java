package ai.elimu.dao.jpa;

import java.util.Calendar;
import java.util.List;
import jakarta.persistence.NoResultException;
import ai.elimu.dao.ContributorDao;
import ai.elimu.entity.contributor.Contributor;

import org.springframework.dao.DataAccessException;

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
            return null;
        }
    }

    @Override
    public Contributor readByProviderIdDiscord(String id) throws DataAccessException {
        try {
            return (Contributor) em.createQuery(
                "SELECT c " +
                "FROM Contributor c " +
                "WHERE c.providerIdDiscord = :id")
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Contributor readByProviderIdWeb3(String id) throws DataAccessException {
        try {
            return (Contributor) em.createQuery(
                "SELECT c " +
                "FROM Contributor c " +
                "WHERE c.providerIdWeb3 = :id")
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
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

    @Override
    public List<Contributor> readAll(Calendar calendarFrom, Calendar calendarTo) throws DataAccessException {
        return em.createQuery(
            "SELECT c " +
            "FROM Contributor c " +
            "WHERE c.registrationTime >= :calendarFrom " +
            "AND c.registrationTime < :calendarTo " +
            "ORDER BY c.registrationTime DESC")
            .setParameter("calendarFrom", calendarFrom)
            .setParameter("calendarTo", calendarTo)
            .getResultList();
    }

    @Override
    public List<Contributor> readAllWithStoryBookContributions() throws DataAccessException {
        return em.createQuery(
            "SELECT c " +
            "FROM Contributor c " +
            "WHERE c IN (SELECT contributor FROM StoryBookContributionEvent)")
            .getResultList();
    }

    @Override
    public List<Contributor> readAllWithWordContributions() throws DataAccessException {
        return em.createQuery(
            "SELECT c " +
            "FROM Contributor c " +
            "WHERE c IN (SELECT contributor FROM WordContributionEvent)")
            .getResultList();
    }
    
    @Override
    public List<Contributor> readAllWithNumberContributions() throws DataAccessException {
        return em.createQuery(
            "SELECT c " +
            "FROM Contributor c " +
            "WHERE c IN (SELECT contributor FROM NumberContributionEvent)")
            .getResultList();
    }
}
