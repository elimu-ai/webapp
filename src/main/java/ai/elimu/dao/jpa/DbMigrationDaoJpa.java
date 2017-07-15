package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.DbMigrationDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.DbMigration;

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
            logger.warn("DbMigration for version \"" + version + "\" was not found");
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
