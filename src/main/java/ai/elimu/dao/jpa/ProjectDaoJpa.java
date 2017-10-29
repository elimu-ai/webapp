package ai.elimu.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;

import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.project.Project;

public class ProjectDaoJpa extends GenericDaoJpa<Project> implements ProjectDao {

    @Override
	@Transactional
    public Project read(String projectName) throws DataAccessException {
        try {
            return (Project) em.createQuery(
                "SELECT p " +
                "FROM Project p " +
                "WHERE p.name = :name")
                .setParameter("name", projectName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Project '" + projectName + "' was not found");
            return null;
        }
    }

    @Override
	@Transactional
    public List<Project> read(Contributor projectManager) throws DataAccessException {
        return (List<Project>) em.createQuery(
            "SELECT project " +
            "FROM Project project " +
            "WHERE :projectManager MEMBER OF project.managers")
            .setParameter("projectManager", projectManager)
            .getResultList();
    }
}
