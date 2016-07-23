package org.literacyapp.rest.content;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.gson.content.AllophoneGson;
import org.literacyapp.rest.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/content/allophone")
public class AllophoneRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @RequestMapping("/list")
    public List<String> list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        List<String> allophones = new ArrayList<>();
        for (Allophone allophone : allophoneDao.readAllOrdered(locale)) {
            AllophoneGson allophoneGson = JavaToGsonConverter.getAllophoneGson(allophone);
            String json = new Gson().toJson(allophoneGson);
            allophones.add(json);
        }
        return allophones;
    }
}
