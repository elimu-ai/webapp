package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.VideoDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.enums.Language;

public class VideoDaoJpa extends GenericDaoJpa<Video> implements VideoDao {

    @Override
    public Video read(String title, Language language) throws DataAccessException {
        try {
            return (Video) em.createQuery(
                "SELECT v " +
                "FROM Video v " +
                "WHERE v.title = :title " +
                "AND v.language = :language")
                .setParameter("title", title)
                .setParameter("language", language)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Video \"" + title + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Video> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT v " +
            "FROM Video v " +
            "WHERE v.language = :language " +
            "ORDER BY v.title")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(v) " +
            "FROM Video v " +
            "WHERE v.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
