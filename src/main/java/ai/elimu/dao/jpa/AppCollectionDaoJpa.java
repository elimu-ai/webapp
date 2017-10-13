package ai.elimu.dao.jpa;

import ai.elimu.dao.AppCollectionDao;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.Project;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class AppCollectionDaoJpa extends GenericDaoJpa<AppCollection> implements AppCollectionDao {

    @Override
    public List<AppCollection> readAll(Project project) throws DataAccessException {
        return em.createQuery(
            "SELECT ac " +
            "FROM AppCollection ac " +
            "WHERE ac.project = :project " +
            "ORDER BY ac.name")
            .setParameter("project", project)
            .getResultList();
    }   
}
