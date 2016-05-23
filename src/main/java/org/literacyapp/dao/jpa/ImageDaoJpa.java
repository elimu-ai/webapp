package org.literacyapp.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Image;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.enums.Language;

public class ImageDaoJpa extends GenericDaoJpa<Image> implements ImageDao {

    @Override
    public Image read(String title, Language language) throws DataAccessException {
        try {
            return (Image) em.createQuery(
                "SELECT i " +
                "FROM Image i " +
                "WHERE i.title = :title " +
                "AND i.language = :language")
                .setParameter("title", title)
                .setParameter("language", language)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Image \"" + title + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Image> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "ORDER BY i.title")
            .getResultList();
    }

    @Override
    public List<Image> readLatest(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE i.language = :language " +
            "ORDER BY i.calendar DESC")
            .setMaxResults(10)
            .setParameter("language", language)
            .getResultList();
    }
}
