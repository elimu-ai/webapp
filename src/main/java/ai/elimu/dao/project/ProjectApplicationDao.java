package ai.elimu.dao.project;

import ai.elimu.dao.*;
import ai.elimu.model.admin.Application;

import ai.elimu.model.project.Project;

import org.springframework.dao.DataAccessException;

public interface ProjectApplicationDao extends GenericDao<Application> {
    
    Application readByPackageName(Project project, String packageName) throws DataAccessException;
}
