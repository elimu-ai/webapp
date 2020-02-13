package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.AudioDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.enums.Language;

public class AudioDaoJpa extends GenericDaoJpa<Audio> implements AudioDao {

    @Override
    public Audio read(String transcription, Language language) throws DataAccessException {
        try {
            return (Audio) em.createQuery(
                "SELECT a " +
                "FROM Audio a " +
                "WHERE a.transcription = :transcription " +
                "AND a.language = :language")
                .setParameter("transcription", transcription)
                .setParameter("language", language)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Audio \"" + transcription + "\" was not found for language " + language);
            return null;
        }
    }

    @Override
    public List<Audio> readAllOrdered(Language language) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "WHERE a.language = :language " +
            "ORDER BY a.transcription")
            .setParameter("language", language)
            .getResultList();
    }
    
    @Override
    public Long readCount(Language language) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(a) " +
            "FROM Audio a " +
            "WHERE a.language = :language")
            .setParameter("language", language)
            .getSingleResult();
    }
}
