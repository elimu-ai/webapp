package ai.elimu.dao;

import ai.elimu.model.Contributor;
import ai.elimu.model.project.Project;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ProjectDao extends GenericDao<Project> {
    
    Project read(String projectName) throws DataAccessException;
    
    List<Project> read(Contributor projectManager) throws DataAccessException;
}
