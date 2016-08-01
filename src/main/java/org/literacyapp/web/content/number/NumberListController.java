package org.literacyapp.web.content.number;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Number;
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
@RequestMapping("/content/number/list")
public class NumberListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private ContentCreationEventDao contentCreationEventDao;

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

                ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
                contentCreationEvent.setContributor(contributor);
                contentCreationEvent.setContent(number);
                contentCreationEvent.setCalendar(Calendar.getInstance());
                contentCreationEventDao.create(contentCreationEvent);
                
                if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                    String text = URLEncoder.encode(
                            contributor.getFirstName() + " just added a new Number:\n" + 
                            "• Language: \"" + number.getLocale().getLanguage() + "\"\n" + 
                            "• Value: \"" + number.getValue() + "\"" + (!StringUtils.isEmpty(number.getSymbol()) ? " (" + number.getSymbol() + ")" : "") + "\n" +
                            "• Word: \"" + number.getWord() + "\"\n" + 
                            "See ") + "http://literacyapp.org/content/number/list";
                    String iconUrl = contributor.getImageUrl();
                    SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
                }
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
        number0.setRevisionNumber(1);
        number0.setTimeLastUpdate(Calendar.getInstance());
        number0.setValue(0);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number0.setWord(wordDao.readByText(locale, "zero"));
        } else if (locale == Locale.ES) {
            number0.setWord(wordDao.readByText(locale, "cero"));
        } else if (locale == Locale.SW) {
            number0.setWord(wordDao.readByText(locale, "sufuri"));
        }
        numbers.add(number0);
        
        Number number1 = new Number();
        number1.setLocale(locale);
        number1.setRevisionNumber(1);
        number1.setTimeLastUpdate(Calendar.getInstance());
        number1.setValue(1);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number1.setWord(wordDao.readByText(locale, "one"));
        } else if (locale == Locale.ES) {
            number1.setWord(wordDao.readByText(locale, "uno"));
        } else if (locale == Locale.SW) {
            number1.setWord(wordDao.readByText(locale, "moja"));
        }
        numbers.add(number1);
        
        Number number2 = new Number();
        number2.setLocale(locale);
        number2.setRevisionNumber(1);
        number2.setTimeLastUpdate(Calendar.getInstance());
        number2.setValue(2);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number2.setWord(wordDao.readByText(locale, "two"));
        } else if (locale == Locale.ES) {
            number2.setWord(wordDao.readByText(locale, "dos"));
        } else if (locale == Locale.SW) {
            number2.setWord(wordDao.readByText(locale, "mbili"));
        }
        numbers.add(number2);
        
        Number number3 = new Number();
        number3.setLocale(locale);
        number3.setRevisionNumber(1);
        number3.setTimeLastUpdate(Calendar.getInstance());
        number3.setValue(3);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number3.setWord(wordDao.readByText(locale, "three"));
        } else if (locale == Locale.ES) {
            number3.setWord(wordDao.readByText(locale, "tres"));
        } else if (locale == Locale.SW) {
            number3.setWord(wordDao.readByText(locale, "tatu"));
        }
        numbers.add(number3);
        
        Number number4 = new Number();
        number4.setLocale(locale);
        number4.setRevisionNumber(1);
        number4.setTimeLastUpdate(Calendar.getInstance());
        number4.setValue(4);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number4.setWord(wordDao.readByText(locale, "four"));
        } else if (locale == Locale.ES) {
            number4.setWord(wordDao.readByText(locale, "cuatro"));
        } else if (locale == Locale.SW) {
            number4.setWord(wordDao.readByText(locale, "nne"));
        }
        numbers.add(number4);
        
        Number number5 = new Number();
        number5.setLocale(locale);
        number5.setRevisionNumber(1);
        number5.setTimeLastUpdate(Calendar.getInstance());
        number5.setValue(5);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number5.setWord(wordDao.readByText(locale, "five"));
        } else if (locale == Locale.ES) {
            number5.setWord(wordDao.readByText(locale, "cinco"));
        } else if (locale == Locale.SW) {
            number5.setWord(wordDao.readByText(locale, "tano"));
        }
        numbers.add(number5);
        
        Number number6 = new Number();
        number6.setLocale(locale);
        number6.setRevisionNumber(1);
        number6.setTimeLastUpdate(Calendar.getInstance());
        number6.setValue(6);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number6.setWord(wordDao.readByText(locale, "six"));
        } else if (locale == Locale.ES) {
            number6.setWord(wordDao.readByText(locale, "seis"));
        } else if (locale == Locale.SW) {
            number6.setWord(wordDao.readByText(locale, "sita"));
        }
        numbers.add(number6);
        
        Number number7 = new Number();
        number7.setLocale(locale);
        number7.setRevisionNumber(1);
        number7.setTimeLastUpdate(Calendar.getInstance());
        number7.setValue(7);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number7.setWord(wordDao.readByText(locale, "seven"));
        } else if (locale == Locale.ES) {
            number7.setWord(wordDao.readByText(locale, "siete"));
        } else if (locale == Locale.SW) {
            number7.setWord(wordDao.readByText(locale, "saba"));
        }
        numbers.add(number7);
        
        Number number8 = new Number();
        number8.setLocale(locale);
        number8.setRevisionNumber(1);
        number8.setTimeLastUpdate(Calendar.getInstance());
        number8.setValue(8);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number8.setWord(wordDao.readByText(locale, "eight"));
        } else if (locale == Locale.ES) {
            number8.setWord(wordDao.readByText(locale, "ocho"));
        } else if (locale == Locale.SW) {
            number8.setWord(wordDao.readByText(locale, "nane"));
        }
        numbers.add(number8);
        
        Number number9 = new Number();
        number9.setLocale(locale);
        number9.setRevisionNumber(1);
        number9.setTimeLastUpdate(Calendar.getInstance());
        number9.setValue(9);
        if (locale == Locale.AR) {
            // TODO: set symbol
            // TODO: set number word
        } else if (locale == Locale.EN) {
            number9.setWord(wordDao.readByText(locale, "nine"));
        } else if (locale == Locale.ES) {
            number9.setWord(wordDao.readByText(locale, "nueve"));
        } else if (locale == Locale.SW) {
            number9.setWord(wordDao.readByText(locale, "tisa"));
        }
        numbers.add(number9);
        
        return numbers;
    }
}
