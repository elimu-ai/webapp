package ai.elimu.dao.jpa;

import java.util.List;
import ai.elimu.dao.SignOnEventDao;

import ai.elimu.model.contributor.SignOnEvent;
import org.springframework.dao.DataAccessException;

public class SignOnEventDaoJpa extends GenericDaoJpa<SignOnEvent> implements SignOnEventDao {

    @Override
    public List<SignOnEvent> readAllOrderedDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT event " +
            "FROM SignOnEvent event " +
            "ORDER BY event.calendar DESC")
            .getResultList();
    }
}
