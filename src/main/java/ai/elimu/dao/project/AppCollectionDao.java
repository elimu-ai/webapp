package ai.elimu.dao.project;

import ai.elimu.dao.GenericDao;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.Project;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface AppCollectionDao extends GenericDao<AppCollection> {
    
    List<AppCollection> readAll(Project project) throws DataAccessException;
}
