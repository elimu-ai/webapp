package org.literacyapp.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.enums.Language;
import org.literacyapp.model.json.NumberJson;
import org.literacyapp.rest.util.JavaToJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/number")
public class NumberRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @RequestMapping("/read")
    public List<NumberJson> read(@RequestParam Language language) {
        logger.info("read");
        
        logger.info("language: " + language);
        
        List<NumberJson> numberJsons = new ArrayList<>();
        for (org.literacyapp.model.Number number : numberDao.readAllOrdered(language)) {
            NumberJson numberJson = JavaToJsonConverter.getNumberJson(number);
            numberJsons.add(numberJson);
        }
        return numberJsons;
    }
}
