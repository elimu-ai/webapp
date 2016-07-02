package org.literacyapp.rest;

import org.apache.log4j.Logger;
import org.literacyapp.dao.DeviceDao;
import org.literacyapp.model.Device;
import org.literacyapp.model.json.DeviceJson;
import org.literacyapp.rest.util.JavaToJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/device")
public class DeviceRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private DeviceDao deviceDao;
    
    @RequestMapping("/read/{deviceId}")
    public DeviceJson readDeviceId(@PathVariable String deviceId) {
        logger.info("readDeviceId");
        
        logger.info("deviceId: " + deviceId);
        Device device = deviceDao.read(deviceId);
        DeviceJson deviceJson = JavaToJsonConverter.getDeviceJson(device);
        return deviceJson;
    }
    
    // TODO: create
}
