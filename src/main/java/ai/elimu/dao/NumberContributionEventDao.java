package ai.elimu.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

import ai.elimu.entity.content.Number;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;

public interface NumberContributionEventDao extends GenericDao<NumberContributionEvent> {
    
    List<NumberContributionEvent> readAll(Number number) throws DataAccessException;
    
    List<NumberContributionEvent> readAll(Contributor contributor) throws DataAccessException;
    
    Long readCount(Contributor contributor) throws DataAccessException;
}
