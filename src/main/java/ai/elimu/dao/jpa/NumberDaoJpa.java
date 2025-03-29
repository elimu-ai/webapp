package ai.elimu.dao.jpa;

import java.util.List;
import jakarta.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.NumberDao;
import ai.elimu.entity.content.Number;

public class NumberDaoJpa extends GenericDaoJpa<Number> implements NumberDao {

    @Override
    public Number readByValue(Integer value) throws DataAccessException {
        try {
            return (Number) em.createQuery(
                "SELECT n " +
                "FROM Number n " +
                "WHERE n.value = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Number> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT n " +
            "FROM Number n " +
            "ORDER BY n.value")
            .getResultList();
    }

    @Override
    public List<Number> readAllOrderedById() throws DataAccessException {
        return em.createQuery(
                "SELECT n " +
                    "FROM Number n " +
                    "ORDER BY n.id")
            .getResultList();
    }
}
