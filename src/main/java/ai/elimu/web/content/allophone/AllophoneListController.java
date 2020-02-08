package ai.elimu.web.content.allophone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/allophone/list")
public class AllophoneListController {
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArrayBEN = new String[][] {
        {"dʒ", "dZ", "-1"}, // TODO: use /ʤ/ instead of /dʒ/?
        // TODO: /dʒʱ/
        // TODO: /t͡ʃ/
        // TODO: /t͡ʃʰ/
        // TODO: /d͡ʒ/
        // TODO: /d͡ʒʱ/
        {"kʰ", "k_h", "-1"},
        // TODO: {"ʈʰ", "t`_h", "-1"},
        {"ʃʰ", "S_h", "-1"},
        
        // Vowels - https://en.wikibooks.org/wiki/Bengali/Script/Vowels
        {"ɔ", "O", "-1"},
        {"a", "a", "-1"},
        {"i", "i", "-1"},
        {"u", "u", "-1"},
        {"e", "e", "-1"},
        // TODO: /e̯/
        // TODO: /ɛ/
        {"o", "o", "-1"},
        // TODO: /ʊ/
        {"æ", "{", "-1"},
        
        // Consonants - https://en.wikibooks.org/wiki/Bengali/Script/Consonants_1
        {"k", "k", "-1"},
        {"g", "g", "-1"},
        // TODO: /gʱ/
        {"ŋ", "N", "-1"},
        {"n", "n", "-1"},
        {"ʈ", "t`", "-1"},
        {"ɖ", "d`", "-1"},
        // TODO: /ɖʱ/
        {"t", "t", "-1"},
        {"t", "t_h", "-1"},
        {"d", "d", "-1"},
        // TODO: /dʱ/
        {"p", "p", "-1"},
        {"p", "p_h", "-1"},
        {"ɸ", "p\\", "-1"},
        {"b", "b", "-1"},
        // TODO: /bʱ/
        {"β", "B", "-1"},
        {"m", "m", "-1"},
        
        // Consonants - https://en.wikibooks.org/wiki/Bengali/Script/Consonants_2
        {"z", "z", "-1"},
        {"r", "r", "-1"},
        // TOOD: /ɽ/
        // TODO: /ɽʱ/
        {"l", "l", "-1"},
        {"w", "w", "-1"},
        {"ɕ", "s\\", "-1"},
        {"ʃ", "S", "-1"},
        {"s", "s", "-1"},
        {"h", "h", "-1"},
        {"ɦ", "h\\", "-1"}
    };
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArrayENG = new String[][] {
        {"aʊ", "aU", "60"},
        {"ɔɪ", "OI", "3"},
        {"əʊ", "@U", "25"},
        {"ɛɪ", "EI", "230"},
        {"ɑɪ", "AI", "97"},
        {"tʃ", "tS", "0"},
        {"dʒ", "dZ", "3"}, // TODO: use /ʤ/ instead of /dʒ/?
        {"r̩", "r_=", "0"}, // TODO: use "r=" instead of "r_="?
        {"ɑ", "A", "121"},
        {"ɔ", "O", "164"},
        {"u", "u", "277"},
        {"i", "i", "512"},
        {"æ", "{", "291"},
        {"ʌ", "V", "212"},
        {"ɛ", "E", "128"},
        {"ɝ", "@`", "-1"},
        {"ɪ", "I", "318"},
        {"ʊ", "U", "67"},
        {"ə", "@", "92"},
        {"p", "p", "85"},
        {"t", "t", "452"},
        {"k", "k", "125"},
        {"b", "b", "95"},
        {"d", "d", "327"},
        {"g", "g", "120"},
        {"f", "f", "62"},
        {"v", "v", "26"},
        {"θ", "T", "5"},
        {"ð", "D", "446"},
        {"s", "s", "231"},
        {"z", "z", "159"},
        {"ʃ", "S", "55"},
        {"ʒ", "Z", "0"},
        {"h", "h", "156"},
        {"l", "l", "164"},
        {"m", "m", "200"},
        {"n", "n", "349"},
        {"ŋ", "N", "22"},
        {"r", "r", "205"},
        {"w", "w", "257"},
        {"j", "j", "58"},
        {"ˈ", "\"", "0"}, // TODO: set isDiacritic = true
        {"ˌ", "%", "0"} // TODO: set isDiacritic = true
    };
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArrayFIL = new String[][] {
        {"aʊ", "aU", "-1"},
        {"ɔɪ", "OI", "-1"},
        {"əʊ", "@U", "-1"},
        {"ɛɪ", "EI", "-1"},
        {"ɑɪ", "AI", "-1"},
        {"tʃ", "tS", "-1"},
        {"dʒ", "dZ", "-1"}, // TODO: use /ʤ/ instead of /dʒ/?
        {"r̩", "r_=", "-1"}, // TODO: use "r=" instead of "r_="?
        {"ɑ", "A", "-1"},
        {"ɔ", "O", "-1"},
        {"u", "u", "-1"},
        {"i", "i", "-1"},
        {"æ", "{", "-1"},
        {"ʌ", "V", "-1"},
        {"ɛ", "E", "-1"},
        {"ɪ", "I", "-1"},
        {"ʊ", "U", "-1"},
        {"ə", "@", "-1"},
        {"p", "p", "-1"},
        {"t", "t", "-1"},
        {"k", "k", "-1"},
        {"b", "b", "-1"},
        {"d", "d", "-1"},
        {"g", "g", "-1"},
        {"f", "f", "-1"},
        {"v", "v", "-1"},
        {"θ", "T", "-1"},
        {"ð", "D", "-1"},
        {"s", "s", "-1"},
        {"z", "z", "-1"},
        {"ʃ", "S", "-1"},
        {"ʒ", "Z", "-1"},
        {"h", "h", "-1"},
        {"l", "l", "-1"},
        {"m", "m", "-1"},
        {"n", "n", "-1"},
        {"ɲ", "J", "-1"},
        {"ŋ", "N", "-1"},
        {"r", "r", "-1"},
        {"ɾ", "4", "-1"},
        {"w", "w", "-1"},
        {"j", "j", "-1"},
        {"ˈ", "\"", "-1"}, // TODO: set isDiacritic = true
        {"ˌ", "%", "-1"} // TODO: set isDiacritic = true
    };
    
