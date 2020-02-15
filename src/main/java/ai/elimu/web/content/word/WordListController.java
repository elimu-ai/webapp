package ai.elimu.web.content.word;

import ai.elimu.dao.AllophoneDao;
import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
public class WordListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<Word> words = wordDao.readAllOrderedByUsage(language);
        
        for (Word word : words) {
            copyValuesFromPhoneticsToAllophones(word);
            wordDao.update(word);
        }
        words = wordDao.readAllOrderedByUsage(language);
        
        model.addAttribute("words", words);
        
        int maxUsageCount = 0;
        for (Word word : words) {
            if (word.getUsageCount() > maxUsageCount) {
                maxUsageCount = word.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/word/list";
    }
    
    /**
     * Example with "they": /ðɛɪ/ --> /ð/ + /ɛɪ/
     */
    @Deprecated
    private void copyValuesFromPhoneticsToAllophones(Word word) {
        logger.info("copyValuesFromPhoneticsToAllophones");
        
        logger.info("word.getText(): \"" + word.getText() + "\"");
        logger.info("word.getPhonetics(): \"" + word.getPhonetics() + "\"");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Allophone> allAllophones = allophoneDao.readAllOrderedByIpaValueCharacterLength(language);
        
        List<Allophone> wordAllophones = new ArrayList<>();
        
        String phonetics = word.getPhonetics();
        while (StringUtils.isNotBlank(phonetics)) {
            logger.info("phonetics: /" + phonetics + "/");
            boolean isMatch = false;
            for (Allophone allophone : allAllophones) {
                logger.info("allophone.getValueIpa(): /" + allophone.getValueIpa() + "/");
                logger.info("phonetics.startsWith(allophone.getValueIpa()): " + phonetics.startsWith(allophone.getValueIpa()));
                if (phonetics.startsWith(allophone.getValueIpa())) {
                    isMatch = true;
                    wordAllophones.add(allophone);
                    phonetics = phonetics.substring(allophone.getValueIpa().length(), phonetics.length());
                    logger.info("phonetics (updated): /" + phonetics + "/");
                }
            }
            if (!isMatch) {
                logger.warn("No Allophones matched the beginning of the IPA value /" + phonetics + "/. Skipping Word.");
                return;
            }
        }
        
        word.setAllophones(wordAllophones);
    }
}
