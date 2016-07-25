package org.literacyapp.rest.v1.content;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.gson.content.WordGson;
import org.literacyapp.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1/content/word")
public class WordRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping("/list")
    public List<String> list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        List<String> words = new ArrayList<>();
        for (Word word : wordDao.readAllOrdered(locale)) {
            WordGson wordJson = JavaToGsonConverter.getWordGson(word);
            String json = new Gson().toJson(wordJson);
            words.add(json);
        }
        return words;
    }
}
