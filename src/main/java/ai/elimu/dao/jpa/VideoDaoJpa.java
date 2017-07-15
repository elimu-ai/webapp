package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.VideoDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.enums.Locale;

public class VideoDaoJpa extends GenericDaoJpa<Video> implements VideoDao {

    @Override
    public Video read(String title, Locale locale) throws DataAccessException {
        try {
            return (Video) em.createQuery(
                "SELECT v " +
                "FROM Video v " +
                "WHERE v.title = :title " +
                "AND v.locale = :locale")
                .setParameter("title", title)
                .setParameter("locale", locale)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Video \"" + title + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Video> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT v " +
            "FROM Video v " +
            "WHERE v.locale = :locale " +
            "ORDER BY v.title")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public Long readCount(Locale locale) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(v) " +
                "FROM Video v " +
                "WHERE v.locale = :locale")
                .setParameter("locale", locale)
                .getSingleResult();
    }
}
