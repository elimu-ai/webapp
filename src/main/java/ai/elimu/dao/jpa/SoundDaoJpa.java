package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.model.content.Allophone;

import org.springframework.dao.DataAccessException;
import ai.elimu.dao.SoundDao;

public class SoundDaoJpa extends GenericDaoJpa<Allophone> implements SoundDao {

    @Override
    public Allophone readByValueIpa(String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT s " +
                "FROM Allophone s " +
                "WHERE s.valueIpa = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Sound \"" + value + "\" was not found");
            return null;
        }
    }
    
    @Override
    public Allophone readByValueSampa(String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT s " +
                "FROM Allophone s " +
                "WHERE s.valueSampa = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Sound \"" + value + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Allophone> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Allophone s " +
            "ORDER BY s.valueIpa")
            .getResultList();
    }
    
    @Override
    public List<Allophone> readAllOrderedByIpaValueCharacterLength() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Allophone s " +
            "ORDER BY CHAR_LENGTH(s.valueIpa) DESC, s.valueIpa")
            .getResultList();
    }
    
    @Override
    public List<Allophone> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Allophone s " +
            "ORDER BY s.usageCount DESC, s.valueIpa")
            .getResultList();
    }
}
