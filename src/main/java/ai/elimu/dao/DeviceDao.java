package ai.elimu.dao;

import org.springframework.dao.DataAccessException;

import ai.elimu.entity.Device;

@Deprecated
public interface DeviceDao extends GenericDao<Device> {
    
    Device read(String androidId) throws DataAccessException;
}
