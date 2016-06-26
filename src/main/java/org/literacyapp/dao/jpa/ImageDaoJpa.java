package org.literacyapp.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Image;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.enums.Locale;

public class ImageDaoJpa extends GenericDaoJpa<Image> implements ImageDao {

    @Override
    public Image read(String title, Locale locale) throws DataAccessException {
        try {
            return (Image) em.createQuery(
                "SELECT i " +
                "FROM Image i " +
                "WHERE i.title = :title " +
                "AND i.locale = :locale")
                .setParameter("title", title)
                .setParameter("locale", locale)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Image \"" + title + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Image> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE i.locale = :locale " +
            "ORDER BY i.title")
            .setParameter("locale", locale)
            .getResultList();
    }

    @Override
    public List<Image> readLatest(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE i.locale = :locale " +
            "ORDER BY i.calendar DESC")
            .setMaxResults(10)
            .setParameter("locale", locale)
            .getResultList();
    }
}
