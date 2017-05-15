package org.literacyapp.web.content.letter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/list")
public class LetterListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Letters
        List<Letter> lettersGenerated = generateLetters(contributor.getLocale());
        for (Letter letter : lettersGenerated) {
            logger.info("letter.getText(): " + letter.getText());
            Letter existingLetter = letterDao.readByText(letter.getLocale(), letter.getText());
            if (existingLetter == null) {
                letterDao.create(letter);
            }
        }
        
        List<Letter> letters = letterDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("letters", letters);

        return "content/letter/list";
    }
    
    private List<Letter> generateLetters(Locale locale) {
        List<Letter> letters = new ArrayList<>();
        
        List<String> letterStringArray = new ArrayList<>();
        if (locale == Locale.AR) {
            // TODO
        } else if (locale == Locale.EN) {
            Letter letterE = new Letter();
            letterE.setLocale(locale);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setUsageCount(2168);
            letters.add(letterE);
            
            Letter letterA = new Letter();
            letterA.setLocale(locale);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setUsageCount(1414);
            letters.add(letterA);
            
            Letter letterT = new Letter();
            letterT.setLocale(locale);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setUsageCount(1396);
            letters.add(letterT);
            
            Letter letterO = new Letter();
            letterO.setLocale(locale);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setUsageCount(1373);
            letters.add(letterO);
            
            Letter letterI = new Letter();
            letterI.setLocale(locale);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setUsageCount(1154);
            letters.add(letterI);
            
            Letter letterH = new Letter();
            letterH.setLocale(locale);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setUsageCount(1107);
            letters.add(letterH);
            
            Letter letterS = new Letter();
            letterS.setLocale(locale);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setUsageCount(1083);
            letters.add(letterS);
            
            Letter letterN = new Letter();
            letterN.setLocale(locale);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setUsageCount(967);
            letters.add(letterN);
            
            Letter letterR = new Letter();
            letterR.setLocale(locale);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setUsageCount(844);
            letters.add(letterR);
            
            Letter letterD = new Letter();
            letterD.setLocale(locale);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setUsageCount(745);
            letters.add(letterD);
            
            Letter letterL = new Letter();
            letterL.setLocale(locale);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setUsageCount(722);
            letters.add(letterL);
            
            Letter letterW = new Letter();
            letterW.setLocale(locale);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setUsageCount(498);
            letters.add(letterW);
            
            Letter letterY = new Letter();
            letterY.setLocale(locale);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setUsageCount(482);
            letters.add(letterY);
            
            Letter letterM = new Letter();
            letterM.setLocale(locale);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setUsageCount(436);
            letters.add(letterM);
            
            Letter letterU = new Letter();
            letterU.setLocale(locale);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setUsageCount(435);
            letters.add(letterU);
            
            Letter letterG = new Letter();
            letterG.setLocale(locale);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setUsageCount(402);
            letters.add(letterG);
            
            Letter letterC = new Letter();
            letterC.setLocale(locale);
            letterC.setTimeLastUpdate(Calendar.getInstance());
            letterC.setText("c");
            letterC.setUsageCount(360);
            letters.add(letterC);
            
            Letter letterB = new Letter();
            letterB.setLocale(locale);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setUsageCount(290);
            letters.add(letterB);
            
            Letter letterP = new Letter();
            letterP.setLocale(locale);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setUsageCount(253);
            letters.add(letterP);
            
            Letter letterK = new Letter();
            letterK.setLocale(locale);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setUsageCount(241);
            letters.add(letterK);
            
            Letter letterF = new Letter();
            letterF.setLocale(locale);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setUsageCount(237);
            letters.add(letterF);
            
            Letter letterV = new Letter();
            letterV.setLocale(locale);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setUsageCount(161);
            letters.add(letterV);
            
            Letter letterZ = new Letter();
            letterZ.setLocale(locale);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setUsageCount(34);
            letters.add(letterZ);
            
            Letter letterJ = new Letter();
            letterJ.setLocale(locale);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setUsageCount(21);
            letters.add(letterJ);
            
            Letter letterX = new Letter();
            letterX.setLocale(locale);
            letterX.setTimeLastUpdate(Calendar.getInstance());
            letterX.setText("x");
            letterX.setUsageCount(12);
            letters.add(letterX);
            
            Letter letterQ = new Letter();
            letterQ.setLocale(locale);
            letterQ.setTimeLastUpdate(Calendar.getInstance());
            letterQ.setText("q");
            letterQ.setUsageCount(9);
            letters.add(letterQ);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLocale(locale);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setBraille("⠁");
//            letterAUpperCase.setUsageCount();
            letters.add(letterAUpperCase);
            
            
            
        } else if (locale == Locale.ES) {
//            letterStringArray = new ArrayList<>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z"));
            
            // TODO
        } else if (locale == Locale.SW) {
//            letterStringArray = new ArrayList<>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","v","w","y","z"));
            
            // TODO
        }
        
        return letters;
    }
}
