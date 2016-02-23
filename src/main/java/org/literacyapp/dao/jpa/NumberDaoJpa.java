package org.literacyapp.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Number;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.enums.Language;

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
            logger.warn("Number \"" + value + "\" was not found");
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
}
