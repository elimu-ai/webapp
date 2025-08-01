package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.EmojiContributionEvent;

public interface EmojiContributionEventDao extends GenericDao<EmojiContributionEvent> {
    
    List<EmojiContributionEvent> readAll(Emoji emoji) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
