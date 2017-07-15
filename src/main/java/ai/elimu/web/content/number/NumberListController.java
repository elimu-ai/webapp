package ai.elimu.web.content.number;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;
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
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Numbers
        List<Number> numbersGenerated = generateNumbers(contributor.getLocale());
        for (Number number : numbersGenerated) {
            Number existingNumber = numberDao.readByValue(number.getLocale(), number.getValue());
            if (existingNumber == null) {
                numberDao.create(number);
            }
        }
        
        List<Number> numbers = numberDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("numbers", numbers);

        return "content/number/list";
    }
    
    /**
     * Note: number words should be generated _before_ adding them to their 
     * corresponding numbers.
     */
    private List<Number> generateNumbers(Locale locale) {
        List<Number> numbers = new ArrayList<>();
        
        Number number0 = new Number();
        number0.setLocale(locale);
        number0.setTimeLastUpdate(Calendar.getInstance());
        number0.setValue(0);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number0.setWords(getNumberWords(locale, "zero"));
        } else if (locale == Locale.ES) {
            number0.setWords(getNumberWords(locale, "cero"));
        } else if (locale == Locale.SW) {
            number0.setWords(getNumberWords(locale, "sifuri"));
        }
        numbers.add(number0);
        
        Number number1 = new Number();
        number1.setLocale(locale);
        number1.setTimeLastUpdate(Calendar.getInstance());
        number1.setValue(1);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number1.setWords(getNumberWords(locale, "one"));
        } else if (locale == Locale.ES) {
            number1.setWords(getNumberWords(locale, "uno"));
        } else if (locale == Locale.SW) {
            number1.setWords(getNumberWords(locale, "moja"));
        }
        numbers.add(number1);
        
        Number number2 = new Number();
        number2.setLocale(locale);
        number2.setTimeLastUpdate(Calendar.getInstance());
        number2.setValue(2);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number2.setWords(getNumberWords(locale, "two"));
        } else if (locale == Locale.ES) {
            number2.setWords(getNumberWords(locale, "dos"));
        } else if (locale == Locale.SW) {
            number2.setWords(getNumberWords(locale, "mbili"));
        }
        numbers.add(number2);
        
        Number number3 = new Number();
        number3.setLocale(locale);
        number3.setTimeLastUpdate(Calendar.getInstance());
        number3.setValue(3);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number3.setWords(getNumberWords(locale, "three"));
        } else if (locale == Locale.ES) {
            number3.setWords(getNumberWords(locale, "tres"));
        } else if (locale == Locale.SW) {
            number3.setWords(getNumberWords(locale, "tatu"));
        }
        numbers.add(number3);
        
        Number number4 = new Number();
        number4.setLocale(locale);
        number4.setTimeLastUpdate(Calendar.getInstance());
        number4.setValue(4);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number4.setWords(getNumberWords(locale, "four"));
        } else if (locale == Locale.ES) {
            number4.setWords(getNumberWords(locale, "cuatro"));
        } else if (locale == Locale.SW) {
            number4.setWords(getNumberWords(locale, "nne"));
        }
        numbers.add(number4);
        
        Number number5 = new Number();
        number5.setLocale(locale);
        number5.setTimeLastUpdate(Calendar.getInstance());
        number5.setValue(5);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number5.setWords(getNumberWords(locale, "five"));
        } else if (locale == Locale.ES) {
            number5.setWords(getNumberWords(locale, "cinco"));
        } else if (locale == Locale.SW) {
            number5.setWords(getNumberWords(locale, "tano"));
        }
        numbers.add(number5);
        
        Number number6 = new Number();
        number6.setLocale(locale);
        number6.setTimeLastUpdate(Calendar.getInstance());
        number6.setValue(6);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number6.setWords(getNumberWords(locale, "six"));
        } else if (locale == Locale.ES) {
            number6.setWords(getNumberWords(locale, "seis"));
        } else if (locale == Locale.SW) {
            number6.setWords(getNumberWords(locale, "sita"));
        }
        numbers.add(number6);
        
        Number number7 = new Number();
        number7.setLocale(locale);
        number7.setTimeLastUpdate(Calendar.getInstance());
        number7.setValue(7);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number7.setWords(getNumberWords(locale, "seven"));
        } else if (locale == Locale.ES) {
            number7.setWords(getNumberWords(locale, "siete"));
        } else if (locale == Locale.SW) {
            number7.setWords(getNumberWords(locale, "saba"));
        }
        numbers.add(number7);
        
        Number number8 = new Number();
        number8.setLocale(locale);
        number8.setTimeLastUpdate(Calendar.getInstance());
        number8.setValue(8);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number8.setWords(getNumberWords(locale, "eight"));
        } else if (locale == Locale.ES) {
            number8.setWords(getNumberWords(locale, "ocho"));
        } else if (locale == Locale.SW) {
            number8.setWords(getNumberWords(locale, "nane"));
        }
        numbers.add(number8);
        
        Number number9 = new Number();
        number9.setLocale(locale);
        number9.setTimeLastUpdate(Calendar.getInstance());
        number9.setValue(9);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number9.setWords(getNumberWords(locale, "nine"));
        } else if (locale == Locale.ES) {
            number9.setWords(getNumberWords(locale, "nueve"));
        } else if (locale == Locale.SW) {
            number9.setWords(getNumberWords(locale, "tisa"));
        }
        numbers.add(number9);
        
        Number number10 = new Number();
        number10.setLocale(locale);
        number10.setTimeLastUpdate(Calendar.getInstance());
        number10.setValue(10);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number10.setWords(getNumberWords(locale, "ten"));
        } else if (locale == Locale.ES) {
            number10.setWords(getNumberWords(locale, "diez"));
        } else if (locale == Locale.SW) {
            number10.setWords(getNumberWords(locale, "kumi"));
        }
        numbers.add(number10);
        
        Number number11 = new Number();
        number11.setLocale(locale);
        number11.setTimeLastUpdate(Calendar.getInstance());
        number11.setValue(11);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number11.setWords(getNumberWords(locale, "eleven"));
        } else if (locale == Locale.ES) {
            number11.setWords(getNumberWords(locale, "once"));
        } else if (locale == Locale.SW) {
            number11.setWords(getNumberWords(locale, "kumi", "na", "moja"));
        }
        numbers.add(number11);
        
        Number number12 = new Number();
        number12.setLocale(locale);
        number12.setTimeLastUpdate(Calendar.getInstance());
        number12.setValue(12);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number12.setWords(getNumberWords(locale, "twelve"));
        } else if (locale == Locale.ES) {
            number12.setWords(getNumberWords(locale, "doce"));
        } else if (locale == Locale.SW) {
            number12.setWords(getNumberWords(locale, "kumi", "na", "mbili"));
        }
        numbers.add(number12);
        
        Number number13 = new Number();
        number13.setLocale(locale);
        number13.setTimeLastUpdate(Calendar.getInstance());
        number13.setValue(13);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number13.setWords(getNumberWords(locale, "thirteen"));
        } else if (locale == Locale.ES) {
            number13.setWords(getNumberWords(locale, "trece"));
        } else if (locale == Locale.SW) {
            number13.setWords(getNumberWords(locale, "kumi", "na", "tatu"));
        }
        numbers.add(number13);
        
        Number number14 = new Number();
        number14.setLocale(locale);
        number14.setTimeLastUpdate(Calendar.getInstance());
        number14.setValue(14);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number14.setWords(getNumberWords(locale, "fourteen"));
        } else if (locale == Locale.ES) {
            number14.setWords(getNumberWords(locale, "catorce"));
        } else if (locale == Locale.SW) {
            number14.setWords(getNumberWords(locale, "kumi", "na", "nne"));
        }
        numbers.add(number14);
        
        Number number15 = new Number();
        number15.setLocale(locale);
        number15.setTimeLastUpdate(Calendar.getInstance());
        number15.setValue(15);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number15.setWords(getNumberWords(locale, "fifteen"));
        } else if (locale == Locale.ES) {
            number15.setWords(getNumberWords(locale, "quince"));
        } else if (locale == Locale.SW) {
            number15.setWords(getNumberWords(locale, "kumi", "na", "tano"));
        }
        numbers.add(number15);
        
        Number number16 = new Number();
        number16.setLocale(locale);
        number16.setTimeLastUpdate(Calendar.getInstance());
        number16.setValue(16);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number16.setWords(getNumberWords(locale, "sixteen"));
        } else if (locale == Locale.ES) {
            number16.setWords(getNumberWords(locale, "dieciséis"));
        } else if (locale == Locale.SW) {
            number16.setWords(getNumberWords(locale, "kumi", "na", "sita"));
        }
        numbers.add(number16);
        
        Number number17 = new Number();
        number17.setLocale(locale);
        number17.setTimeLastUpdate(Calendar.getInstance());
        number17.setValue(17);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number17.setWords(getNumberWords(locale, "seventeen"));
        } else if (locale == Locale.ES) {
            number17.setWords(getNumberWords(locale, "diecisiete"));
        } else if (locale == Locale.SW) {
            number17.setWords(getNumberWords(locale, "kumi", "na", "saba"));
        }
        numbers.add(number17);
        
        Number number18 = new Number();
        number18.setLocale(locale);
        number18.setTimeLastUpdate(Calendar.getInstance());
        number18.setValue(18);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number18.setWords(getNumberWords(locale, "eighteen"));
        } else if (locale == Locale.ES) {
            number18.setWords(getNumberWords(locale, "dieciocho"));
        } else if (locale == Locale.SW) {
            number18.setWords(getNumberWords(locale, "kumi", "na", "nane"));
        }
        numbers.add(number18);
        
        Number number19 = new Number();
        number19.setLocale(locale);
        number19.setTimeLastUpdate(Calendar.getInstance());
        number19.setValue(19);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number19.setWords(getNumberWords(locale, "nineteen"));
        } else if (locale == Locale.ES) {
            number19.setWords(getNumberWords(locale, "diecinueve"));
        } else if (locale == Locale.SW) {
            number19.setWords(getNumberWords(locale, "kumi", "na", "tisa"));
        }
        numbers.add(number19);
        
        Number number20 = new Number();
        number20.setLocale(locale);
        number20.setTimeLastUpdate(Calendar.getInstance());
        number20.setValue(20);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number20.setWords(getNumberWords(locale, "twenty"));
        } else if (locale == Locale.ES) {
            number20.setWords(getNumberWords(locale, "veinte"));
        } else if (locale == Locale.SW) {
            number20.setWords(getNumberWords(locale, "ishirini"));
        }
        numbers.add(number20);
        
        Number number21 = new Number();
        number21.setLocale(locale);
        number21.setTimeLastUpdate(Calendar.getInstance());
        number21.setValue(21);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number21.setWords(getNumberWords(locale, "twenty", "one"));
        } else if (locale == Locale.ES) {
            number21.setWords(getNumberWords(locale, "veintiuno"));
        } else if (locale == Locale.SW) {
            number21.setWords(getNumberWords(locale, "ishirini", "na", "moja"));
        }
        numbers.add(number21);
        
        return numbers;
    }
    
    private List<Word> getNumberWords(Locale locale, String... words) {
        List<Word> numberWords = new ArrayList<>();
        
        for (String word : words) {
            Word numberWord = wordDao.readByText(locale, word);
            numberWords.add(numberWord);
        }
        
        return numberWords;
    }
}
