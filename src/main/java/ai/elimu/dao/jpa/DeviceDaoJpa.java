package ai.elimu.dao.jpa;

import javax.persistence.NoResultException;
import ai.elimu.dao.DeviceDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.Device;

public class DeviceDaoJpa extends GenericDaoJpa<Device> implements DeviceDao {

    @Override
    public Device read(String deviceId) throws DataAccessException {
        try {
            return (Device) em.createQuery(
                "SELECT d " +
                "FROM Device d " +
                "WHERE d.deviceId = :deviceId")
                .setParameter("deviceId", deviceId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
