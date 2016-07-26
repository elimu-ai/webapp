package org.literacyapp.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import org.literacyapp.dao.AudioDao;

import org.springframework.dao.DataAccessException;

import org.literacyapp.model.content.multimedia.Audio;
import org.literacyapp.model.enums.Locale;

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
}
