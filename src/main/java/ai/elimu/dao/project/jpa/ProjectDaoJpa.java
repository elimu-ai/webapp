package ai.elimu.dao.project.jpa;

import ai.elimu.dao.jpa.GenericDaoJpa;
import javax.persistence.NoResultException;
import ai.elimu.dao.project.ProjectDao;
import ai.elimu.model.Contributor;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.project.Project;
import java.util.List;

public class ProjectDaoJpa extends GenericDaoJpa<Project> implements ProjectDao {

    @Override
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
    public List<Project> read(Contributor projectManager) throws DataAccessException {
        return (List<Project>) em.createQuery(
            "SELECT project " +
            "FROM Project project " +
            "WHERE :projectManager MEMBER OF project.managers")
            .setParameter("projectManager", projectManager)
            .getResultList();
    }
}
