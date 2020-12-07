package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.AudioDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Audio;

public class AudioDaoJpa extends GenericDaoJpa<Audio> implements AudioDao {
    
    @Override
    public Audio readByTitle(String title) throws DataAccessException {
        try {
            return (Audio) em.createQuery(
                "SELECT a " +
                "FROM Audio a " +
                "WHERE a.title = :title")
                .setParameter("title", title)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Audio with title \"" + title + "\" was not found");
            return null;
        }
    }

    @Override
    public Audio readByTranscription(String transcription) throws DataAccessException {
        try {
            return (Audio) em.createQuery(
                "SELECT a " +
                "FROM Audio a " +
                "WHERE a.transcription = :transcription")
                .setParameter("transcription", transcription)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Audio with transcription \"" + transcription + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Audio> readAllOrdered() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "ORDER BY a.title")
            .getResultList();
    }
}
