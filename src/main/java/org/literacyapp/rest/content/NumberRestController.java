package org.literacyapp.rest.content;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.gson.content.NumberGson;
import org.literacyapp.rest.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/content/number")
public class NumberRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @RequestMapping("/list")
    public List<NumberGson> list(@RequestParam Locale locale) {
        logger.info("list");
        
        logger.info("locale: " + locale);
        
        List<NumberGson> numberJsons = new ArrayList<>();
        for (org.literacyapp.model.content.Number number : numberDao.readAllOrdered(locale)) {
            NumberGson numberJson = JavaToGsonConverter.getNumberGson(number);
            numberJsons.add(numberJson);
        }
        return numberJsons;
    }
}
