package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.AudioDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.enums.Locale;

public class AudioDaoJpa extends GenericDaoJpa<Audio> implements AudioDao {

    @Override
    public Audio read(String transcription, Locale locale) throws DataAccessException {
        try {
            return (Audio) em.createQuery(
                "SELECT a " +
                "FROM Audio a " +
                "WHERE a.transcription = :transcription " +
                "AND a.locale = :locale")
                .setParameter("transcription", transcription)
                .setParameter("locale", locale)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Audio \"" + transcription + "\" was not found for locale " + locale);
            return null;
        }
    }

    @Override
    public List<Audio> readAllOrdered(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "WHERE a.locale = :locale " +
            "ORDER BY a.transcription")
            .setParameter("locale", locale)
            .getResultList();
    }
    
    @Override
    public Long readCount(Locale locale) throws DataAccessException {
        return (Long) em.createQuery(
                "SELECT COUNT(a) " +
                "FROM Audio a " +
                "WHERE a.locale = :locale")
                .setParameter("locale", locale)
                .getSingleResult();
    }
}
