package org.literacyapp.web.content.word;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
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
            }
        }
        
        List<Word> words = wordDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("words", words);

        return "content/word/list";
    }
    
    /**
     * Note: these number words should be generated _before_ their corresponding numbers.
     */
    private List<Word> generateWords(Locale locale) {
        List<Word> words = new ArrayList<>();
        
        // Add number words

        Word wordZero = new Word();
        wordZero.setLocale(locale);
        wordZero.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordZero.setText("zero");
            wordZero.setPhonetics("ˈzɪroʊ");
        } else if (locale == Locale.ES) {
            wordZero.setText("cero");
//            wordZero.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordZero.setText("sifuri");
            wordZero.setPhonetics("siˈfuɾi");
        }
        words.add(wordZero);

        Word wordOne = new Word();
        wordOne.setLocale(locale);
        wordOne.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordOne.setText("one");
            wordOne.setPhonetics("wʌn");
        } else if (locale == Locale.ES) {
            wordOne.setText("uno");
//            wordOne.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordOne.setText("moja");
            wordOne.setPhonetics("ˈmɔja");
        }
        words.add(wordOne);

        Word wordTwo = new Word();
        wordTwo.setLocale(locale);
        wordTwo.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordTwo.setText("two");
            wordTwo.setPhonetics("tu");
        } else if (locale == Locale.ES) {
            wordTwo.setText("dos");
//            wordTwo.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordTwo.setText("mbili");
            wordTwo.setPhonetics("mˈbili");
        }
        words.add(wordTwo);

        Word wordThree = new Word();
        wordThree.setLocale(locale);
        wordThree.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordThree.setText("three");
            wordThree.setPhonetics("θri");
        } else if (locale == Locale.ES) {
            wordThree.setText("tres");
//            wordThree.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordThree.setText("tatu");
            wordThree.setPhonetics("ˈtɑtu");
        }
        words.add(wordThree);

        Word wordFour = new Word();
        wordFour.setLocale(locale);
        wordFour.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordFour.setText("four");
            wordFour.setPhonetics("fɔr");
        } else if (locale == Locale.ES) {
            wordFour.setText("cuatro");
//            wordFour.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordFour.setText("nne");
            wordFour.setPhonetics("nˈnɛ");
        }
        words.add(wordFour);

        Word wordFive = new Word();
        wordFive.setLocale(locale);
        wordFive.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordFive.setText("five");
            wordFive.setPhonetics("faɪv");
        } else if (locale == Locale.ES) {
            wordFive.setText("cinco");
//            wordFive.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordFive.setText("tano");
            wordFive.setPhonetics("ˈtɑnɔ");
        }
        words.add(wordFive);

        Word wordSix = new Word();
        wordSix.setLocale(locale);
        wordSix.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordSix.setText("six");
            wordSix.setPhonetics("sɪks");
        } else if (locale == Locale.ES) {
            wordSix.setText("seis");
//            wordSix.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordSix.setText("sita");
            wordSix.setPhonetics("ˈsitɑ");
        }
        words.add(wordSix);

        Word wordSeven = new Word();
        wordSeven.setLocale(locale);
        wordSeven.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordSeven.setText("seven");
            wordSeven.setPhonetics("ˈsɛvən");
        } else if (locale == Locale.ES) {
            wordSeven.setText("siete");
//            wordSeven.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordSeven.setText("saba");
            wordSeven.setPhonetics("ˈsɑbɑ");
        }
        words.add(wordSeven);

        Word wordEight = new Word();
        wordEight.setLocale(locale);
        wordEight.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordEight.setText("eight");
            wordEight.setPhonetics("eɪt");
        } else if (locale == Locale.ES) {
            wordEight.setText("ocho");
//            wordEight.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordEight.setText("nane");
            wordEight.setPhonetics("ˈnɑnɛ");
        }
        words.add(wordEight);

        Word wordNine = new Word();
        wordNine.setLocale(locale);
        wordNine.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
        } else if (locale == Locale.EN) {
            wordNine.setText("nine");
            wordNine.setPhonetics("naɪn");
        } else if (locale == Locale.ES) {
            wordNine.setText("nueve");
//            wordNine.setPhonetics(TODO);
        } else if (locale == Locale.SW) {
            wordNine.setText("tisa");
            wordNine.setPhonetics("ˈtisɑ");
        }
        words.add(wordNine);
        
        return words;
    }
}