    // Note: The array must list the allophones with larger IPA value length first, e.g. 'əʊ' before 'ə'
    public static final String[][] allophonesArraySWA = new String[][] {
        {"mb", "mb", "9"},
        {"mv", "mv", "0"},
        {"nd", "nd", "4"},
        {"nz", "nz", "4"},
        {"ɲɟ", "Jj\\", "0"},
        {"tʃ", "tS", "0"},
        {"ŋɡ", "Nɡ", "8"},
        {"ɑ", "A", "376"},
        {"ɛ", "E", "74"},
        {"i", "i", "207"},
        {"ɔ", "O", "0"},
        {"u", "u", "142"},
        {"m", "m", "124"},
        //{"ɓ", "b_<", "68"},
        {"p", "p", "23"},
        {"v", "v", "0"},
        {"f", "f", "10"},
        {"ð", "D", "0"},
        {"θ", "T", "0"},
        {"n", "n", "74"},
        {"ɗ", "d_<", "8"},
        {"t", "t", "0"},
        {"z", "z", "0"},
        {"s", "s", "76"},
        {"ɾ", "4", "0"},
        {"l", "l", "0"},
        {"ɲ", "J", "8"},
        {"ʄ", "j_<", "0"},
        {"ʃ", "S", "4"},
        {"j", "j", "0"},
        {"ŋ", "N", "0"},
        {"ɠ", "g_<", "0"},
        {"k", "k", "70"},
        {"ɣ", "G", "0"},
        {"x", "x", "0"},
        {"w", "w", "0"},
        {"h", "h", "19"},
        {"ˈ", "\"", "0"} // TODO: set isDiacritic = true
    };
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Allophones
        List<Allophone> allophonesGenerated = generateAllophones(contributor.getLanguage());
        for (Allophone allophone : allophonesGenerated) {
            logger.info("allophone.getValueIpa(): /" + allophone.getValueIpa() + "/, allophone.getValueSampa(): " + allophone.getValueSampa());
            Allophone existingAllophone = allophoneDao.readByValueIpa(contributor.getLanguage(), allophone.getValueIpa());
            if (existingAllophone == null) {
                allophoneDao.create(allophone);
            }
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLanguage());
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
    
    private List<Allophone> generateAllophones(Language language) {
        List<Allophone> allophones = new ArrayList<>();
        
        String[][] allophonesArray = null;
        if (language == Language.BEN) {
            allophonesArray = allophonesArrayBEN;
        } else if (language == Language.ENG) {
            allophonesArray = allophonesArrayENG;
        } else if (language == Language.FIL) {
            allophonesArray = allophonesArrayFIL;
        } else if (language == Language.SWA) {
            allophonesArray = allophonesArraySWA;
        }
        
        for (String[] allophoneRow : allophonesArray) {
            Allophone allophone = new Allophone();
            allophone.setLanguage(language);
            allophone.setTimeLastUpdate(Calendar.getInstance());
            allophone.setValueIpa(allophoneRow[0]);
            allophone.setValueSampa(allophoneRow[1]);
            // TODO: add SoundType to each Allophone
            allophone.setUsageCount(Integer.valueOf(allophoneRow[2]));
            // TODO: set diacritic
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
