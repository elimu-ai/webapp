package org.literacyapp.web.content.number;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Number;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
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
