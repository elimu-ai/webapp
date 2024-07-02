package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;

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
            return null;
        }
    }

    @Override
    public List<Audio> readAllOrderedByTitle() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "ORDER BY a.title")
            .getResultList();
    }
    
    @Override
    public List<Audio> readAllOrderedByTimeLastUpdate() throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "ORDER BY a.timeLastUpdate DESC")
            .getResultList();
    }

    @Override
    public List<Audio> readAll(Word word) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "WHERE a.word = :word " + 
            "ORDER BY a.timeLastUpdate DESC")
            .setParameter("word", word)
            .getResultList();
    }

    @Override
    public List<Audio> readAll(StoryBookParagraph storyBookParagraph) throws DataAccessException {
        return em.createQuery(
            "SELECT a " +
            "FROM Audio a " +
            "WHERE a.storyBookParagraph = :storyBookParagraph " + 
            "ORDER BY a.timeLastUpdate DESC")
            .setParameter("storyBookParagraph", storyBookParagraph)
            .getResultList();
    }
}
