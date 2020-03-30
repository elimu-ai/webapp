package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.LetterDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Letter;
import ai.elimu.model.enums.Language;

public class LetterDaoJpa extends GenericDaoJpa<Letter> implements LetterDao {

    @Override
    public Letter readByText(Language language, String text) throws DataAccessException {
        try {
            return (Letter) em.createQuery(
                "SELECT l " +
                "FROM Letter l " +
                "WHERE l.language = :language " +
                "AND l.text = :text")
                .setParameter("language", language)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Letter '" + text + "' was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Letter> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM Letter l " +
            "WHERE l.language = :language " +
            "ORDER BY l.text")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<Letter> readAllOrderedByUsage(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM Letter l " +
            "WHERE l.language = :language " +
            "ORDER BY l.usageCount DESC, l.text")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(l) " +
            "FROM Letter l " +
            "WHERE l.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
