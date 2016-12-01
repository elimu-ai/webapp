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
            Allophone existingAllophone = allophoneDao.readByValueIpa(contributor.getLocale(), allophone.getValueIpa());
            if (existingAllophone == null) {
                allophoneDao.create(allophone);
            }
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("allophones", allophones);

        return "content/allophone/list";
    }
    
    private List<Allophone> generateAllophones(Locale locale) {
        List<Allophone> allophones = new ArrayList<>();
        
        String[][] allophonesArray = null;
        if (locale == Locale.AR) {
            // TODO
        } else if (locale == Locale.EN) {
            allophonesArray = new String[][] {
                {"ˈ","\""},
                {"ɑ","A"},
                {"ɔ","O"},
                {"u","u"},
                {"i","i"},
                {"æ","{"},
                {"ʌ","V"},
                {"ɛ","E"},
                {"ɪ","I"},
                {"ʊ","U"},
                {"ə","@"},
                {"r̩","r_="}, // TODO: use "r=" instead of "r_="?
                {"aʊ","aU"},
                {"ɔɪ","OI"},
                {"əʊ","@U"},
                {"ɛɪ","EI"},
                {"ɑɪ","AI"},
                {"p","p"},
                {"t","t"},
                {"k","k"},
                {"b","b"},
                {"d","d"},
                {"g","g"},
                {"tʃ","tS"},
                {"dʒ","dZ"}, // TODO: use /ʤ/ instead of /dʒ/?
                {"f","f"},
                {"v","v"},
                {"θ","T"},
                {"ð","D"},
                {"s","s"},
                {"z","z"},
                {"ʃ","S"},
                {"ʒ","Z"},
                {"h","h"},
                {"l","l"},
                {"m","m"},
                {"n","n"},
                {"ŋ","N"},
                {"r","r"},
                {"w","w"},
                {"j","j"}
            };
        } else if (locale == Locale.ES) {
            // TODO
        } else if (locale == Locale.SW) {
            allophonesArray = new String[][] {
                {"ˈ","\""},
                {"ɑ","A"},
                {"ɛ","E"},
                {"i","i"},
                {"ɔ","O"},
                {"u","u"},
                {"m","m"},
                {"mb","mb"},
                {"ɓ","b_<"},
                {"p","p"},
                {"mv","mv"},
                {"v","v"},
                {"f","f"},
                {"ð","D"},
                {"θ","T"},
                {"n","n"},
                {"nd","nd"},
                {"ɗ","d_<"},
                {"t","t"},
                {"nz","nz"},
                {"z","z"},
                {"s","s"},
                {"ɾ","4"},
                {"l","l"},
                {"ɲ","J"},
                {"ɲɟ","Jj\\"},
                {"ʄ","j_<"},
                {"tʃ","tS"},
                {"ʃ","S"},
                {"j","j"},
                {"ŋ","N"},
                {"ŋɡ","Nɡ"},
                {"ɠ","g_<"},
                {"k","k"},
                {"ɣ","G"},
                {"x","x"},
                {"w","w"},
                {"h","h"}
            };
        }
        
        for (String[] allophoneRow : allophonesArray) {
            Allophone allophone = new Allophone();
            allophone.setLocale(locale);
            allophone.setTimeLastUpdate(Calendar.getInstance());
            allophone.setValueIpa(allophoneRow[0]);
            allophone.setValueSampa(allophoneRow[1]);
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
