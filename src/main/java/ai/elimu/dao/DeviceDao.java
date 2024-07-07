package ai.elimu.dao;

import ai.elimu.entity.Device;

import org.springframework.dao.DataAccessException;

public interface DeviceDao extends GenericDao<Device> {
	
    Device read(String deviceId) throws DataAccessException;
}
