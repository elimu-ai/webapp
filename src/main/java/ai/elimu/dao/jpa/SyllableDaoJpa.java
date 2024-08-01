package ai.elimu.dao.jpa;

import java.util.List;
import jakarta.persistence.NoResultException;
import ai.elimu.dao.SyllableDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Syllable;

public class SyllableDaoJpa extends GenericDaoJpa<Syllable> implements SyllableDao {

    @Override
    public Syllable readByText(String text) throws DataAccessException {
        try {
            return (Syllable) em.createQuery(
                "SELECT s " +
                "FROM Syllable s " +
                "WHERE s.text = :text")
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Syllable> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Syllable s " +
            "ORDER BY s.text")
            .getResultList();
    }
    
    @Override
    public List<Syllable> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Syllable s " +
            "ORDER BY s.usageCount DESC, s.text")
            .getResultList();
    }
}
