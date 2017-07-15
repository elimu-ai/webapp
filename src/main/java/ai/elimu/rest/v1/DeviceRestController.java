package ai.elimu.rest.v1;

import com.google.gson.Gson;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ai.elimu.dao.DeviceDao;
import ai.elimu.model.Device;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.DeviceGson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/device", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
            @RequestParam String applicationId,
            @RequestParam Integer appVersionCode,
            @RequestParam Locale locale
    ) {
        logger.info("create");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        logger.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        
        JSONObject jSONObject = new JSONObject();
        Device device = deviceDao.read(deviceId);
        if (device != null) {
            jSONObject.put("result", "error");
            jSONObject.put("description", "Device already exists");
        } else {
            device = new Device();
            device.setDeviceId(deviceId);
            device.setDeviceManufacturer(deviceManufacturer);
            device.setDeviceModel(deviceModel);
            device.setDeviceSerial(deviceSerial);
            device.setTimeRegistered(Calendar.getInstance());
            device.setRemoteAddress(request.getRemoteAddr());
            device.setOsVersion(osVersion);
            device.setLocale(locale);
            deviceDao.create(device);
            
            DeviceGson deviceGson = JavaToGsonConverter.getDeviceGson(device);
            jSONObject.put("result", "success");
            jSONObject.put("device", new Gson().toJson(deviceGson));
        }
        
        logger.info("jSONObject: " + jSONObject);
        return jSONObject.toString();
    }
    
    @RequestMapping("/read/{deviceId}")
    public String read(@PathVariable String deviceId) {
        logger.info("read");
        
        logger.info("deviceId: " + deviceId);

        JSONObject jSONObject = new JSONObject();
        Device device = deviceDao.read(deviceId);
        if (device == null) {
            jSONObject.put("result", "error");
            jSONObject.put("description", "Device not found");
        } else {
            DeviceGson deviceGson = JavaToGsonConverter.getDeviceGson(device);
            jSONObject.put("result", "success");
            jSONObject.put("device", new Gson().toJson(deviceGson));
        }
        
        logger.info("jSONObject: " + jSONObject);
        return jSONObject.toString();
    }
    
    // TODO: update
}
