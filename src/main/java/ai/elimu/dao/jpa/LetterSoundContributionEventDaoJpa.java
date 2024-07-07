package ai.elimu.dao.jpa;

import ai.elimu.entity.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.entity.content.LetterSoundCorrespondence;
import ai.elimu.entity.contributor.Contributor;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class LetterSoundContributionEventDaoJpa extends GenericDaoJpa<LetterSoundCorrespondenceContributionEvent> implements LetterSoundContributionEventDao {

    @Override
    public List<LetterSoundCorrespondenceContributionEvent> readAllOrderedByTimeDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundCorrespondenceContributionEvent e " +
            "ORDER BY e.time DESC")
            .getResultList();
    }

    @Override
    public List<LetterSoundCorrespondenceContributionEvent> readAll(LetterSoundCorrespondence letterSound) throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundCorrespondenceContributionEvent e " +
            "WHERE e.letterSoundCorrespondence = :letterSoundCorrespondence " + 
            "ORDER BY e.time DESC")
            .setParameter("letterSoundCorrespondence", letterSound)
            .getResultList();
    }

    @Override
    public List<LetterSoundCorrespondenceContributionEvent> readAll(Contributor contributor) throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundCorrespondenceContributionEvent e " +
            "WHERE e.contributor = :contributor " + 
            "ORDER BY e.time DESC")
            .setParameter("contributor", contributor)
            .getResultList();
    }
    
    @Override
    public List<LetterSoundCorrespondenceContributionEvent> readMostRecentPerLetterSound() throws DataAccessException {
        return em.createQuery(
            "SELECT e " + 
            "FROM LetterSoundCorrespondenceContributionEvent e " +
            "WHERE e.time IN (SELECT MAX(time) FROM LetterSoundCorrespondenceContributionEvent GROUP BY letterSoundCorrespondence_id) " + // TODO: replace with "NOT EXISTS"? - https://stackoverflow.com/a/25694562
            "ORDER BY e.time ASC")
            .getResultList();
    }

    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(e) " +
            "FROM LetterSoundCorrespondenceContributionEvent e " +
            "WHERE e.contributor = :contributor")
            .setParameter("contributor", contributor)
            .getSingleResult();
    }
}
