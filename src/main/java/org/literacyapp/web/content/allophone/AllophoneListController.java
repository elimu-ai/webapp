package org.literacyapp.web.content.allophone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/allophone/list")
public class AllophoneListController {
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArrayAR = new String[][] {
        // TODO
    };
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArrayEN = new String[][] {
        {"aʊ","aU", "60"},
        {"ɔɪ","OI", "3"},
        {"əʊ","@U", "25"},
        {"ɛɪ","EI", "230"},
        {"ɑɪ","AI", "97"},
        {"tʃ","tS", "0"},
        {"dʒ","dZ", "3"}, // TODO: use /ʤ/ instead of /dʒ/?
        {"r̩","r_=", "0"}, // TODO: use "r=" instead of "r_="?
        {"ɑ","A", "121"},
        {"ɔ","O", "164"},
        {"u","u", "277"},
        {"i","i", "512"},
        {"æ","{", "291"},
        {"ʌ","V", "212"},
        {"ɛ","E", "128"},
        {"ɪ","I", "318"},
        {"ʊ","U", "67"},
        {"ə","@", "92"},
        {"p","p", "85"},
        {"t","t", "452"},
        {"k","k", "125"},
        {"b","b", "95"},
        {"d","d", "327"},
        {"g","g", "120"},
        {"f","f", "62"},
        {"v","v", "26"},
        {"θ","T", "5"},
        {"ð","D", "446"},
        {"s","s", "231"},
        {"z","z", "159"},
        {"ʃ","S", "55"},
        {"ʒ","Z", "0"},
        {"h","h", "156"},
        {"l","l", "164"},
        {"m","m", "200"},
        {"n","n", "349"},
        {"ŋ","N", "22"},
        {"r","r", "205"},
        {"w","w", "257"},
        {"j","j", "58"},
        {"ˈ","\"", "0"}, // TODO: set isDiacritic = true
        {"ˌ","%", "0"} // TODO: set isDiacritic = true
    };
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArrayES = new String[][] {
        // TODO
    };
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArraySW = new String[][] {
        {"mb","mb"},
        {"mv","mv"},
        {"nd","nd"},
        {"nz","nz"},
        {"ɲɟ","Jj\\"},
        {"tʃ","tS"},
        {"ŋɡ","Nɡ"},
        {"ɑ","A"},
        {"ɛ","E"},
        {"i","i"},
        {"ɔ","O"},
        {"u","u"},
        {"m","m"},
        {"ɓ","b_<"},
        {"p","p"},
        {"v","v"},
        {"f","f"},
        {"ð","D"},
        {"θ","T"},
        {"n","n"},
        {"ɗ","d_<"},
        {"t","t"},
        {"z","z"},
        {"s","s"},
        {"ɾ","4"},
        {"l","l"},
        {"ɲ","J"},
        {"ʄ","j_<"},
        {"ʃ","S"},
        {"j","j"},
        {"ŋ","N"},
        {"ɠ","g_<"},
        {"k","k"},
        {"ɣ","G"},
        {"x","x"},
        {"w","w"},
        {"h","h"},
        {"ˈ","\""} // TODO: set isDiacritic = true
    };
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Allophones
        List<Allophone> allophonesGenerated = generateAllophones(contributor.getLocale());
        for (Allophone allophone : allophonesGenerated) {
            logger.info("allophone.getValueIpa(): /" + allophone.getValueIpa() + "/, allophone.getValueSampa(): " + allophone.getValueSampa());
            Allophone existingAllophone = allophoneDao.readByValueIpa(contributor.getLocale(), allophone.getValueIpa());
            if (existingAllophone == null) {
                allophoneDao.create(allophone);
            }
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        model.addAttribute("allophones", allophones);
        
        int maxUsageCount = 0;
        for (Allophone allophone : allophones) {
            if (allophone.getUsageCount() > maxUsageCount) {
                maxUsageCount = allophone.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/allophone/list";
    }
    
    private List<Allophone> generateAllophones(Locale locale) {
        List<Allophone> allophones = new ArrayList<>();
        
        String[][] allophonesArray = null;
        if (locale == Locale.AR) {
            allophonesArray = allophonesArrayAR;
        } else if (locale == Locale.EN) {
            allophonesArray = allophonesArrayEN;
        } else if (locale == Locale.ES) {
            allophonesArray = allophonesArrayES;
        } else if (locale == Locale.SW) {
            allophonesArray = allophonesArraySW;
        }
        
        for (String[] allophoneRow : allophonesArray) {
            Allophone allophone = new Allophone();
            allophone.setLocale(locale);
            allophone.setTimeLastUpdate(Calendar.getInstance());
            allophone.setValueIpa(allophoneRow[0]);
            allophone.setValueSampa(allophoneRow[1]);
            allophone.setUsageCount(Integer.valueOf(allophoneRow[2]));
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
