package ai.elimu.web.content.word;

import ai.elimu.dao.AllophoneDao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
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
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        // To ease development/testing, auto-generate Words
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(language);
        List<Word> wordsGenerated = generateWords(language);
        for (Word word : wordsGenerated) {
            Word existingWord = wordDao.readByText(language, word.getText());
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
        
        List<Word> words = wordDao.readAllOrderedByUsage(language);
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
     * {@link NumberListController#generateNumbers(ai.elimu.model.enums.Language)}).
     */
    private List<Word> generateWords(Language language) {
        List<Word> words = new ArrayList<>();
        
        // Add number words

        Word wordZero = new Word();
        wordZero.setLanguage(language);
        wordZero.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordZero.setText("শূন্য");
            wordZero.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordZero.setText("zero");
            wordZero.setPhonetics("ˈzɪrɔʊ");
        } else if (language == Language.FIL) {
            wordZero.setText("sero");
            wordZero.setPhonetics("sɛr̩ɔ");
            wordZero.setAllophones(getAllophones(language, "s", "ɛ", "r̩", "ɔ"));
        } else if (language == Language.SWA) {
            wordZero.setText("sifuri");
            wordZero.setPhonetics("siˈfuɾi");
        }
        words.add(wordZero);

        Word wordOne = new Word();
        wordOne.setLanguage(language);
        wordOne.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordOne.setText("এক");
            wordOne.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordOne.setText("one");
            wordOne.setPhonetics("wʌn");
        } else if (language == Language.FIL) {
            wordOne.setText("isa");
            wordOne.setPhonetics("ɪsɑ");
            wordOne.setAllophones(getAllophones(language, "ɪ", "s", "ɑ"));
        } else if (language == Language.SWA) {
            wordOne.setText("moja");
            wordOne.setPhonetics("ˈmɔjɑ");
            wordOne.setUsageCount(29);
        }
        words.add(wordOne);

        Word wordTwo = new Word();
        wordTwo.setLanguage(language);
        wordTwo.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordTwo.setText("দুই");
            wordTwo.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordTwo.setText("two");
            wordTwo.setPhonetics("tu");
        } else if (language == Language.FIL) {
            wordTwo.setText("dalawa");
            wordTwo.setPhonetics("dɑlɑwɑ");
            wordTwo.setAllophones(getAllophones(language, "d", "ɑ", "l", "ɑ", "w", "ɑ"));
        } else if (language == Language.SWA) {
            wordTwo.setText("mbili");
            wordTwo.setPhonetics("mˈbili");
        }
        words.add(wordTwo);

        Word wordThree = new Word();
        wordThree.setLanguage(language);
        wordThree.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordThree.setText("তিন");
            wordThree.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordThree.setText("three");
            wordThree.setPhonetics("θri");
        } else if (language == Language.FIL) {
            wordThree.setText("tatlo");
            wordThree.setPhonetics("tɑtlɔ");
            wordThree.setAllophones(getAllophones(language, "t", "ɑ", "t", "l", "ɔ"));
        } else if (language == Language.SWA) {
            wordThree.setText("tatu");
            wordThree.setPhonetics("ˈtɑtu");
            wordThree.setUsageCount(6);
        }
        words.add(wordThree);

        Word wordFour = new Word();
        wordFour.setLanguage(language);
        wordFour.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordFour.setText("চার");
            wordFour.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordFour.setText("four");
            wordFour.setPhonetics("fɔr");
        } else if (language == Language.FIL) {
            wordFour.setText("apat");
            wordFour.setPhonetics("ɑpɑt");
            wordFour.setAllophones(getAllophones(language, "ɑ", "p", "ɑ", "t"));
        } else if (language == Language.SWA) {
            wordFour.setText("nne");
            wordFour.setPhonetics("nˈnɛ");
        }
        words.add(wordFour);

        Word wordFive = new Word();
        wordFive.setLanguage(language);
        wordFive.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordFive.setText("পাঁচ");
            wordFive.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordFive.setText("five");
            wordFive.setPhonetics("fɑɪv");
        } else if (language == Language.FIL) {
            wordFive.setText("lima");
            wordFive.setPhonetics("lɪmɑ");
            wordFive.setAllophones(getAllophones(language, "l", "ɪ", "m", "ɑ"));
        } else if (language == Language.SWA) {
            wordFive.setText("tano");
            wordFive.setPhonetics("ˈtɑnɔ");
        }
        words.add(wordFive);

        Word wordSix = new Word();
        wordSix.setLanguage(language);
        wordSix.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordSix.setText("ছয়");
            wordSix.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordSix.setText("six");
            wordSix.setPhonetics("sɪks");
        } else if (language == Language.FIL) {
            wordSix.setText("anim");
            wordSix.setPhonetics("ɑnɪm");
            wordSix.setAllophones(getAllophones(language, "ɑ", "n", "ɪ", "m"));
        } else if (language == Language.SWA) {
            wordSix.setText("sita");
            wordSix.setPhonetics("ˈsitɑ");
            wordSix.setUsageCount(3);
        }
        words.add(wordSix);

        Word wordSeven = new Word();
        wordSeven.setLanguage(language);
        wordSeven.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordSeven.setText("সাত");
            wordSeven.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordSeven.setText("seven");
            wordSeven.setPhonetics("ˈsɛvən");
        } else if (language == Language.FIL) {
            wordSeven.setText("pito");
            wordSeven.setPhonetics("pɪtɔ");
            wordSeven.setAllophones(getAllophones(language, "p", "ɪ", "t", "ɔ"));
        } else if (language == Language.SWA) {
            wordSeven.setText("saba");
            wordSeven.setPhonetics("ˈsɑbɑ");
            wordSeven.setUsageCount(4);
        }
        words.add(wordSeven);

        Word wordEight = new Word();
        wordEight.setLanguage(language);
        wordEight.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordEight.setText("আট");
            wordEight.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordEight.setText("eight");
            wordEight.setPhonetics("ɛɪt");
        } else if (language == Language.FIL) {
            wordEight.setText("walo");
            wordEight.setPhonetics("wɑlɔ");
            wordEight.setAllophones(getAllophones(language, "w", "ɑ", "l", "ɔ"));
        } else if (language == Language.SWA) {
            wordEight.setText("nane");
            wordEight.setPhonetics("ˈnɑnɛ");
            wordEight.setUsageCount(1);
        }
        words.add(wordEight);

        Word wordNine = new Word();
        wordNine.setLanguage(language);
        wordNine.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordNine.setText("নয়");
            wordNine.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordNine.setText("nine");
            wordNine.setPhonetics("nɑɪn");
        } else if (language == Language.FIL) {
            wordNine.setText("siyam");
            wordNine.setPhonetics("ʃɑm");
            wordNine.setAllophones(getAllophones(language, "ʃ", "ɑ", "m"));
        } else if (language == Language.SWA) {
            wordNine.setText("tisa");
            wordNine.setPhonetics("ˈtisɑ");
        }
        words.add(wordNine);
        
        Word wordTen = new Word();
        wordTen.setLanguage(language);
        wordTen.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordTen.setText("দশ");
            wordTen.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordTen.setText("ten");
            wordTen.setPhonetics("tɛn");
        } else if (language == Language.FIL) {
            wordTen.setText("sampu");
            wordTen.setPhonetics("sɑmpu");
            wordTen.setAllophones(getAllophones(language, "s", "ɑ", "m", "p", "u"));
        } else if (language == Language.SWA) {
            wordTen.setText("kumi");
            wordTen.setPhonetics("kumi");
            wordTen.setUsageCount(2);
        }
        words.add(wordTen);
        
        if (language == Language.SWA) {
            // E.g. "kumi na mbili", which literally means "ten and two" (12)
            Word wordNa = new Word();
            wordNa.setLanguage(language);
            wordNa.setTimeLastUpdate(Calendar.getInstance());
            wordNa.setText("na");
            wordNa.setPhonetics("nɑ");
            wordNa.setUsageCount(179);
            words.add(wordNa);
        }
        
        if ((language == Language.BEN) || (language == Language.ENG) || (language == Language.FIL)) {
            Word wordEleven = new Word();
            wordEleven.setLanguage(language);
            wordEleven.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordEleven.setText("এগার");
                wordEleven.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordEleven.setText("eleven");
                wordEleven.setPhonetics("ɪˈlɛvən");
            } else if (language == Language.FIL) {
                wordEleven.setText("labing-isa"); // TODO: handle "labing" and "isa" as separate words?
                wordEleven.setPhonetics("lɑbɪŋ.ɪsɑ");
                wordEleven.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "ŋ", ".", "ɪ", "s", "ɑ"));
            }
            words.add(wordEleven);

            Word wordTwelve = new Word();
            wordTwelve.setLanguage(language);
            wordTwelve.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordTwelve.setText("বার");
                wordTwelve.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordTwelve.setText("twelve");
                wordTwelve.setPhonetics("twɛlv");
            } else if (language == Language.FIL) {
                wordTwelve.setText("labindalawa");
                wordTwelve.setPhonetics("lɑbɪn.dɑlɑwɑ");
                wordTwelve.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "n", ".", "d", "ɑ", "l", "ɑ", "w", "a"));
            }
            words.add(wordTwelve);

            Word wordThirteen = new Word();
            wordThirteen.setLanguage(language);
            wordThirteen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordThirteen.setText("তের");
                wordThirteen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordThirteen.setText("thirteen");
                wordThirteen.setPhonetics("θɛrtin");
            } else if (language == Language.FIL) {
                wordThirteen.setText("labintatlo");
                wordThirteen.setPhonetics("lɑbɪn.tɑtlɔ");
                wordThirteen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "n", ".", "t", "ɑ", "t", "l", "ɔ"));
            }
            words.add(wordThirteen);

            Word wordFourteen = new Word();
            wordFourteen.setLanguage(language);
            wordFourteen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordFourteen.setText("চতুর্দশ");
                wordFourteen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordFourteen.setText("fourteen");
                wordFourteen.setPhonetics("ˈfɔrˈtin");
            } else if (language == Language.FIL) {
                wordFourteen.setText("labing-apat");
                wordFourteen.setPhonetics("lɑbɪŋ.ɑpɑt");
                wordFourteen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "ŋ", ".", "ɑ", "p", "ɑ", "t"));
            }
            words.add(wordFourteen);

            Word wordFifteen = new Word();
            wordFifteen.setLanguage(language);
            wordFifteen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordFifteen.setText("পনের");
                wordFifteen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordFifteen.setText("fifteen");
                wordFifteen.setPhonetics("fɪfˈtin");
            } else if (language == Language.FIL) {
                wordFifteen.setText("labinlima");
                wordFifteen.setPhonetics("lɑbɪn.lɪmɑ");
                wordFifteen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "n", ".", "l", "ɪ", "m", "ɑ"));
            }
            words.add(wordFifteen);

            Word wordSixteen = new Word();
            wordSixteen.setLanguage(language);
            wordSixteen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordSixteen.setText("ষোল");
                wordSixteen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordSixteen.setText("sixteen");
                wordSixteen.setPhonetics("sɪkˈstin");
            } else if (language == Language.FIL) {
                wordSixteen.setText("labing-anim");
                wordSixteen.setPhonetics("lɑbɪŋ.ɑnɪm");
                wordSixteen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "ŋ", ".", "ɑ", "n", "ɪ", "m"));
            }
            words.add(wordSixteen);

            Word wordSeventeen = new Word();
            wordSeventeen.setLanguage(language);
            wordSeventeen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordSeventeen.setText("সতের");
                wordSeventeen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordSeventeen.setText("seventeen");
                wordSeventeen.setPhonetics("ˈsɛvənˈtin");
            } else if (language == Language.FIL) {
                wordSeventeen.setText("labimpito");
                wordSeventeen.setPhonetics("lɑbɪm.pɪtɔ");
                wordSeventeen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "m", ".", "p", "ɪ", "t", "ɔ"));
            }
            words.add(wordSeventeen);

            Word wordEighteen = new Word();
            wordEighteen.setLanguage(language);
            wordEighteen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordEighteen.setText("আঠার");
                wordEighteen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordEighteen.setText("eighteen");
                wordEighteen.setPhonetics("ɛɪtin");
            } else if (language == Language.FIL) {
                wordEighteen.setText("labingwalo");
                wordEighteen.setPhonetics("lɑbɪŋ.wɑlɔ");
                wordEighteen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "ŋ", ".", "w", "ɑ", "l", "ɔ"));
            }
            words.add(wordEighteen);

            Word wordNineteen = new Word();
            wordNineteen.setLanguage(language);
            wordNineteen.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordNineteen.setText("উনিশ");
                wordNineteen.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordNineteen.setText("nineteen");
                wordNineteen.setPhonetics("ˈnaɪnˈtin");
            } else if (language == Language.FIL) {
                wordNineteen.setText("labinsiyam");
                wordNineteen.setPhonetics("lɑbɪn.ʃɑm");
                wordNineteen.setAllophones(getAllophones(language, "l", "ɑ", "b", "ɪ", "n", ".", "ʃ", "ɑ", "m"));
            }
            words.add(wordNineteen);
        }
        
        Word wordTwenty = new Word();
        wordTwenty.setLanguage(language);
        wordTwenty.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordTwenty.setText("বিশ");
            wordTwenty.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordTwenty.setText("twenty");
            wordTwenty.setPhonetics("ˈtwɛnti");
        } else if (language == Language.FIL) {
            wordTwenty.setText("dalawampu");
            wordTwenty.setPhonetics("dɑlɑwɑmpu");
            wordTwenty.setAllophones(getAllophones(language, "d", "ɑ", "l", "ɑ", "w", "ɑ", "m", "p", "u"));
        } else if (language == Language.SWA) {
            wordTwenty.setText("ishirini");
            wordTwenty.setPhonetics("iʃiɾini");
        }
        words.add(wordTwenty);
        
        if (language == Language.FIL) {
            // "dalawampu't" = "dalawampu at", e.g. in "dalawampu't isa" (21)
            Word wordTwentyAnd = new Word();
            wordTwentyAnd.setLanguage(language);
            wordTwentyAnd.setTimeLastUpdate(Calendar.getInstance());
            wordTwentyAnd.setText("dalawampu't"); // TODO: handle apostrophe in "dalawampu't" ("dalawampu at")
            wordTwentyAnd.setPhonetics("dɑlɑwɑmput");
            wordTwentyAnd.setAllophones(getAllophones(language, "d", "ɑ", "l", "ɑ", "w", "ɑ", "m", "p", "u", "t"));
            words.add(wordTwentyAnd);
        }
        
        if ((language == Language.BEN) || (language == Language.ENG)) {
            Word wordTwentyOne = new Word();
            wordTwentyOne.setLanguage(language);
            wordTwentyOne.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordTwentyOne.setText("একুশ");
                wordTwentyOne.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordTwentyOne.setText("twenty-one"); // TODO: handle words separated by hyphen
                wordTwentyOne.setPhonetics("ˈtwɛntiˈwʌn"); // TODO: handle pauses (whitespaces)
            }
            words.add(wordTwentyOne);

            Word wordTwentyTwo = new Word();
            wordTwentyTwo.setLanguage(language);
            wordTwentyTwo.setTimeLastUpdate(Calendar.getInstance());
            if (language == Language.BEN) {
                wordTwentyTwo.setText("বাইশ");
                wordTwentyTwo.setPhonetics("..."); // TODO
            } else if (language == Language.ENG) {
                wordTwentyTwo.setText("twenty-two"); // TODO: handle words separated by hyphen
                wordTwentyTwo.setPhonetics("ˈtwɛntiˈtu"); // TODO: handle pauses (whitespaces)
            }
            words.add(wordTwentyTwo);
        }
        
        Word wordThirty = new Word();
        wordThirty.setLanguage(language);
        wordThirty.setTimeLastUpdate(Calendar.getInstance());
        if (language == Language.BEN) {
            wordThirty.setText("ত্রিশ");
            wordThirty.setPhonetics("..."); // TODO
        } else if (language == Language.ENG) {
            wordThirty.setText("thirty");
            wordThirty.setPhonetics("ˈθɝtɪ");
        } else if (language == Language.FIL) {
            wordThirty.setText("tatlumpu");
            wordThirty.setPhonetics("tɑtlumpu");
            wordThirty.setAllophones(getAllophones(language, "t", "ɑ", "t", "l", "u", "m", "p", "u"));
        } else if (language == Language.SWA) {
            wordThirty.setText("thelathini");
            wordThirty.setPhonetics("θɛlɑθini");
        }
        words.add(wordThirty);
        
        // TODO: add number words 0-1000
        
        
        // Words (other than number words)
        
        if (language == Language.BEN) {
            Word wordMother = new Word();
            wordMother.setLanguage(language);
            wordMother.setTimeLastUpdate(Calendar.getInstance());
            wordMother.setText("মা");
            wordMother.setPhonetics("ma");
            words.add(wordMother);
            
            Word wordSheIs = new Word();
            wordSheIs.setLanguage(language);
            wordSheIs.setTimeLastUpdate(Calendar.getInstance());
            wordSheIs.setText("সে");
            wordSheIs.setPhonetics("ʃe");
            words.add(wordSheIs);
            
            Word wordInvitation = new Word();
            wordInvitation.setLanguage(language);
            wordInvitation.setTimeLastUpdate(Calendar.getInstance());
            wordInvitation.setText("নিতা");
            wordInvitation.setPhonetics("nita");
            words.add(wordInvitation);
            
            Word wordIAm = new Word();
            wordIAm.setLanguage(language);
            wordIAm.setTimeLastUpdate(Calendar.getInstance());
            wordIAm.setText("আমি");
            wordIAm.setPhonetics("ami");
            words.add(wordIAm);
            
            // TODO
        }
        
        
        return words;
    }
    
    /**
     * As an example, the word 'one' is represented by three Allophones; /w/, /ʌ/ and /n/.
     */
    private List<Allophone> getAllophones(Language language, String... ipaValues) {
        List<Allophone> allophones = new ArrayList<>();
        
        for (String ipaValue : ipaValues) {
            Allophone allophone = allophoneDao.readByValueIpa(language, ipaValue);
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
