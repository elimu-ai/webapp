package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.LetterDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Letter;
import ai.elimu.model.enums.Locale;

public class LetterDaoJpa extends GenericDaoJpa<Letter> implements LetterDao {

    @Override
    public Letter readByText(Locale locale, String text) throws DataAccessException {
        try {
            return (Letter) em.createQuery(
                "SELECT l " +
                "FROM Letter l " +
                "WHERE l.locale = :locale " +
                "AND l.text = :text")
                .setParameter("locale", locale)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Letter '" + text + "' was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Letter> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM Letter l " +
            "WHERE l.locale = :locale " +
            "ORDER BY l.usageCount DESC, l.text")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public Long readCount(Locale locale) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(l) " +
                "FROM Letter l " +
                "WHERE l.locale = :locale")
                .setParameter("locale", locale)
                .getSingleResult();
    }
}
