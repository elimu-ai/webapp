package ai.elimu.dao.jpa;

import jakarta.persistence.NoResultException;
import ai.elimu.dao.DeviceDao;
import ai.elimu.entity.Device;

import org.springframework.dao.DataAccessException;

@Deprecated
public class DeviceDaoJpa extends GenericDaoJpa<Device> implements DeviceDao {

    @Override
    public Device read(String androidId) throws DataAccessException {
        try {
            return (Device) em.createQuery(
                "SELECT d " +
                "FROM Device d " +
                "WHERE d.androidId = :androidId")
                .setParameter("androidId", androidId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
