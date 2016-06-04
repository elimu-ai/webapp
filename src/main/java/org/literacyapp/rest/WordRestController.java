package org.literacyapp.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Word;
import org.literacyapp.model.enums.Language;
import org.literacyapp.model.json.WordJson;
import org.literacyapp.rest.util.JavaToJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/word")
public class WordRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping("/{language}")
    public List<WordJson> readAll(@PathVariable Language language) {
        logger.info("readAll");
        
        logger.info("language: " + language);
        
        List<WordJson> wordJsons = new ArrayList<>();
        for (Word word : wordDao.readAllOrdered(language)) {
            WordJson wordJson = JavaToJsonConverter.getWordJson(word);
            wordJsons.add(wordJson);
        }
        return wordJsons;
    }
}
