package ai.elimu.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.DbMigration;

public interface DbMigrationDao extends GenericDao<DbMigration> {
    
    DbMigration read(Integer version) throws DataAccessException;
    
    List<DbMigration> readAllOrderedByVersionDesc() throws DataAccessException;
    
    void executeMigration(String script) throws DataAccessException;
}
