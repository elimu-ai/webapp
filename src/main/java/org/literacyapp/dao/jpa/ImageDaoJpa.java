package org.literacyapp.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.content.multimedia.Image;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.content.Word;
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
    public List<Image> readAllLabeled(Word word, Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "WHERE :word MEMBER OF i.words " + 
            "AND i.locale = :locale " +
            "ORDER BY i.title")
            .setParameter("word", word)
            .setParameter("locale", locale)
            .getResultList();
    }
}
