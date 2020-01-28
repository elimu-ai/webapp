package ai.elimu.web.content.word;

import ai.elimu.dao.AllophoneDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;
import ai.elimu.web.content.number.NumberListController;
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
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Words
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        List<Word> wordsGenerated = generateWords(contributor.getLocale());
        for (Word word : wordsGenerated) {
            Word existingWord = wordDao.readByText(word.getLocale(), word.getText());
            if (existingWord == null) {
                // Verify that only valid Allophones are used (copied from WordCreateController#handleSubmit)
                String allAllophonesCombined = "";
                for (Allophone allophone : allophones) {
                    allAllophonesCombined += allophone.getValueIpa();
                }
                if (StringUtils.isNotBlank(word.getPhonetics())) {
                    for (char allophoneCharacter : word.getPhonetics().toCharArray()) {
                        String allophone = String.valueOf(allophoneCharacter);
                        if (!allAllophonesCombined.contains(allophone)) {
                            throw new IllegalArgumentException("Invalid Allophone in the Word \"" + word.getText() + "\": /" + allophone + "/");
                        }
                    }
                }
                
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
     * Note: these number words should be generated _before_ their corresponding numbers (see 
     * {@link NumberListController#generateNumbers(ai.elimu.model.enums.Locale)}).
     */
    private List<Word> generateWords(Locale locale) {
        List<Word> words = new ArrayList<>();
        
        // Add number words

        Word wordZero = new Word();
        wordZero.setLocale(locale);
        wordZero.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordZero.setText("zero");
            wordZero.setPhonetics("ˈzɪrɔʊ");
        } else if (locale == Locale.FI) {
            wordZero.setText("sero");
            wordZero.setPhonetics("sɛr̩ɔ");
        } else if (locale == Locale.SW) {
            wordZero.setText("sifuri");
            wordZero.setPhonetics("siˈfuɾi");
        }
        words.add(wordZero);

        Word wordOne = new Word();
        wordOne.setLocale(locale);
        wordOne.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordOne.setText("one");
            wordOne.setPhonetics("wʌn");
        } else if (locale == Locale.FI) {
            wordOne.setText("isa");
            wordOne.setPhonetics("ɪsɑ");
        } else if (locale == Locale.SW) {
            wordOne.setText("moja");
            wordOne.setPhonetics("ˈmɔjɑ");
            wordOne.setUsageCount(29);
        }
        words.add(wordOne);

        Word wordTwo = new Word();
        wordTwo.setLocale(locale);
        wordTwo.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordTwo.setText("two");
            wordTwo.setPhonetics("tu");
        } else if (locale == Locale.FI) {
            wordTwo.setText("dalawa");
            wordTwo.setPhonetics("dɑlɑwɑ");
        } else if (locale == Locale.SW) {
            wordTwo.setText("mbili");
            wordTwo.setPhonetics("mˈbili");
        }
        words.add(wordTwo);

        Word wordThree = new Word();
        wordThree.setLocale(locale);
        wordThree.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordThree.setText("three");
            wordThree.setPhonetics("θri");
        } else if (locale == Locale.FI) {
            wordThree.setText("tatlo");
            wordThree.setPhonetics("tɑtlɔ");
        } else if (locale == Locale.SW) {
            wordThree.setText("tatu");
            wordThree.setPhonetics("ˈtɑtu");
            wordThree.setUsageCount(6);
        }
        words.add(wordThree);

        Word wordFour = new Word();
        wordFour.setLocale(locale);
        wordFour.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordFour.setText("four");
            wordFour.setPhonetics("fɔr");
        } else if (locale == Locale.FI) {
            wordFour.setText("apat");
            wordFour.setPhonetics("ɑpɑt");
        } else if (locale == Locale.SW) {
            wordFour.setText("nne");
            wordFour.setPhonetics("nˈnɛ");
        }
        words.add(wordFour);

        Word wordFive = new Word();
        wordFive.setLocale(locale);
        wordFive.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordFive.setText("five");
            wordFive.setPhonetics("fɑɪv");
        } else if (locale == Locale.FI) {
            wordFive.setText("lima");
            wordFive.setPhonetics("lɪmɑ");
        } else if (locale == Locale.SW) {
            wordFive.setText("tano");
            wordFive.setPhonetics("ˈtɑnɔ");
        }
        words.add(wordFive);

        Word wordSix = new Word();
        wordSix.setLocale(locale);
        wordSix.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordSix.setText("six");
            wordSix.setPhonetics("sɪks");
        } else if (locale == Locale.FI) {
            wordSix.setText("anim");
            wordSix.setPhonetics("ɑnɪm");
        } else if (locale == Locale.SW) {
            wordSix.setText("sita");
            wordSix.setPhonetics("ˈsitɑ");
            wordSix.setUsageCount(3);
        }
        words.add(wordSix);

        Word wordSeven = new Word();
        wordSeven.setLocale(locale);
        wordSeven.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordSeven.setText("seven");
            wordSeven.setPhonetics("ˈsɛvən");
        } else if (locale == Locale.FI) {
            wordSeven.setText("pito");
            wordSeven.setPhonetics("pɪtɔ");
        } else if (locale == Locale.SW) {
            wordSeven.setText("saba");
            wordSeven.setPhonetics("ˈsɑbɑ");
            wordSeven.setUsageCount(4);
        }
        words.add(wordSeven);

        Word wordEight = new Word();
        wordEight.setLocale(locale);
        wordEight.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordEight.setText("eight");
            wordEight.setPhonetics("ɛɪt");
        } else if (locale == Locale.FI) {
            wordEight.setText("walo");
            wordEight.setPhonetics("wɑlɔ");
        } else if (locale == Locale.SW) {
            wordEight.setText("nane");
            wordEight.setPhonetics("ˈnɑnɛ");
            wordEight.setUsageCount(1);
        }
        words.add(wordEight);

        Word wordNine = new Word();
        wordNine.setLocale(locale);
        wordNine.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordNine.setText("nine");
            wordNine.setPhonetics("nɑɪn");
        } else if (locale == Locale.FI) {
            wordNine.setText("siyam");
            wordNine.setPhonetics("ʃɑm");
        } else if (locale == Locale.SW) {
            wordNine.setText("tisa");
            wordNine.setPhonetics("ˈtisɑ");
        }
        words.add(wordNine);
        
        Word wordTen = new Word();
        wordTen.setLocale(locale);
        wordTen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordTen.setText("ten");
            wordTen.setPhonetics("tɛn");
        } else if (locale == Locale.FI) {
            wordTen.setText("sampu");
            wordTen.setPhonetics("sɑmpu");
        } else if (locale == Locale.SW) {
            wordTen.setText("kumi");
            wordTen.setPhonetics("kumi");
            wordTen.setUsageCount(2);
        }
        words.add(wordTen);
        
        if (locale == Locale.SW) {
            // E.g. "kumi na mbili", which literally means "ten and two" (12)
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
        if (locale == Locale.EN) {
            wordEleven.setText("eleven");
            wordEleven.setPhonetics("ɪˈlɛvən");
        } else if (locale == Locale.FI) {
            wordEleven.setText("labing-isa"); // TODO: handle "labing" and "isa" as separate words?
            wordEleven.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordEleven);
        
        Word wordTwelve = new Word();
        wordTwelve.setLocale(locale);
        wordTwelve.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordTwelve.setText("twelve");
            wordTwelve.setPhonetics("twɛlv");
        } else if (locale == Locale.FI) {
            wordTwelve.setText("labindalawa");
            wordTwelve.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordTwelve);
        
        Word wordThirteen = new Word();
        wordThirteen.setLocale(locale);
        wordThirteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordThirteen.setText("thirteen");
            wordThirteen.setPhonetics("θɛrtin");
        } else if (locale == Locale.FI) {
            wordThirteen.setText("labintatlo");
            wordThirteen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordThirteen);
        
        Word wordFourteen = new Word();
        wordFourteen.setLocale(locale);
        wordFourteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordFourteen.setText("fourteen");
            wordFourteen.setPhonetics("ˈfɔrˈtin");
        } else if (locale == Locale.FI) {
            wordFourteen.setText("labing-apat");
            wordFourteen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordFourteen);
        
        Word wordFifteen = new Word();
        wordFifteen.setLocale(locale);
        wordFifteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordFifteen.setText("fifteen");
            wordFifteen.setPhonetics("fɪfˈtin");
        } else if (locale == Locale.FI) {
            wordFifteen.setText("labinlima");
            wordFifteen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordFifteen);
        
        Word wordSixteen = new Word();
        wordSixteen.setLocale(locale);
        wordSixteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordSixteen.setText("sixteen");
            wordSixteen.setPhonetics("sɪkˈstin");
        } else if (locale == Locale.FI) {
            wordSixteen.setText("labing-anim");
            wordSixteen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordSixteen);
        
        Word wordSeventeen = new Word();
        wordSeventeen.setLocale(locale);
        wordSeventeen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordSeventeen.setText("seventeen");
            wordSeventeen.setPhonetics("ˈsɛvənˈtin");
        } else if (locale == Locale.FI) {
            wordSeventeen.setText("labimpito");
            wordSeventeen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordSeventeen);
        
        Word wordEighteen = new Word();
        wordEighteen.setLocale(locale);
        wordEighteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordEighteen.setText("eighteen");
            wordEighteen.setPhonetics("ɛɪtin");
        } else if (locale == Locale.FI) {
            wordEighteen.setText("labingwalo");
            wordEighteen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordEighteen);
        
        Word wordNineteen = new Word();
        wordNineteen.setLocale(locale);
        wordNineteen.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordNineteen.setText("nineteen");
            wordNineteen.setPhonetics("ˈnaɪnˈtin");
        } else if (locale == Locale.FI) {
            wordNineteen.setText("labinsiyam");
            wordNineteen.setPhonetics("ˌˌˌ"); // TODO
        }
        words.add(wordNineteen);
        
        Word wordTwenty = new Word();
        wordTwenty.setLocale(locale);
        wordTwenty.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordTwenty.setText("twenty");
            wordTwenty.setPhonetics("ˈtwɛnti");
        } else if (locale == Locale.FI) {
            wordTwenty.setText("dalawampu");
            wordTwenty.setPhonetics("ˌˌˌ"); // TODO
        } else if (locale == Locale.SW) {
            wordTwenty.setText("ishirini");
            wordTwenty.setPhonetics("iʃiɾini");
        }
        words.add(wordTwenty);
        
        if (locale == Locale.FI) {
            // "dalawampu't" = "dalawampu at", e.g. in "dalawampu't isa" (21)
            Word wordTwentyAnd = new Word();
            wordTwentyAnd.setLocale(locale);
            wordTwentyAnd.setTimeLastUpdate(Calendar.getInstance());
            wordTwentyAnd.setText("dalawampu't"); // TODO: handle apostrophe in "dalawampu't" ("dalawampu at")
            wordTwentyAnd.setPhonetics("ˌˌˌ"); // TODO
            words.add(wordTwentyAnd);
        }
        
        if (locale == Locale.EN) {
            Word wordTwentyOne = new Word();
            wordTwentyOne.setLocale(locale);
            wordTwentyOne.setTimeLastUpdate(Calendar.getInstance());
            wordTwentyOne.setText("twenty-one"); // TODO: handle words separated by hyphen
            wordTwentyOne.setPhonetics("ˈtwɛntiˈwʌn"); // TODO: handle pauses (whitespaces)
            words.add(wordTwentyOne);

            Word wordTwentyTwo = new Word();
            wordTwentyTwo.setLocale(locale);
            wordTwentyTwo.setTimeLastUpdate(Calendar.getInstance());
            wordTwentyTwo.setText("twenty-two"); // TODO: handle words separated by hyphen
            wordTwentyTwo.setPhonetics("ˈtwɛntiˈtu"); // TODO: handle pauses (whitespaces)
            words.add(wordTwentyTwo);
        }
        
        Word wordThirty = new Word();
        wordThirty.setLocale(locale);
        wordThirty.setTimeLastUpdate(Calendar.getInstance());
        if (locale == Locale.EN) {
            wordThirty.setText("thirty");
            wordThirty.setPhonetics("ˈθɝtɪ");
        } else if (locale == Locale.FI) {
            wordThirty.setText("tatlumpu");
            wordThirty.setPhonetics("ˌˌˌ"); // TODO
        } else if (locale == Locale.SW) {
            wordThirty.setText("thelathini");
            wordThirty.setPhonetics("θɛlɑθini");
        }
        words.add(wordThirty);
        
        // TODO: add 
        
        return words;
    }
}
