package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Number;
import ai.elimu.dao.NumberDao;
import ai.elimu.model.enums.Locale;

public class NumberDaoJpa extends GenericDaoJpa<Number> implements NumberDao {

    @Override
    public Number readByValue(Locale locale, Integer value) throws DataAccessException {
        try {
            return (Number) em.createQuery(
                "SELECT n " +
                "FROM Number n " +
                "WHERE n.locale = :locale " +
                "AND n.value = :value")
                .setParameter("locale", locale)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Number \"" + value + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Number> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT n " +
            "FROM Number n " +
            "WHERE n.locale = :locale " +
            "ORDER BY n.value")
            .setParameter("locale", locale)
            .getResultList();
    }

    @Override
    public Long readCount(Locale locale) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(n) " +
                "FROM Number n " +
                "WHERE n.locale = :locale")
                .setParameter("locale", locale)
                .getSingleResult();
    }
}
