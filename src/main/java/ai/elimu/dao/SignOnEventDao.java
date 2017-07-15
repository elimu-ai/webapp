package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.contributor.SignOnEvent;
import org.springframework.dao.DataAccessException;

public interface SignOnEventDao extends GenericDao<SignOnEvent> {
    
    List<SignOnEvent> readAllOrderedDesc() throws DataAccessException;
}
