package ai.elimu.dao.jpa;

import javax.persistence.NoResultException;
import ai.elimu.dao.ProjectDao;
import ai.elimu.model.Contributor;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.project.Project;

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
    public Project read(Contributor projectManager) throws DataAccessException {
        try {
            return (Project) em.createQuery(
                "SELECT c.p " +
                "FROM Contributor c " +
                "WHERE c = :contributor")
                .setParameter("contributor", projectManager)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Project for Contributor '" + projectManager.getEmail() + "' was not found");
            return null;
        }
    }
}
