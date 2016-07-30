package org.literacyapp.web.content.word;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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
    private ContentCreationEventDao contentCreationEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Words
        List<Word> wordsGenerated = generateWords(contributor.getLocale());
        for (Word word : wordsGenerated) {
            Word existingWord = wordDao.readByText(word.getLocale(), word.getText());
            if (existingWord == null) {
                wordDao.create(word);

                ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
                contentCreationEvent.setContributor(contributor);
                contentCreationEvent.setContent(word);
                contentCreationEvent.setCalendar(Calendar.getInstance());
                contentCreationEventDao.create(contentCreationEvent);
                
                if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                    String text = URLEncoder.encode(
                            contributor.getFirstName() + " just added a new Word:\n" + 
                            "• Language: \"" + word.getLocale().getLanguage() + "\"\n" + 
                            "• Text: \"" + word.getText() + "\"\n" + 
                            "• Phonetics: /" + word.getPhonetics() + "/\n" + 
                            "See ") + "http://literacyapp.org/content/word/list";
                    String iconUrl = contributor.getImageUrl();
                    SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
                }
            }
        }
        
        List<Word> words = wordDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("words", words);

        return "content/word/list";
    }
    
    private List<Word> generateWords(Locale locale) {
        List<Word> words = new ArrayList<>();
        
        if (locale == Locale.AR) {
            // Add number words
            // TODO
        } else if (locale == Locale.EN) {
            // Add number words
//            Word wordZero = new Word();
//            wordZero.setLocale(locale);
//            wordZero.setRevisionNumber(1);
//            wordZero.setTimeLastUpdate(Calendar.getInstance());
//            wordZero.setText("zero");
//            wordZero.setPhonetics("TODO");
//            words.add(wordZero);
//            
//            Word wordOne = new Word();
//            wordOne.setLocale(locale);
//            wordOne.setRevisionNumber(1);
//            wordOne.setTimeLastUpdate(Calendar.getInstance());
//            wordOne.setText("one");
//            wordOne.setPhonetics("TODO");
//            words.add(wordOne);
//            
//            Word wordTwo = new Word();
//            wordTwo.setLocale(locale);
//            wordTwo.setRevisionNumber(1);
//            wordTwo.setTimeLastUpdate(Calendar.getInstance());
//            wordTwo.setText("two");
//            wordTwo.setPhonetics("TODO");
//            words.add(wordTwo);
//            
//            Word wordThree = new Word();
//            wordThree.setLocale(locale);
//            wordThree.setRevisionNumber(1);
//            wordThree.setTimeLastUpdate(Calendar.getInstance());
//            wordThree.setText("three");
//            wordThree.setPhonetics("TODO");
//            words.add(wordThree);
//            
//            Word wordFour = new Word();
//            wordFour.setLocale(locale);
//            wordFour.setRevisionNumber(1);
//            wordFour.setTimeLastUpdate(Calendar.getInstance());
//            wordFour.setText("four");
//            wordFour.setPhonetics("TODO");
//            words.add(wordFour);
//            
//            Word wordFive = new Word();
//            wordFive.setLocale(locale);
//            wordFive.setRevisionNumber(1);
//            wordFive.setTimeLastUpdate(Calendar.getInstance());
//            wordFive.setText("five");
//            wordFive.setPhonetics("TODO");
//            words.add(wordFive);
//            
//            Word wordSix = new Word();
//            wordSix.setLocale(locale);
//            wordSix.setRevisionNumber(1);
//            wordSix.setTimeLastUpdate(Calendar.getInstance());
//            wordSix.setText("six");
//            wordSix.setPhonetics("TODO");
//            words.add(wordSix);
//            
//            Word wordSeven = new Word();
//            wordSeven.setLocale(locale);
//            wordSeven.setRevisionNumber(1);
//            wordSeven.setTimeLastUpdate(Calendar.getInstance());
//            wordSeven.setText("seven");
//            wordSeven.setPhonetics("TODO");
//            words.add(wordSeven);
//            
//            Word wordEight = new Word();
//            wordEight.setLocale(locale);
//            wordEight.setRevisionNumber(1);
//            wordEight.setTimeLastUpdate(Calendar.getInstance());
//            wordEight.setText("eight");
//            wordEight.setPhonetics("TODO");
//            words.add(wordEight);
//            
//            Word wordNine = new Word();
//            wordNine.setLocale(locale);
//            wordNine.setRevisionNumber(1);
//            wordNine.setTimeLastUpdate(Calendar.getInstance());
//            wordNine.setText("nine");
//            wordNine.setPhonetics("TODO");
//            words.add(wordNine);
        } else if (locale == Locale.ES) {
            // Add number words
            // TODO
        } else if (locale == Locale.SW) {
            // Add number words
            // TODO
        }
        
        return words;
    }
}
