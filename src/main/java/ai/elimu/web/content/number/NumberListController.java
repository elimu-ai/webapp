package ai.elimu.web.content.number;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.content.word.WordListController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/list")
public class NumberListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        // To ease development/testing, auto-generate Numbers
        List<Number> numbersGenerated = generateNumbers(language);
        for (Number number : numbersGenerated) {
            Number existingNumber = numberDao.readByValue(language, number.getValue());
            if (existingNumber == null) {
                numberDao.create(number);
            }
        }
        
        List<Number> numbers = numberDao.readAllOrdered(language);
        model.addAttribute("numbers", numbers);

        return "content/number/list";
    }
    
    /**
     * Note: number words should be generated _before_ adding them to their 
     * corresponding numbers (see {@link WordListController#generateWords(ai.elimu.model.enums.Language)}).
     */
    private List<Number> generateNumbers(Language language) {
        List<Number> numbers = new ArrayList<>();
        
        Number number0 = new Number();
        number0.setLanguage(language);
        number0.setTimeLastUpdate(Calendar.getInstance());
        number0.setValue(0);
        if (language == Language.BEN) {
            number0.setWords(getNumberWords(language, "শূন্য"));
        } else if (language == Language.ENG) {
            number0.setWords(getNumberWords(language, "zero"));
        } else if (language == Language.FIL) {
            number0.setWords(getNumberWords(language, "sero"));
        } else if (language == Language.SWA) {
            number0.setWords(getNumberWords(language, "sifuri"));
        }
        numbers.add(number0);
        
        Number number1 = new Number();
        number1.setLanguage(language);
        number1.setTimeLastUpdate(Calendar.getInstance());
        number1.setValue(1);
        if (language == Language.BEN) {
            number1.setWords(getNumberWords(language, "এক"));
        } else if (language == Language.ENG) {
            number1.setWords(getNumberWords(language, "one"));
        } else if (language == Language.FIL) {
            number1.setWords(getNumberWords(language, "isa"));
        } else if (language == Language.SWA) {
            number1.setWords(getNumberWords(language, "moja"));
        }
        numbers.add(number1);
        
        Number number2 = new Number();
        number2.setLanguage(language);
        number2.setTimeLastUpdate(Calendar.getInstance());
        number2.setValue(2);
        if (language == Language.BEN) {
            number2.setWords(getNumberWords(language, "দুই"));
        } else if (language == Language.ENG) {
            number2.setWords(getNumberWords(language, "two"));
        } else if (language == Language.FIL) {
            number2.setWords(getNumberWords(language, "dalawa"));
        } else if (language == Language.SWA) {
            number2.setWords(getNumberWords(language, "mbili"));
        }
        numbers.add(number2);
        
        Number number3 = new Number();
        number3.setLanguage(language);
        number3.setTimeLastUpdate(Calendar.getInstance());
        number3.setValue(3);
        if (language == Language.BEN) {
            number3.setWords(getNumberWords(language, "তিন"));
        } else if (language == Language.ENG) {
            number3.setWords(getNumberWords(language, "three"));
        } else if (language == Language.FIL) {
            number3.setWords(getNumberWords(language, "tatlo"));
        } else if (language == Language.SWA) {
            number3.setWords(getNumberWords(language, "tatu"));
        }
        numbers.add(number3);
        
        Number number4 = new Number();
        number4.setLanguage(language);
        number4.setTimeLastUpdate(Calendar.getInstance());
        number4.setValue(4);
        if (language == Language.BEN) {
            number4.setWords(getNumberWords(language, "চার"));
        } else if (language == Language.ENG) {
            number4.setWords(getNumberWords(language, "four"));
        } else if (language == Language.FIL) {
            number4.setWords(getNumberWords(language, "apat"));
        } else if (language == Language.SWA) {
            number4.setWords(getNumberWords(language, "nne"));
        }
        numbers.add(number4);
        
        Number number5 = new Number();
        number5.setLanguage(language);
        number5.setTimeLastUpdate(Calendar.getInstance());
        number5.setValue(5);
        if (language == Language.BEN) {
            number5.setWords(getNumberWords(language, "পাঁচ"));
        } else if (language == Language.ENG) {
            number5.setWords(getNumberWords(language, "five"));
        } else if (language == Language.FIL) {
            number5.setWords(getNumberWords(language, "lima"));
        } else if (language == Language.SWA) {
            number5.setWords(getNumberWords(language, "tano"));
        }
        numbers.add(number5);
        
        Number number6 = new Number();
        number6.setLanguage(language);
        number6.setTimeLastUpdate(Calendar.getInstance());
        number6.setValue(6);
        if (language == Language.BEN) {
            number6.setWords(getNumberWords(language, "ছয়"));
        } else if (language == Language.ENG) {
            number6.setWords(getNumberWords(language, "six"));
        } else if (language == Language.FIL) {
            number6.setWords(getNumberWords(language, "anim"));
        } else if (language == Language.SWA) {
            number6.setWords(getNumberWords(language, "sita"));
        }
        numbers.add(number6);
        
        Number number7 = new Number();
        number7.setLanguage(language);
        number7.setTimeLastUpdate(Calendar.getInstance());
        number7.setValue(7);
        if (language == Language.BEN) {
            number7.setWords(getNumberWords(language, "সাত"));
        } else if (language == Language.ENG) {
            number7.setWords(getNumberWords(language, "seven"));
        } else if (language == Language.FIL) {
            number7.setWords(getNumberWords(language, "pito"));
        } else if (language == Language.SWA) {
            number7.setWords(getNumberWords(language, "saba"));
        }
        numbers.add(number7);
        
        Number number8 = new Number();
        number8.setLanguage(language);
        number8.setTimeLastUpdate(Calendar.getInstance());
        number8.setValue(8);
        if (language == Language.BEN) {
            number8.setWords(getNumberWords(language, "আট"));
        } else if (language == Language.ENG) {
            number8.setWords(getNumberWords(language, "eight"));
        } else if (language == Language.FIL) {
            number8.setWords(getNumberWords(language, "walo"));
        } else if (language == Language.SWA) {
            number8.setWords(getNumberWords(language, "nane"));
        }
        numbers.add(number8);
        
        Number number9 = new Number();
        number9.setLanguage(language);
        number9.setTimeLastUpdate(Calendar.getInstance());
        number9.setValue(9);
        if (language == Language.BEN) {
            number9.setWords(getNumberWords(language, "নয়"));
        } else if (language == Language.ENG) {
            number9.setWords(getNumberWords(language, "nine"));
        } else if (language == Language.FIL) {
            number9.setWords(getNumberWords(language, "siyam"));
        } else if (language == Language.SWA) {
            number9.setWords(getNumberWords(language, "tisa"));
        }
        numbers.add(number9);
        
        Number number10 = new Number();
        number10.setLanguage(language);
        number10.setTimeLastUpdate(Calendar.getInstance());
        number10.setValue(10);
        if (language == Language.BEN) {
            number10.setWords(getNumberWords(language, "দশ"));
        } else if (language == Language.ENG) {
            number10.setWords(getNumberWords(language, "ten"));
        } else if (language == Language.FIL) {
            number10.setWords(getNumberWords(language, "sampu"));
        } else if (language == Language.SWA) {
            number10.setWords(getNumberWords(language, "kumi"));
        }
        numbers.add(number10);
        
        Number number11 = new Number();
        number11.setLanguage(language);
        number11.setTimeLastUpdate(Calendar.getInstance());
        number11.setValue(11);
        if (language == Language.BEN) {
            number11.setWords(getNumberWords(language, "এগার"));
        } else if (language == Language.ENG) {
            number11.setWords(getNumberWords(language, "eleven"));
        } else if (language == Language.FIL) {
            number11.setWords(getNumberWords(language, "labing-isa")); // TODO: handle "labing" and "isa" as separate words?
        } else if (language == Language.SWA) {
            number11.setWords(getNumberWords(language, "kumi", "na", "moja"));
        }
        numbers.add(number11);
        
        Number number12 = new Number();
        number12.setLanguage(language);
        number12.setTimeLastUpdate(Calendar.getInstance());
        number12.setValue(12);
        if (language == Language.BEN) {
            number12.setWords(getNumberWords(language, "বার"));
        } else if (language == Language.ENG) {
            number12.setWords(getNumberWords(language, "twelve"));
        } else if (language == Language.FIL) {
            number12.setWords(getNumberWords(language, "labindalawa"));
        } else if (language == Language.SWA) {
            number12.setWords(getNumberWords(language, "kumi", "na", "mbili"));
        }
        numbers.add(number12);
        
        Number number13 = new Number();
        number13.setLanguage(language);
        number13.setTimeLastUpdate(Calendar.getInstance());
        number13.setValue(13);
        if (language == Language.BEN) {
            number13.setWords(getNumberWords(language, "তের"));
        } else if (language == Language.ENG) {
            number13.setWords(getNumberWords(language, "thirteen"));
        } else if (language == Language.FIL) {
            number13.setWords(getNumberWords(language, "labintatlo"));
        } else if (language == Language.SWA) {
            number13.setWords(getNumberWords(language, "kumi", "na", "tatu"));
        }
        numbers.add(number13);
        
        Number number14 = new Number();
        number14.setLanguage(language);
        number14.setTimeLastUpdate(Calendar.getInstance());
        number14.setValue(14);
        if (language == Language.BEN) {
            number14.setWords(getNumberWords(language, "চতুর্দশ"));
        } else if (language == Language.ENG) {
            number14.setWords(getNumberWords(language, "fourteen"));
        } else if (language == Language.FIL) {
            number14.setWords(getNumberWords(language, "labing-apat"));
        } else if (language == Language.SWA) {
            number14.setWords(getNumberWords(language, "kumi", "na", "nne"));
        }
        numbers.add(number14);
        
        Number number15 = new Number();
        number15.setLanguage(language);
        number15.setTimeLastUpdate(Calendar.getInstance());
        number15.setValue(15);
        if (language == Language.BEN) {
            number15.setWords(getNumberWords(language, "পনের"));
        } else if (language == Language.ENG) {
            number15.setWords(getNumberWords(language, "fifteen"));
        } else if (language == Language.FIL) {
            number15.setWords(getNumberWords(language, "labinlima"));
        } else if (language == Language.SWA) {
            number15.setWords(getNumberWords(language, "kumi", "na", "tano"));
        }
        numbers.add(number15);
        
        Number number16 = new Number();
        number16.setLanguage(language);
        number16.setTimeLastUpdate(Calendar.getInstance());
        number16.setValue(16);
        if (language == Language.BEN) {
            number16.setWords(getNumberWords(language, "ষোল"));
        } else if (language == Language.ENG) {
            number16.setWords(getNumberWords(language, "sixteen"));
        } else if (language == Language.FIL) {
            number16.setWords(getNumberWords(language, "labing-anim"));
        } else if (language == Language.SWA) {
            number16.setWords(getNumberWords(language, "kumi", "na", "sita"));
        }
        numbers.add(number16);
        
        Number number17 = new Number();
        number17.setLanguage(language);
        number17.setTimeLastUpdate(Calendar.getInstance());
        number17.setValue(17);
        if (language == Language.BEN) {
            number17.setWords(getNumberWords(language, "সতের"));
        } else if (language == Language.ENG) {
            number17.setWords(getNumberWords(language, "seventeen"));
        } else if (language == Language.FIL) {
            number17.setWords(getNumberWords(language, "labimpito"));
        } else if (language == Language.SWA) {
            number17.setWords(getNumberWords(language, "kumi", "na", "saba"));
        }
        numbers.add(number17);
        
        Number number18 = new Number();
        number18.setLanguage(language);
        number18.setTimeLastUpdate(Calendar.getInstance());
        number18.setValue(18);
        if (language == Language.BEN) {
            number18.setWords(getNumberWords(language, "আঠার"));
        } else if (language == Language.ENG) {
            number18.setWords(getNumberWords(language, "eighteen"));
        } else if (language == Language.FIL) {
            number18.setWords(getNumberWords(language, "labingwalo"));
        } else if (language == Language.SWA) {
            number18.setWords(getNumberWords(language, "kumi", "na", "nane"));
        }
        numbers.add(number18);
        
        Number number19 = new Number();
        number19.setLanguage(language);
        number19.setTimeLastUpdate(Calendar.getInstance());
        number19.setValue(19);
        if (language == Language.BEN) {
            number19.setWords(getNumberWords(language, "উনিশ"));
        } else if (language == Language.ENG) {
            number19.setWords(getNumberWords(language, "nineteen"));
        } else if (language == Language.FIL) {
            number19.setWords(getNumberWords(language, "labinsiyam"));
        } else if (language == Language.SWA) {
            number19.setWords(getNumberWords(language, "kumi", "na", "tisa"));
        }
        numbers.add(number19);
        
        Number number20 = new Number();
        number20.setLanguage(language);
        number20.setTimeLastUpdate(Calendar.getInstance());
        number20.setValue(20);
        if (language == Language.BEN) {
            number20.setWords(getNumberWords(language, "বিশ"));
        } else if (language == Language.ENG) {
            number20.setWords(getNumberWords(language, "twenty"));
        } else if (language == Language.FIL) {
            number20.setWords(getNumberWords(language, "dalawampu"));
        } else if (language == Language.SWA) {
            number20.setWords(getNumberWords(language, "ishirini"));
        }
        numbers.add(number20);
        
        Number number21 = new Number();
        number21.setLanguage(language);
        number21.setTimeLastUpdate(Calendar.getInstance());
        number21.setValue(21);
        if (language == Language.BEN) {
            number21.setWords(getNumberWords(language, "একুশ"));
        } else if (language == Language.ENG) {
            number21.setWords(getNumberWords(language, "twenty-one")); // TODO: handle words separated by hyphen
        } else if (language == Language.FIL) {
            number21.setWords(getNumberWords(language, "dalawampu't", "isa")); // TODO: handle apostrophe in "dalawampu't" ("dalawampu at")
        } else if (language == Language.SWA) {
            number21.setWords(getNumberWords(language, "ishirini", "na", "moja"));
        }
        numbers.add(number21);
        
        Number number22 = new Number();
        number22.setLanguage(language);
        number22.setTimeLastUpdate(Calendar.getInstance());
        number22.setValue(22);
        if (language == Language.BEN) {
            number22.setWords(getNumberWords(language, "বাইশ"));
        } else if (language == Language.ENG) {
            number22.setWords(getNumberWords(language, "twenty-two")); // TODO: handle words separated by hyphen
        } else if (language == Language.FIL) {
            number22.setWords(getNumberWords(language, "dalawampu't", "dalawa")); // TODO: handle apostrophe in "dalawampu't" ("dalawampu at")
        } else if (language == Language.SWA) {
            number22.setWords(getNumberWords(language, "ishirini", "na", "mbili"));
        }
        numbers.add(number22);
        
        Number number30 = new Number();
        number30.setLanguage(language);
        number30.setTimeLastUpdate(Calendar.getInstance());
        number30.setValue(30);
        if (language == Language.BEN) {
            number30.setWords(getNumberWords(language, "ত্রিশ"));
        } else if (language == Language.ENG) {
            number30.setWords(getNumberWords(language, "thirty"));
        } else if (language == Language.FIL) {
            number30.setWords(getNumberWords(language, "tatlumpu"));
        } else if (language == Language.SWA) {
            number30.setWords(getNumberWords(language, "thelathini"));
        }
        numbers.add(number30);
        
        // TODO: Add up to 1,000
        
        return numbers;
    }
    
    private List<Word> getNumberWords(Language language, String... words) {
        List<Word> numberWords = new ArrayList<>();
        
        for (String word : words) {
            Word numberWord = wordDao.readByText(language, word);
            numberWords.add(numberWord);
        }
        
        return numberWords;
    }
}
