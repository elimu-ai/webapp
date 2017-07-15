package ai.elimu.web.analytics.application_opened_event;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationOpenedEventDao;
import ai.elimu.dao.DeviceDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.Device;
import ai.elimu.model.analytics.ApplicationOpenedEvent;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Locale;
import ai.elimu.util.EventLineHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/application-opened-event/list")
public class ApplicationOpenedEventListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationOpenedEventDao applicationOpenedEventDao;
    
    @Autowired
    private DeviceDao deviceDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate events
        if (EnvironmentContextLoaderListener.env != Environment.PROD) {
            generateEvents(contributor.getLocale());
        }
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = applicationOpenedEventDao.readAll(contributor.getLocale());
        model.addAttribute("applicationOpenedEvents", applicationOpenedEvents);

        return "analytics/application-opened-event/list";
    }
    
    private List<ApplicationOpenedEvent> generateEvents(Locale locale) {
        logger.info("generateEvents");
        
        List<ApplicationOpenedEvent> applicationOpenedEvents = new ArrayList<>();
        
        String[] eventFiles = new String[] {
            "application_opened_events_2017-05-25.log",
            "application_opened_events_2017-05-27.log",
            "application_opened_events_2017-05-28.log",
            "application_opened_events_2017-05-29.log",
            "application_opened_events_2017-05-30.log",
            "application_opened_events_2017-06-02.log",
            "application_opened_events_2017-06-03.log",
            "application_opened_events_2017-06-04.log",
            "application_opened_events_2017-06-05.log",
            "application_opened_events_2017-06-06.log",
            "application_opened_events_2017-06-07.log",
            "application_opened_events_2017-06-08.log",
            "application_opened_events_2017-06-09.log",
        };
        for (String eventFile : eventFiles) {
            logger.info("eventFile: " + eventFile);
            URL url = getClass().getResource(eventFile);
            try {
                Reader reader = new FileReader(url.getFile());
                List<String> lines = IOUtils.readLines(reader);
                logger.info("lines.size(): " + lines.size());

                reader.close();

                for (String eventLine : lines) {
                    logger.info("eventLine: " + eventLine);
                    // Expected format: id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:ai.elimu.nyaqd|studentId:2312aff4939750ea_4

                    String deviceId = EventLineHelper.getDeviceId(eventLine);
                    Device device = deviceDao.read(deviceId);
                    logger.info("device: " + device);
                    if (device == null) {
                        device = new Device();
                        device.setDeviceId(deviceId);
                        device.setDeviceManufacturer("asdf");
                        device.setDeviceModel("asdf");
                        device.setDeviceSerial("asdf");
                        device.setRemoteAddress("0.0.0.0");
                        device.setOsVersion(23);
                        device.setLocale(locale);
                        deviceDao.create(device);
                    }

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
                        applicationOpenedEvents.add(applicationOpenedEvent);
                    }
                }
            } catch (FileNotFoundException ex) {
                logger.error(null, ex);
            } catch (IOException ex) {
                logger.error(null, ex);
            }
        }
        
        return applicationOpenedEvents;
    }
}
