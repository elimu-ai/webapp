package ai.elimu.dao.jpa;

import java.util.List;
import jakarta.persistence.NoResultException;
import ai.elimu.model.content.Sound;

import org.springframework.dao.DataAccessException;
import ai.elimu.dao.SoundDao;

public class SoundDaoJpa extends GenericDaoJpa<Sound> implements SoundDao {

    @Override
    public Sound readByValueIpa(String value) throws DataAccessException {
        try {
            return (Sound) em.createQuery(
                "SELECT s " +
                "FROM Sound s " +
                "WHERE s.valueIpa = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Sound readByValueSampa(String value) throws DataAccessException {
        try {
            return (Sound) em.createQuery(
                "SELECT s " +
                "FROM Sound s " +
                "WHERE s.valueSampa = :value")
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Sound> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Sound s " +
            "ORDER BY s.valueIpa")
            .getResultList();
    }
    
    @Override
    public List<Sound> readAllOrderedByIpaValueCharacterLength() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Sound s " +
            "ORDER BY CHAR_LENGTH(s.valueIpa) DESC, s.valueIpa")
            .getResultList();
    }
    
    @Override
    public List<Sound> readAllOrderedByUsage() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Sound s " +
            "ORDER BY s.usageCount DESC, s.valueIpa")
            .getResultList();
    }

    @Override
    public List<Sound> readAllOrderedById() throws DataAccessException {
        return em.createQuery(
            "SELECT s " +
            "FROM Sound s " +
            "ORDER BY s.id")
            .getResultList();
    }
}
