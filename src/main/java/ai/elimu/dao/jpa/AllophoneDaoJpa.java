package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Language;

public class AllophoneDaoJpa extends GenericDaoJpa<Allophone> implements AllophoneDao {

    @Override
    public Allophone readByValueIpa(Language language, String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.language = :language " +
                "AND a.valueIpa = :value")
                .setParameter("language", language)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Allophone \"" + value + "\" was not found for language " + language);
            return null;
        }
    }
    
    @Override
    public Allophone readByValueSampa(Language language, String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.language = :language " +
                "AND a.valueSampa = :value")
                .setParameter("language", language)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Allophone \"" + value + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Allophone> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "WHERE a.language = :language " +
            "ORDER BY a.valueIpa")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public List<Allophone> readAllOrderedByUsage(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "WHERE a.language = :language " +
            "ORDER BY a.usageCount DESC, a.valueIpa")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(a) " +
            "FROM Allophone a " +
            "WHERE a.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
