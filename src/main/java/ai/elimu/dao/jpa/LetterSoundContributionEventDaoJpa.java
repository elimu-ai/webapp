package ai.elimu.dao.jpa;

import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundContributionEvent;

import java.util.List;

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
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery("SELECT COUNT(e) " +
            "FROM LetterSoundContributionEvent e " +
            "WHERE e.contributor = :contributor")
            .setParameter("contributor", contributor)
            .getSingleResult();
    }
}
