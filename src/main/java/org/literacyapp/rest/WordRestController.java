package org.literacyapp.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.json.WordJson;
import org.literacyapp.rest.util.JavaToJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/word")
public class WordRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping("/read")
    public List<WordJson> read(@RequestParam Locale locale) {
        logger.info("read");
        
        logger.info("locale: " + locale);
        
        List<WordJson> wordJsons = new ArrayList<>();
        for (Word word : wordDao.readAllOrdered(locale)) {
            WordJson wordJson = JavaToJsonConverter.getWordJson(word);
            wordJsons.add(wordJson);
        }
        return wordJsons;
    }
}
