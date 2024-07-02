package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.LetterDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.Letter;

public class LetterDaoJpa extends GenericDaoJpa<Letter> implements LetterDao {

    @Override
    public Letter readByText(String text) throws DataAccessException {
        try {
            return (Letter) em.createQuery(
                "SELECT l " +
                "FROM Letter l " +
                "WHERE l.text = :text")
                .setParameter("text", text)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Letter> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM Letter l " +
            "ORDER BY l.text")
            .getResultList();
    }
    
    @Override
    public List<Letter> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT l " +
            "FROM Letter l " +
            "ORDER BY l.usageCount DESC, l.text")
            .getResultList();
    }
}
