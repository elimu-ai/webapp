package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.SyllableDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Syllable;
import ai.elimu.model.enums.Language;

public class SyllableDaoJpa extends GenericDaoJpa<Syllable> implements SyllableDao {

    @Override
    public Syllable readByText(Language language, String text) throws DataAccessException {
        try {
            return (Syllable) em.createQuery(
                "SELECT s " +
                "FROM Syllable s " +
                "WHERE s.language = :language " +
                "AND s.text = :text")
                .setParameter("language", language)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Syllable '" + text + "' was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Syllable> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Syllable s " +
            "WHERE s.language = :language " +
            "ORDER BY s.text")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<Syllable> readAllOrderedByUsage(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Syllable s " +
            "WHERE s.language = :language " +
            "ORDER BY s.usageCount DESC, s.text")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(s) " +
            "FROM Syllable s " +
            "WHERE s.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
