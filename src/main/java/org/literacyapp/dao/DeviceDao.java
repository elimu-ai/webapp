package org.literacyapp.dao;

import java.util.List;
import org.literacyapp.model.Device;
import org.literacyapp.model.enums.Locale;

import org.springframework.dao.DataAccessException;

public interface DeviceDao extends GenericDao<Device> {
	
    Device read(String deviceId) throws DataAccessException;
    
    List<Device> readAll(Locale locale) throws DataAccessException;
}
