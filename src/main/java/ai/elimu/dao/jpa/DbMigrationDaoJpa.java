package ai.elimu.dao.jpa;

import java.util.List;
import jakarta.persistence.NoResultException;
import ai.elimu.dao.DbMigrationDao;
import ai.elimu.entity.DbMigration;

import org.springframework.dao.DataAccessException;

public class DbMigrationDaoJpa extends GenericDaoJpa<DbMigration> implements DbMigrationDao {

    @Override
    public DbMigration read(Integer version) throws DataAccessException {
        try {
            return (DbMigration) em.createQuery(
                "SELECT dm " +
                "FROM DbMigration dm " +
                "WHERE dm.version = :version")
                .setParameter("version", version)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<DbMigration> readAllOrderedByVersionDesc() throws DataAccessException {
        return em.createQuery(
            "SELECT dm " +
            "FROM DbMigration dm " +
            "ORDER BY dm.version DESC")
            .getResultList();
    }

    @Override
    public void executeMigration(String script) throws DataAccessException {
        em.createNativeQuery(script).executeUpdate();
    }
}
