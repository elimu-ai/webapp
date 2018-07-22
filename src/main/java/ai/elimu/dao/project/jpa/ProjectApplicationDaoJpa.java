package ai.elimu.dao.project.jpa;

import ai.elimu.dao.jpa.*;
import javax.persistence.NoResultException;
import ai.elimu.dao.project.ProjectApplicationDao;
import ai.elimu.model.admin.Application;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.project.Project;

public class ProjectApplicationDaoJpa extends GenericDaoJpa<Application> implements ProjectApplicationDao {
    
    @Override
    public Application readByPackageName(Project project, String packageName) throws DataAccessException {
        try {
            return (Application) em.createQuery(
                "SELECT a " +
                "FROM Application a " +
                "WHERE a.appGroup.appCategory.project = :project " +
                "AND a.packageName = :packageName")
                .setParameter("project", project)
                .setParameter("packageName", packageName)
                .getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Application with packageName \"" + packageName + "\" was not found for project " + project.getId() + " (" + project.getName() + ")", e);
            return null;
        }
    }
}
