package org.literacyapp.dao.jpa;

import java.util.List;
import org.literacyapp.dao.SignOnEventDao;

import org.literacyapp.model.contributor.SignOnEvent;
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
