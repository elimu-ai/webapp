package ai.elimu.dao;

import ai.elimu.model.Device;

import org.springframework.dao.DataAccessException;

public interface DeviceDao extends GenericDao<Device> {
	
    Device read(String androidId) throws DataAccessException;
}
