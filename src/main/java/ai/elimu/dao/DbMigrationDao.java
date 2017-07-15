package ai.elimu.dao;

import java.util.List;
import ai.elimu.model.DbMigration;

import org.springframework.dao.DataAccessException;

public interface DbMigrationDao extends GenericDao<DbMigration> {
	
    DbMigration read(Integer version) throws DataAccessException;
    
    List<DbMigration> readAllOrderedByVersionDesc() throws DataAccessException;
    
    void executeMigration(String script) throws DataAccessException;
}
