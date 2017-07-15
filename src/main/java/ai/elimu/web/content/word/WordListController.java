package ai.elimu.web.content.word;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;
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
        
        List<Word> words = wordDao.readAllOrderedByUsage(contributor.getLocale());
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
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordZero.setText("zero");
            wordZero.setPhonetics("ˈzɪrɔʊ");
            words.add(wordZero);
        } else if (locale == Locale.ES) {
            wordZero.setText("cero");
//            wordZero.setPhonetics(TODO);
            words.add(wordZero);
        } else if (locale == Locale.SW) {
            wordZero.setText("sifuri");
            wordZero.setPhonetics("siˈfuɾi");
            words.add(wordZero);
        }

        Word wordOne = new Word();
        wordOne.setLocale(locale);
        wordOne.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordOne.setText("one");
            wordOne.setPhonetics("wʌn");
            words.add(wordOne);
        } else if (locale == Locale.ES) {
            wordOne.setText("uno");
//            wordOne.setPhonetics(TODO);
            words.add(wordOne);
        } else if (locale == Locale.SW) {
            wordOne.setText("moja");
            wordOne.setPhonetics("ˈmɔjɑ");
            wordOne.setUsageCount(29);
            words.add(wordOne);
        }

        Word wordTwo = new Word();
        wordTwo.setLocale(locale);
        wordTwo.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordTwo.setText("two");
            wordTwo.setPhonetics("tu");
            words.add(wordTwo);
        } else if (locale == Locale.ES) {
            wordTwo.setText("dos");
//            wordTwo.setPhonetics(TODO);
            words.add(wordTwo);
        } else if (locale == Locale.SW) {
            wordTwo.setText("mbili");
            wordTwo.setPhonetics("mˈbili");
            words.add(wordTwo);
        }

        Word wordThree = new Word();
        wordThree.setLocale(locale);
        wordThree.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordThree.setText("three");
            wordThree.setPhonetics("θri");
            words.add(wordThree);
        } else if (locale == Locale.ES) {
            wordThree.setText("tres");
//            wordThree.setPhonetics(TODO);
            words.add(wordThree);
        } else if (locale == Locale.SW) {
            wordThree.setText("tatu");
            wordThree.setPhonetics("ˈtɑtu");
            wordThree.setUsageCount(6);
            words.add(wordThree);
        }

        Word wordFour = new Word();
        wordFour.setLocale(locale);
        wordFour.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordFour.setText("four");
            wordFour.setPhonetics("fɔr");
            words.add(wordFour);
        } else if (locale == Locale.ES) {
            wordFour.setText("cuatro");
//            wordFour.setPhonetics(TODO);
            words.add(wordFour);
        } else if (locale == Locale.SW) {
            wordFour.setText("nne");
            wordFour.setPhonetics("nˈnɛ");
            words.add(wordFour);
        }

        Word wordFive = new Word();
        wordFive.setLocale(locale);
        wordFive.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordFive.setText("five");
            wordFive.setPhonetics("fɑɪv");
            words.add(wordFive);
        } else if (locale == Locale.ES) {
            wordFive.setText("cinco");
//            wordFive.setPhonetics(TODO);
            words.add(wordFive);
        } else if (locale == Locale.SW) {
            wordFive.setText("tano");
            wordFive.setPhonetics("ˈtɑnɔ");
            words.add(wordFive);
        }

        Word wordSix = new Word();
        wordSix.setLocale(locale);
        wordSix.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordSix.setText("six");
            wordSix.setPhonetics("sɪks");
            words.add(wordSix);
        } else if (locale == Locale.ES) {
            wordSix.setText("seis");
//            wordSix.setPhonetics(TODO);
            words.add(wordSix);
        } else if (locale == Locale.SW) {
            wordSix.setText("sita");
            wordSix.setPhonetics("ˈsitɑ");
            wordSix.setUsageCount(3);
            words.add(wordSix);
        }

        Word wordSeven = new Word();
        wordSeven.setLocale(locale);
        wordSeven.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordSeven.setText("seven");
            wordSeven.setPhonetics("ˈsɛvən");
            words.add(wordSeven);
        } else if (locale == Locale.ES) {
            wordSeven.setText("siete");
//            wordSeven.setPhonetics(TODO);
            words.add(wordSeven);
        } else if (locale == Locale.SW) {
            wordSeven.setText("saba");
            wordSeven.setPhonetics("ˈsɑbɑ");
            wordSeven.setUsageCount(4);
            words.add(wordSeven);
        }

        Word wordEight = new Word();
        wordEight.setLocale(locale);
        wordEight.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordEight.setText("eight");
            wordEight.setPhonetics("ɛɪt");
            words.add(wordEight);
        } else if (locale == Locale.ES) {
            wordEight.setText("ocho");
//            wordEight.setPhonetics(TODO);
            words.add(wordEight);
        } else if (locale == Locale.SW) {
            wordEight.setText("nane");
            wordEight.setPhonetics("ˈnɑnɛ");
            wordEight.setUsageCount(1);
            words.add(wordEight);
        }

        Word wordNine = new Word();
        wordNine.setLocale(locale);
        wordNine.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordNine.setText("nine");
            wordNine.setPhonetics("nɑɪn");
            words.add(wordNine);
        } else if (locale == Locale.ES) {
            wordNine.setText("nueve");
//            wordNine.setPhonetics(TODO);
            words.add(wordNine);
        } else if (locale == Locale.SW) {
            wordNine.setText("tisa");
            wordNine.setPhonetics("ˈtisɑ");
            words.add(wordNine);
        }
        
        Word wordTen = new Word();
        wordTen.setLocale(locale);
        wordTen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordTen.setText("ten");
            wordTen.setPhonetics("tɛn");
            words.add(wordTen);
        } else if (locale == Locale.ES) {
            wordTen.setText("diez");
//            word???.setPhonetics(TODO);
            words.add(wordTen);
        } else if (locale == Locale.SW) {
            wordTen.setText("kumi");
            wordTen.setPhonetics("kumi");
            wordTen.setUsageCount(2);
            words.add(wordTen);
        }
        
        if (locale == Locale.SW) {
            Word wordNa = new Word();
            wordNa.setLocale(locale);
            wordNa.setTimeLastUpdate(Calendar.getInstance());
            wordNa.setText("na");
            wordNa.setPhonetics("nɑ");
            wordNa.setUsageCount(179);
            words.add(wordNa);
        }
        
        Word wordEleven = new Word();
        wordEleven.setLocale(locale);
        wordEleven.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordEleven.setText("eleven");
            wordEleven.setPhonetics("ɪˈlɛvən");
            words.add(wordEleven);
        } else if (locale == Locale.ES) {
            wordEleven.setText("once");
//            word???.setPhonetics(TODO);
            words.add(wordEleven);
        }
        
        Word wordTwelve = new Word();
        wordTwelve.setLocale(locale);
        wordTwelve.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordTwelve.setText("twelve");
            wordTwelve.setPhonetics("twɛlv");
            words.add(wordTwelve);
        } else if (locale == Locale.ES) {
            wordTwelve.setText("doce");
//            word???.setPhonetics(TODO);
            words.add(wordTwelve);
        }
        
        Word wordThirteen = new Word();
        wordThirteen.setLocale(locale);
        wordThirteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordThirteen.setText("thirteen");
            wordThirteen.setPhonetics("θɛrtin");
            words.add(wordThirteen);
        } else if (locale == Locale.ES) {
            wordThirteen.setText("trece");
//            word???.setPhonetics(TODO);
            words.add(wordThirteen);
        }
        
        Word wordFourteen = new Word();
        wordFourteen.setLocale(locale);
        wordFourteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordFourteen.setText("fourteen");
            wordFourteen.setPhonetics("ˈfɔrˈtin");
            words.add(wordFourteen);
        } else if (locale == Locale.ES) {
            wordFourteen.setText("catorce");
//            word???.setPhonetics(TODO);
            words.add(wordFourteen);
        }
        
        Word wordFifteen = new Word();
        wordFifteen.setLocale(locale);
        wordFifteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordFifteen.setText("fifteen");
            wordFifteen.setPhonetics("fɪfˈtin");
            words.add(wordFifteen);
        } else if (locale == Locale.ES) {
            wordFifteen.setText("quince");
//            word???.setPhonetics(TODO);
            words.add(wordFifteen);
        }
        
        Word wordSixteen = new Word();
        wordSixteen.setLocale(locale);
        wordSixteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordSixteen.setText("sixteen");
            wordSixteen.setPhonetics("sɪkˈstin");
            words.add(wordSixteen);
        } else if (locale == Locale.ES) {
            wordSixteen.setText("dieciséis");
//            word???.setPhonetics(TODO);
            words.add(wordSixteen);
        }
        
        Word wordSeventeen = new Word();
        wordSeventeen.setLocale(locale);
        wordSeventeen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordSeventeen.setText("seventeen");
            wordSeventeen.setPhonetics("ˈsɛvənˈtin");
            words.add(wordSeventeen);
        } else if (locale == Locale.ES) {
            wordSeventeen.setText("diecisiete");
//            word???.setPhonetics(TODO);
            words.add(wordSeventeen);
        }
        
        Word wordEighteen = new Word();
        wordEighteen.setLocale(locale);
        wordEighteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordEighteen.setText("eighteen");
            wordEighteen.setPhonetics("ɛɪtin");
            words.add(wordEighteen);
        } else if (locale == Locale.ES) {
            wordEighteen.setText("dieciocho");
//            word???.setPhonetics(TODO);
            words.add(wordEighteen);
        }
        
        Word wordNineteen = new Word();
        wordNineteen.setLocale(locale);
        wordNineteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordNineteen.setText("nineteen");
            wordNineteen.setPhonetics("ˈnaɪnˈtin");
            words.add(wordNineteen);
        } else if (locale == Locale.ES) {
            wordNineteen.setText("diecinueve");
//            word???.setPhonetics(TODO);
            words.add(wordNineteen);
        }
        
        Word wordTwenty = new Word();
        wordTwenty.setLocale(locale);
        wordTwenty.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.EN) {
            wordTwenty.setText("twenty");
            wordTwenty.setPhonetics("ˈtwɛnti");
            words.add(wordTwenty);
        } else if (locale == Locale.ES) {
            wordTwenty.setText("veinte");
//            word???.setPhonetics(TODO);
            words.add(wordTwenty);
        } else if (locale == Locale.SW) {
            wordTwenty.setText("ishirini");
            wordTwenty.setPhonetics("iʃiɾini");
            words.add(wordTwenty);
        }
        
        Word wordTwentyOne = new Word();
        wordTwentyOne.setLocale(locale);
        wordTwentyOne.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.AR) {
            // TODO: set text
            // TODO: set phonetics
//            words.add(word???);
        } else if (locale == Locale.ES) {
            wordTwentyOne.setText("veintiuno");
//            word???.setPhonetics(TODO);
            words.add(wordTwentyOne);
        }
        
        return words;
    }
}
