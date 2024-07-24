package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.contributor.Contributor;
import java.util.List;

import ai.elimu.model.contributor.LetterSoundContributionEvent;
import org.springframework.dao.DataAccessException;

public class LetterSoundContributionEventDaoJpa extends GenericDaoJpa<LetterSoundContributionEvent> implements LetterSoundContributionEventDao {

    @Override
    public List<LetterSoundContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundContributionEvent e " +
            "ORDER BY e.timestamp DESC")
            .getResultList();
    }

    @Override
    public List<LetterSoundContributionEvent> readAll(LetterSound letterSound) throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundContributionEvent e " +
            "WHERE e.letterSound = :letterSound " + 
            "ORDER BY e.timestamp DESC")
            .setParameter("letterSound", letterSound)
            .getResultList();
    }

    @Override
    public List<LetterSoundContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundContributionEvent e " +
            "WHERE e.contributor = :contributor " + 
            "ORDER BY e.timestamp DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<LetterSoundContributionEvent> readMostRecentPerLetterSound() throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundContributionEvent e " +
            "WHERE e.timestamp IN (SELECT MAX(timestamp) FROM LetterSoundContributionEvent GROUP BY letterSound_id) " + // TODO: replace with "NOT EXISTS"? - https://stackoverflow.com/a/25694562
            "ORDER BY e.timestamp ASC")
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(e) " +
            "FROM LetterSoundContributionEvent e " +
            "WHERE e.contributor = :contributor")
            .setParameter("contributor", contributor)
            .getSingleResult();
    }
}
