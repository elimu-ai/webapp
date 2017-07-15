package ai.elimu.rest.v1.analytics;

import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import ai.elimu.dao.ApplicationOpenedEventDao;
import ai.elimu.dao.DeviceDao;
import ai.elimu.model.Device;
import ai.elimu.model.analytics.ApplicationOpenedEvent;
import ai.elimu.util.EventLineHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/rest/v1/analytics/application-opened-event", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApplicationOpenedEventRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationOpenedEventDao applicationOpenedEventDao;
    
    @Autowired
    private DeviceDao deviceDao;
    
    @RequestMapping("/create")
    public String create(
            HttpServletRequest request,
            // TODO: checksum,
            @RequestParam MultipartFile multipartFile
    ) {
        logger.info("create");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();
                Reader reader = new CharSequenceReader((new String(bytes)));
                List<String> lines = IOUtils.readLines(reader);
                logger.info("lines.size(): " + lines.size());
                reader.close();
                
                for (String eventLine : lines) {
                    logger.info("eventLine: " + eventLine);
                    // Expected format: id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:ai.elimu.nyaqd|studentId:2312aff4939750ea_4
                    
                    String deviceId = EventLineHelper.getDeviceId(eventLine);
                    Device device = deviceDao.read(deviceId);
                    logger.info("device: " + device);
                    
                    Calendar timeOfEvent = EventLineHelper.getTime(eventLine);
                    String packageName = EventLineHelper.getPackageName(eventLine);
                    
                    ApplicationOpenedEvent existingApplicationOpenedEvent = applicationOpenedEventDao.read(device, timeOfEvent, packageName);
                    logger.info("existingApplicationOpenedEvent: " + existingApplicationOpenedEvent);
                    if (existingApplicationOpenedEvent == null) {
                        ApplicationOpenedEvent applicationOpenedEvent = new ApplicationOpenedEvent();
                        applicationOpenedEvent.setDevice(device);
                        applicationOpenedEvent.setCalendar(timeOfEvent);
                        applicationOpenedEvent.setPackageName(packageName);
                        applicationOpenedEventDao.create(applicationOpenedEvent);
                    }
                }
            } catch (IOException ex) {
                logger.error(null, ex);
            }
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        // TODO: handle error
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
