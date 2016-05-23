package org.literacyapp.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.model.Allophone;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.enums.Language;

public class AllophoneDaoJpa extends GenericDaoJpa<Allophone> implements AllophoneDao {

    @Override
    public Allophone readByValueIpa(Language language, String value) throws DataAccessException {
        try {
            return (Allophone) em.createQuery(
                "SELECT a " +
                "FROM Allophone a " +
                "WHERE a.language = :language " +
                "AND a.value = :value")
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
            "SELECT n " +
            "FROM Number n " +
            "WHERE n.language = :language " +
            "ORDER BY n.value")
            .setParameter("language", language)
            .getResultList();
    }
}
