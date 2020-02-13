package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Number;
import ai.elimu.dao.NumberDao;
import ai.elimu.model.enums.Language;

public class NumberDaoJpa extends GenericDaoJpa<Number> implements NumberDao {

    @Override
    public Number readByValue(Language language, Integer value) throws DataAccessException {
        try {
            return (Number) em.createQuery(
                "SELECT n " +
                "FROM Number n " +
                "WHERE n.language = :language " +
                "AND n.value = :value")
                .setParameter("language", language)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Number \"" + value + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Number> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT n " +
            "FROM Number n " +
            "WHERE n.language = :language " +
            "ORDER BY n.value")
            .setParameter("language", language)
            .getResultList();
    }

    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(n) " +
            "FROM Number n " +
            "WHERE n.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
