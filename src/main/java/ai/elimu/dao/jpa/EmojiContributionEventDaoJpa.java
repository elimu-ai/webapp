package ai.elimu.dao.jpa;

import ai.elimu.dao.EmojiContributionEventDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.EmojiContributionEvent;

import java.util.List;
import org.springframework.dao.DataAccessException;

public class EmojiContributionEventDaoJpa extends GenericDaoJpa<EmojiContributionEvent> implements EmojiContributionEventDao {

    @Override
    public List<EmojiContributionEvent> readAll(Emoji emoji) throws DataAccessException {
        return em.createQuery(
            "SELECT event " + 
            "FROM EmojiContributionEvent event " +
            "WHERE event.emoji = :emoji " + 
            "ORDER BY event.timestamp DESC")
            .setParameter("emoji", emoji)
            .getResultList();
    }
    
    @Override
    public Long readCount(Contributor contributor) throws DataAccessException {
        return (Long) em.createQuery(
            "SELECT COUNT(event) " +
            "FROM EmojiContributionEvent event " +
            "WHERE event.contributor = :contributor")
            .setParameter("contributor", contributor)
            .getSingleResult();
    }
}
