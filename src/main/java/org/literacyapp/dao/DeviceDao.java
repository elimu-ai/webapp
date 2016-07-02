package org.literacyapp.dao;

import org.literacyapp.model.Device;

import org.springframework.dao.DataAccessException;

public interface DeviceDao extends GenericDao<Device> {
	
    Device read(String deviceId) throws DataAccessException;
}
