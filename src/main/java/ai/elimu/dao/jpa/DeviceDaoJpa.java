package ai.elimu.dao.jpa;

import java.util.List;
import javax.persistence.NoResultException;
import ai.elimu.dao.DeviceDao;

import org.springframework.dao.DataAccessException;

import ai.elimu.model.Device;
import ai.elimu.model.enums.Locale;

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
            logger.warn("Device \"" + deviceId + "\" was not found");
            return null;
        }
    }

    @Override
    public List<Device> readAll(Locale locale) throws DataAccessException {
        return em.createQuery(
            "SELECT d " +
            "FROM Device d " +
            "WHERE d.locale = :locale " +
            "ORDER BY d.timeRegistered DESC")
            .setParameter("locale", locale)
            .getResultList();
    }
}
