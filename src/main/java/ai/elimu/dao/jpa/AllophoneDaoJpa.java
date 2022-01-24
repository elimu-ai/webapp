package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.model.content.Allophone;

import org.springframework.dao.DataAccessException;
import ai.elimu.dao.SoundDao;

public class AllophoneDaoJpa extends GenericDaoJpa<Allophone> implements SoundDao {

    @Override
    public Allophone readByValueIpa(String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.valueIpa = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Allophone \"" + value + "\" was not found");
            return null;
        }
    }
    
    @Override
    public Allophone readByValueSampa(String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.valueSampa = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Allophone \"" + value + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Allophone> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "ORDER BY a.valueIpa")
            .getResultList();
    }
    
    @Override
    public List<Allophone> readAllOrderedByIpaValueCharacterLength() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "ORDER BY CHAR_LENGTH(a.valueIpa) DESC, a.valueIpa")
            .getResultList();
    }
    
    @Override
    public List<Allophone> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "ORDER BY a.usageCount DESC, a.valueIpa")
            .getResultList();
    }
}
