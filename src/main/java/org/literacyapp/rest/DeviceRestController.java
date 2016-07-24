package org.literacyapp.rest;

import com.google.gson.Gson;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.literacyapp.dao.DeviceDao;
import org.literacyapp.model.Device;
import org.literacyapp.model.gson.DeviceGson;
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
    public String create(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum
            @RequestParam String deviceManufacturer,
            @RequestParam String deviceModel,
            @RequestParam String deviceSerial,
            @RequestParam Integer osVersion,
            @RequestParam String locale,
            @RequestParam Boolean rooted
    ) {
        logger.info("create");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        Device device = deviceDao.read(deviceId);
        if (device == null) {
            device = new Device();
            device.setDeviceId(deviceId);
            device.setDeviceManufacturer(deviceManufacturer);
            device.setDeviceModel(deviceModel);
            device.setDeviceSerial(deviceSerial);
            device.setTimeRegistered(Calendar.getInstance());
            device.setOsVersion(osVersion);
            device.setLocale(locale);
            device.setRooted(rooted);
            deviceDao.create(device);
        }
        
        DeviceGson deviceGson = JavaToGsonConverter.getDeviceGson(device);
        String json = new Gson().toJson(deviceGson);
        logger.info("json: " + json);
        return json;
    }
    
    @RequestMapping("/read/{deviceId}")
    public DeviceGson read(@PathVariable String deviceId) {
        logger.info("read");
        
        logger.info("deviceId: " + deviceId);
        Device device = deviceDao.read(deviceId);
        DeviceGson deviceJson = JavaToGsonConverter.getDeviceGson(device);
        return deviceJson;
    }
    
    // TODO: update
}
