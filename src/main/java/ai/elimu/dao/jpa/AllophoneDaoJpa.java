package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.enums.Locale;

public class AllophoneDaoJpa extends GenericDaoJpa<Allophone> implements AllophoneDao {

    @Override
    public Allophone readByValueIpa(Locale locale, String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.locale = :locale " +
                "AND a.valueIpa = :value")
                .setParameter("locale", locale)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Allophone \"" + value + "\" was not found for locale " + locale);
            return null;
        }
    }
    
    @Override
    public Allophone readByValueSampa(Locale locale, String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.locale = :locale " +
                "AND a.valueSampa = :value")
                .setParameter("locale", locale)
                .setParameter("value", value)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Allophone \"" + value + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Allophone> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "WHERE a.locale = :locale " +
            "ORDER BY a.valueIpa")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public List<Allophone> readAllOrderedByUsage(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Allophone a " +
            "WHERE a.locale = :locale " +
            "ORDER BY a.usageCount DESC, a.valueIpa")
            .setParameter("locale", locale)
            .getResultList();
    }
}
