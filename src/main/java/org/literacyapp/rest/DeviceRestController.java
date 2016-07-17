package org.literacyapp.rest;

import java.util.Calendar;
import org.apache.log4j.Logger;
import org.literacyapp.dao.DeviceDao;
import org.literacyapp.model.Device;
import org.literacyapp.model.json.DeviceJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/device")
public class DeviceRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private DeviceDao deviceDao;
    
    @RequestMapping("/create")
    public Device create(
            @RequestParam String deviceId,
            @RequestParam String deviceModel,
            @RequestParam Integer osVersion,
            @RequestParam String locale,
            @RequestParam Boolean rooted
    ) {
        logger.info("create");
        
        logger.info("deviceId: " + deviceId);
        Device device = deviceDao.read(deviceId);
        if (device == null) {
            device = new Device();
            device.setDeviceId(deviceId);
            device.setDeviceModel(deviceModel);
            device.setTimeRegistered(Calendar.getInstance());
            device.setOsVersion(osVersion);
            device.setLocale(locale);
            device.setRooted(rooted);
            deviceDao.create(device);
        }
        
        return device;
    }
    
    @RequestMapping("/read/{deviceId}")
    public DeviceJson read(@PathVariable String deviceId) {
        logger.info("read");
        
        logger.info("deviceId: " + deviceId);
        Device device = deviceDao.read(deviceId);
        DeviceJson deviceJson = JavaToJsonConverter.getDeviceJson(device);
        return deviceJson;
    }
    
    // TODO: update
}
