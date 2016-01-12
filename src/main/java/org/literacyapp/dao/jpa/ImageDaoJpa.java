package org.literacyapp.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.Image;
import org.literacyapp.dao.ImageDao;

public class ImageDaoJpa extends GenericDaoJpa<Image> implements ImageDao {

    @Override
    public Image read(String title) throws DataAccessException {
        try {
            return (Image) em.createQuery(
                "SELECT i " +
                "FROM Image i " +
                "WHERE i.title = :title")
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Image \"" + title + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Image> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT i " +
            "FROM Image i " +
            "ORDER BY i.title")
            .getResultList();
    }
}
