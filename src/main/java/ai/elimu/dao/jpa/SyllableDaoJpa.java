package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.SyllableDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Syllable;
import ai.elimu.model.enums.Locale;

public class SyllableDaoJpa extends GenericDaoJpa<Syllable> implements SyllableDao {

    @Override
    public Syllable readByText(Locale locale, String text) throws DataAccessException {
        try {
            return (Syllable) em.createQuery(
                "SELECT s " +
                "FROM Syllable s " +
                "WHERE s.locale = :locale " +
                "AND s.text = :text")
                .setParameter("locale", locale)
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Syllable '" + text + "' was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Syllable> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Syllable s " +
            "WHERE s.locale = :locale " +
            "ORDER BY s.usageCount DESC, s.text")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public Long readCount(Locale locale) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(s) " +
                "FROM Syllable s " +
                "WHERE s.locale = :locale")
                .setParameter("locale", locale)
                .getSingleResult();
    }
}
