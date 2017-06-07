package org.literacyapp.rest.v1.analytics;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.literacyapp.dao.ApplicationOpenedEventDao;
import org.literacyapp.dao.DeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/v1/analytics/application-opened-event")
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
                    
                    String[] eventLineValues = eventLine.split("|");
                    logger.info("eventLineValues: " + eventLineValues);
                    
                    for (String eventLineValue : eventLineValues) {
                        logger.info("eventLineValue: " + eventLineValue);
                        
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
