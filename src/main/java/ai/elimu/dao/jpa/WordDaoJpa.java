package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;

public class WordDaoJpa extends GenericDaoJpa<Word> implements WordDao {

    @Override
    public Word readByText(Locale locale, String text) throws DataAccessException {
        try {
            return (Word) em.createQuery(
                "SELECT w " +
                "FROM Word w " +
                "WHERE w.locale = :locale " +
                "AND w.text = :text")
                .setParameter("locale", locale)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Word \"" + text + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Word> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.locale = :locale " +
            "ORDER BY w.text")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public List<Word> readAllOrderedByUsage(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.locale = :locale " +
            "ORDER BY w.usageCount DESC, w.text")
            .setParameter("locale", locale)
            .getResultList();
    }

    @Override
    public List<Word> readLatest(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT w " +
            "FROM Word w " +
            "WHERE w.locale = :locale " +
            "ORDER BY w.calendar DESC")
            .setParameter("locale", locale)
            .setMaxResults(10)
            .getResultList();
    }
    
    @Override
    public Long readCount(Locale locale) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(w) " +
                "FROM Word w " +
                "WHERE w.locale = :locale")
                .setParameter("locale", locale)
                .getSingleResult();
    }
}
