package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;

public class WordDaoJpa extends GenericDaoJpa<Word> implements WordDao {

    @Override
    public Word readByText(Language language, String text) throws DataAccessException {
        try {
            return (Word) em.createQuery(
                "SELECT w " +
                "FROM Word w " +
                "WHERE w.language = :language " +
                "AND w.text = :text")
                .setParameter("language", language)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Word \"" + text + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Word> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.language = :language " +
            "ORDER BY w.text")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<Word> readAllOrderedByUsage(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.language = :language " +
            "ORDER BY w.usageCount DESC, w.text")
            .setParameter("language", language)
            .getResultList();
    }

    @Override
    public List<Word> readLatest(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.language = :language " +
            "ORDER BY w.calendar DESC")
            .setParameter("language", language)
            .setMaxResults(10)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(w) " +
                "FROM Word w " +
                "WHERE w.language = :language")
                .setParameter("language", language)
                .getSingleResult();
    }
}
