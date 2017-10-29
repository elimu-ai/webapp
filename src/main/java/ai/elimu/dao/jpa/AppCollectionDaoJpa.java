package ai.elimu.dao.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.AppCollectionDao;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.Project;

public class AppCollectionDaoJpa extends GenericDaoJpa<AppCollection> implements AppCollectionDao {

    @Override
	@Transactional
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
