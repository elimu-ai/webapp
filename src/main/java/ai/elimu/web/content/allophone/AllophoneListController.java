package ai.elimu.web.content.allophone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Locale;
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
        {"mb","mb", "9"},
        {"mv","mv", "0"},
        {"nd","nd", "4"},
        {"nz","nz", "4"},
        {"ɲɟ","Jj\\", "0"},
        {"tʃ","tS", "0"},
        {"ŋɡ","Nɡ", "8"},
        {"ɑ","A", "376"},
        {"ɛ","E", "74"},
        {"i","i", "207"},
        {"ɔ","O", "0"},
        {"u","u", "142"},
        {"m","m", "124"},
        //{"ɓ","b_<", "68"},
        {"p","p", "23"},
        {"v","v", "0"},
        {"f","f", "10"},
        {"ð","D", "0"},
        {"θ","T", "0"},
        {"n","n", "74"},
        {"ɗ","d_<", "8"},
        {"t","t", "0"},
        {"z","z", "0"},
        {"s","s", "76"},
        {"ɾ","4", "0"},
        {"l","l", "0"},
        {"ɲ","J", "8"},
        {"ʄ","j_<", "0"},
        {"ʃ","S", "4"},
        {"j","j", "0"},
        {"ŋ","N", "0"},
        {"ɠ","g_<", "0"},
        {"k","k", "70"},
        {"ɣ","G", "0"},
        {"x","x", "0"},
        {"w","w", "0"},
        {"h","h", "19"},
        {"ˈ","\"", "0"} // TODO: set isDiacritic = true
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
    
    // TODO: add SoundType to each Allophone
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
