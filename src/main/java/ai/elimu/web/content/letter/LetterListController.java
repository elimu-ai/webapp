package ai.elimu.web.content.letter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.enums.Locale;
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
    
    @Autowired
    private AllophoneDao allophoneDao;

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
        
        int maxUsageCount = 0;
        for (Letter letter : letters) {
            if (letter.getUsageCount() > maxUsageCount) {
                maxUsageCount = letter.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/letter/list";
    }
    
    private List<Letter> generateLetters(Locale locale) {
        List<Letter> letters = new ArrayList<>();
        
        if (locale == Locale.EN) {
            Letter letterE = new Letter();
            letterE.setLocale(locale);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setAllophones(getAllophones(locale, "ɛ"));
            letterE.setUsageCount(2168);
            letters.add(letterE);
            
            Letter letterA = new Letter();
            letterA.setLocale(locale);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setAllophones(getAllophones(locale, "æ"));
            letterA.setUsageCount(1414);
            letters.add(letterA);
            
            Letter letterT = new Letter();
            letterT.setLocale(locale);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setAllophones(getAllophones(locale, "t"));
            letterT.setUsageCount(1396);
            letters.add(letterT);
            
            Letter letterO = new Letter();
            letterO.setLocale(locale);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setAllophones(getAllophones(locale, "ɔ"));
            letterO.setUsageCount(1373);
            letters.add(letterO);
            
            Letter letterI = new Letter();
            letterI.setLocale(locale);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setAllophones(getAllophones(locale, "i"));
            letterI.setUsageCount(1154);
            letters.add(letterI);
            
            Letter letterH = new Letter();
            letterH.setLocale(locale);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setAllophones(getAllophones(locale, "h"));
            letterH.setUsageCount(1107);
            letters.add(letterH);
            
            Letter letterS = new Letter();
            letterS.setLocale(locale);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setAllophones(getAllophones(locale, "s"));
            letterS.setUsageCount(1083);
            letters.add(letterS);
            
            Letter letterN = new Letter();
            letterN.setLocale(locale);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setAllophones(getAllophones(locale, "n"));
            letterN.setUsageCount(967);
            letters.add(letterN);
            
            Letter letterR = new Letter();
            letterR.setLocale(locale);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setAllophones(getAllophones(locale, "r"));
            letterR.setUsageCount(844);
            letters.add(letterR);
            
            Letter letterD = new Letter();
            letterD.setLocale(locale);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setAllophones(getAllophones(locale, "d"));
            letterD.setUsageCount(745);
            letters.add(letterD);
            
            Letter letterL = new Letter();
            letterL.setLocale(locale);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setAllophones(getAllophones(locale, "l"));
            letterL.setUsageCount(722);
            letters.add(letterL);
            
            Letter letterW = new Letter();
            letterW.setLocale(locale);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setAllophones(getAllophones(locale, "w"));
            letterW.setUsageCount(498);
            letters.add(letterW);
            
            Letter letterY = new Letter();
            letterY.setLocale(locale);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setAllophones(getAllophones(locale, "j"));
            letterY.setUsageCount(482);
            letters.add(letterY);
            
            Letter letterM = new Letter();
            letterM.setLocale(locale);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setAllophones(getAllophones(locale, "m"));
            letterM.setUsageCount(436);
            letters.add(letterM);
            
            Letter letterU = new Letter();
            letterU.setLocale(locale);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setAllophones(getAllophones(locale, "ʌ"));
            letterU.setUsageCount(435);
            letters.add(letterU);
            
            Letter letterG = new Letter();
            letterG.setLocale(locale);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setAllophones(getAllophones(locale, "g"));
            letterG.setUsageCount(402);
            letters.add(letterG);
            
            Letter letterC = new Letter();
            letterC.setLocale(locale);
            letterC.setTimeLastUpdate(Calendar.getInstance());
            letterC.setText("c");
            letterC.setAllophones(getAllophones(locale, "k"));
            letterC.setUsageCount(360);
            letters.add(letterC);
            
            Letter letterB = new Letter();
            letterB.setLocale(locale);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setAllophones(getAllophones(locale, "b"));
            letterB.setUsageCount(290);
            letters.add(letterB);
            
            Letter letterP = new Letter();
            letterP.setLocale(locale);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setAllophones(getAllophones(locale, "p"));
            letterP.setUsageCount(253);
            letters.add(letterP);
            
            Letter letterK = new Letter();
            letterK.setLocale(locale);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setAllophones(getAllophones(locale, "k"));
            letterK.setUsageCount(241);
            letters.add(letterK);
            
            Letter letterF = new Letter();
            letterF.setLocale(locale);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setAllophones(getAllophones(locale, "f"));
            letterF.setUsageCount(237);
            letters.add(letterF);
            
            Letter letterV = new Letter();
            letterV.setLocale(locale);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setAllophones(getAllophones(locale, "v"));
            letterV.setUsageCount(161);
            letters.add(letterV);
            
            Letter letterZ = new Letter();
            letterZ.setLocale(locale);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setAllophones(getAllophones(locale, "z"));
            letterZ.setUsageCount(34);
            letters.add(letterZ);
            
            Letter letterJ = new Letter();
            letterJ.setLocale(locale);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setAllophones(getAllophones(locale, "dʒ"));
            letterJ.setUsageCount(21);
            letters.add(letterJ);
            
            Letter letterX = new Letter();
            letterX.setLocale(locale);
            letterX.setTimeLastUpdate(Calendar.getInstance());
            letterX.setText("x");
            letterX.setAllophones(getAllophones(locale, "k", "s"));
            letterX.setUsageCount(12);
            letters.add(letterX);
            
            Letter letterQ = new Letter();
            letterQ.setLocale(locale);
            letterQ.setTimeLastUpdate(Calendar.getInstance());
            letterQ.setText("q");
            letterQ.setAllophones(getAllophones(locale, "k", "w"));
            letterQ.setUsageCount(9);
            letters.add(letterQ);
            
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLocale(locale);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(locale, "i"));
            letterIUpperCase.setUsageCount(193);
            letters.add(letterIUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLocale(locale);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(locale, "t"));
            letterTUpperCase.setUsageCount(110);
            letters.add(letterTUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLocale(locale);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(locale, "w"));
            letterWUpperCase.setUsageCount(71);
            letters.add(letterWUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLocale(locale);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(locale, "h"));
            letterHUpperCase.setUsageCount(64);
            letters.add(letterHUpperCase);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLocale(locale);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(locale, "æ"));
            letterAUpperCase.setUsageCount(57);
            letters.add(letterAUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLocale(locale);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(locale, "m"));
            letterMUpperCase.setUsageCount(53);
            letters.add(letterMUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLocale(locale);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(locale, "s"));
            letterSUpperCase.setUsageCount(52);
            letters.add(letterSUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLocale(locale);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(locale, "l"));
            letterLUpperCase.setUsageCount(43);
            letters.add(letterLUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLocale(locale);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(locale, "g"));
            letterGUpperCase.setUsageCount(30);
            letters.add(letterGUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLocale(locale);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(locale, "b"));
            letterBUpperCase.setUsageCount(29);
            letters.add(letterBUpperCase);
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLocale(locale);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(locale, "ɔ"));
            letterOUpperCase.setUsageCount(29);
            letters.add(letterOUpperCase);
            
            Letter letterCUpperCase = new Letter();
            letterCUpperCase.setLocale(locale);
            letterCUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterCUpperCase.setText("C");
            letterCUpperCase.setAllophones(getAllophones(locale, "k"));
            letterCUpperCase.setUsageCount(27);
            letters.add(letterCUpperCase);
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLocale(locale);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(locale, "n"));
            letterNUpperCase.setUsageCount(27);
            letters.add(letterNUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLocale(locale);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(locale, "d"));
            letterDUpperCase.setUsageCount(24);
            letters.add(letterDUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLocale(locale);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(locale, "k"));
            letterKUpperCase.setUsageCount(19);
            letters.add(letterKUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLocale(locale);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(locale, "p"));
            letterPUpperCase.setUsageCount(18);
            letters.add(letterPUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLocale(locale);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(locale, "ɛ"));
            letterEUpperCase.setUsageCount(17);
            letters.add(letterEUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLocale(locale);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(locale, "j"));
            letterYUpperCase.setUsageCount(17);
            letters.add(letterYUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLocale(locale);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(locale, "f"));
            letterFUpperCase.setUsageCount(4);
            letters.add(letterFUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLocale(locale);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(locale, "z"));
            letterZUpperCase.setUsageCount(3);
            letters.add(letterZUpperCase);
            
            Letter letterQUpperCase = new Letter();
            letterQUpperCase.setLocale(locale);
            letterQUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterQUpperCase.setText("Q");
            letterQUpperCase.setAllophones(getAllophones(locale, "k", "w"));
            letterQUpperCase.setUsageCount(2);
            letters.add(letterQUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLocale(locale);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(locale, "v"));
            letterVUpperCase.setUsageCount(2);
            letters.add(letterVUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLocale(locale);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(locale, "dʒ"));
            letterJUpperCase.setUsageCount(1);
            letters.add(letterJUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLocale(locale);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(locale, "ʌ"));
            letterUUpperCase.setUsageCount(1);
            letters.add(letterUUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLocale(locale);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(locale, "r"));
            letterRUpperCase.setUsageCount(0);
            letters.add(letterRUpperCase);
            
            Letter letterXUpperCase = new Letter();
            letterXUpperCase.setLocale(locale);
            letterXUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterXUpperCase.setText("X");
            letterXUpperCase.setAllophones(getAllophones(locale, "k", "s"));
            letterXUpperCase.setUsageCount(0);
            letters.add(letterXUpperCase);
        } else if (locale == Locale.FI) {
            Letter letterA = new Letter();
            letterA.setLocale(locale);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setAllophones(getAllophones(locale, "ɑ"));
            letterA.setUsageCount(-1);
            letters.add(letterA);
            
            Letter letterB = new Letter();
            letterB.setLocale(locale);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setAllophones(getAllophones(locale, "b"));
            letterB.setUsageCount(-1);
            letters.add(letterB);
            
            Letter letterC = new Letter();
            letterC.setLocale(locale);
            letterC.setTimeLastUpdate(Calendar.getInstance());
            letterC.setText("c");
            letterC.setAllophones(getAllophones(locale, "k")); // TODO: can also be /s/
            letterC.setUsageCount(-1);
            letters.add(letterC);
            
            Letter letterD = new Letter();
            letterD.setLocale(locale);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setAllophones(getAllophones(locale, "d"));
            letterD.setUsageCount(-1);
            letters.add(letterD);
            
            Letter letterE = new Letter();
            letterE.setLocale(locale);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setAllophones(getAllophones(locale, "ɛ"));
            letterE.setUsageCount(-1);
            letters.add(letterE);
            
            Letter letterF = new Letter();
            letterF.setLocale(locale);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setAllophones(getAllophones(locale, "f"));
            letterF.setUsageCount(-1);
            letters.add(letterF);
            
            Letter letterG = new Letter();
            letterG.setLocale(locale);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setAllophones(getAllophones(locale, "g")); // TODO: can also be /dʒ/ and /h/
            letterG.setUsageCount(-1);
            letters.add(letterG);
            
            Letter letterH = new Letter();
            letterH.setLocale(locale);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setAllophones(getAllophones(locale, "h"));
            letterH.setUsageCount(-1);
            letters.add(letterH);
            
            Letter letterI = new Letter();
            letterI.setLocale(locale);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setAllophones(getAllophones(locale, "i"));
            letterI.setUsageCount(-1);
            letters.add(letterI);
            
            Letter letterJ = new Letter();
            letterJ.setLocale(locale);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setAllophones(getAllophones(locale, "dʒ")); // TODO: can also be /h/
            letterJ.setUsageCount(-1);
            letters.add(letterJ);
            
            Letter letterK = new Letter();
            letterK.setLocale(locale);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setAllophones(getAllophones(locale, "k"));
            letterK.setUsageCount(-1);
            letters.add(letterK);
            
            Letter letterL = new Letter();
            letterL.setLocale(locale);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setAllophones(getAllophones(locale, "l"));
            letterL.setUsageCount(-1);
            letters.add(letterL);
            
            Letter letterM = new Letter();
            letterM.setLocale(locale);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setAllophones(getAllophones(locale, "m"));
            letterM.setUsageCount(-1);
            letters.add(letterM);
            
            Letter letterN = new Letter();
            letterN.setLocale(locale);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setAllophones(getAllophones(locale, "n"));
            letterN.setUsageCount(-1);
            letters.add(letterN);
            
            Letter letterÑ = new Letter();
            letterÑ.setLocale(locale);
            letterÑ.setTimeLastUpdate(Calendar.getInstance());
            letterÑ.setText("ñ");
            letterÑ.setAllophones(getAllophones(locale, "ɲ"));
            letterÑ.setUsageCount(-1);
            letters.add(letterÑ);
            
            // TODO: add support for 2-character letters?
//            Letter letterNg = new Letter();
//            letterNg.setLocale(locale);
//            letterNg.setTimeLastUpdate(Calendar.getInstance());
//            letterNg.setText("ng");
//            letterNg.setAllophones(getAllophones(locale, "ŋ"));
//            letterNg.setUsageCount(-1);
//            letters.add(letterNg);
            
            Letter letterO = new Letter();
            letterO.setLocale(locale);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setAllophones(getAllophones(locale, "ɔ"));
            letterO.setUsageCount(-1);
            letters.add(letterO);
            
            Letter letterP = new Letter();
            letterP.setLocale(locale);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setAllophones(getAllophones(locale, "p"));
            letterP.setUsageCount(-1);
            letters.add(letterP);
            
            Letter letterQ = new Letter();
            letterQ.setLocale(locale);
            letterQ.setTimeLastUpdate(Calendar.getInstance());
            letterQ.setText("q");
            letterQ.setAllophones(getAllophones(locale, "k"));
            letterQ.setUsageCount(-1);
            letters.add(letterQ);
            
            Letter letterR = new Letter();
            letterR.setLocale(locale);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setAllophones(getAllophones(locale, "ɾ"));
            letterR.setUsageCount(-1);
            letters.add(letterR);
            
            Letter letterS = new Letter();
            letterS.setLocale(locale);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setAllophones(getAllophones(locale, "s")); // TODO: can also be /z/
            letterS.setUsageCount(-1);
            letters.add(letterS);
            
            Letter letterT = new Letter();
            letterT.setLocale(locale);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setAllophones(getAllophones(locale, "t"));
            letterT.setUsageCount(-1);
            letters.add(letterT);
            
            Letter letterU = new Letter();
            letterU.setLocale(locale);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setAllophones(getAllophones(locale, "u"));
            letterU.setUsageCount(-1);
            letters.add(letterU);
            
            Letter letterV = new Letter();
            letterV.setLocale(locale);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setAllophones(getAllophones(locale, "v"));
            letterV.setUsageCount(-1);
            letters.add(letterV);
            
            Letter letterW = new Letter();
            letterW.setLocale(locale);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setAllophones(getAllophones(locale, "w"));
            letterW.setUsageCount(-1);
            letters.add(letterW);
            
            Letter letterX = new Letter();
            letterX.setLocale(locale);
            letterX.setTimeLastUpdate(Calendar.getInstance());
            letterX.setText("x");
            letterX.setAllophones(getAllophones(locale, "k", "s"));
            letterX.setUsageCount(-1);
            letters.add(letterX);
            
            Letter letterY = new Letter();
            letterY.setLocale(locale);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setAllophones(getAllophones(locale, "j"));
            letterY.setUsageCount(-1);
            letters.add(letterY);
            
            Letter letterZ = new Letter();
            letterZ.setLocale(locale);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setAllophones(getAllophones(locale, "z")); // TODO: can also be /s/
            letterZ.setUsageCount(-1);
            letters.add(letterZ);
            
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLocale(locale);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(locale, "ɑ"));
            letterAUpperCase.setUsageCount(-1);
            letters.add(letterAUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLocale(locale);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(locale, "b"));
            letterBUpperCase.setUsageCount(-1);
            letters.add(letterBUpperCase);
            
            Letter letterCUpperCase = new Letter();
            letterCUpperCase.setLocale(locale);
            letterCUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterCUpperCase.setText("C");
            letterCUpperCase.setAllophones(getAllophones(locale, "k")); // TODO: can also be /s/
            letterCUpperCase.setUsageCount(-1);
            letters.add(letterCUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLocale(locale);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(locale, "d"));
            letterDUpperCase.setUsageCount(-1);
            letters.add(letterDUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLocale(locale);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(locale, "ɛ"));
            letterEUpperCase.setUsageCount(-1);
            letters.add(letterEUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLocale(locale);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(locale, "f"));
            letterFUpperCase.setUsageCount(-1);
            letters.add(letterFUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLocale(locale);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(locale, "g")); // TODO: can also be /dʒ/ and /h/
            letterGUpperCase.setUsageCount(-1);
            letters.add(letterGUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLocale(locale);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(locale, "h"));
            letterHUpperCase.setUsageCount(-1);
            letters.add(letterHUpperCase);
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLocale(locale);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(locale, "i"));
            letterIUpperCase.setUsageCount(-1);
            letters.add(letterIUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLocale(locale);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(locale, "dʒ")); // TODO: can also be /h/
            letterJUpperCase.setUsageCount(-1);
            letters.add(letterJUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLocale(locale);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(locale, "k"));
            letterKUpperCase.setUsageCount(-1);
            letters.add(letterKUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLocale(locale);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(locale, "l"));
            letterLUpperCase.setUsageCount(-1);
            letters.add(letterLUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLocale(locale);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(locale, "m"));
            letterMUpperCase.setUsageCount(-1);
            letters.add(letterMUpperCase);
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLocale(locale);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(locale, "n"));
            letterNUpperCase.setUsageCount(-1);
            letters.add(letterNUpperCase);
            
            Letter letterÑUpperCase = new Letter();
            letterÑUpperCase.setLocale(locale);
            letterÑUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterÑUpperCase.setText("Ñ");
            letterÑUpperCase.setAllophones(getAllophones(locale, "ɲ"));
            letterÑUpperCase.setUsageCount(-1);
            letters.add(letterÑUpperCase);
            
            // TODO: add support for 2-character letters?
//            Letter letterNgUpperCase = new Letter();
//            letterNgUpperCase.setLocale(locale);
//            letterNgUpperCase.setTimeLastUpdate(Calendar.getInstance());
//            letterNgUpperCase.setText("NG");
//            letterNgUpperCase.setAllophones(getAllophones(locale, "ŋ"));
//            letterNgUpperCase.setUsageCount(-1);
//            letters.add(letterNgUpperCase);
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLocale(locale);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(locale, "ɔ"));
            letterOUpperCase.setUsageCount(-1);
            letters.add(letterOUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLocale(locale);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(locale, "p"));
            letterPUpperCase.setUsageCount(-1);
            letters.add(letterPUpperCase);
            
            Letter letterQUpperCase = new Letter();
            letterQUpperCase.setLocale(locale);
            letterQUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterQUpperCase.setText("Q");
            letterQUpperCase.setAllophones(getAllophones(locale, "k"));
            letterQUpperCase.setUsageCount(-1);
            letters.add(letterQUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLocale(locale);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(locale, "ɾ"));
            letterRUpperCase.setUsageCount(-1);
            letters.add(letterRUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLocale(locale);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(locale, "s")); // TODO: can also be /z/
            letterSUpperCase.setUsageCount(-1);
            letters.add(letterSUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLocale(locale);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(locale, "t"));
            letterTUpperCase.setUsageCount(-1);
            letters.add(letterTUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLocale(locale);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(locale, "u"));
            letterUUpperCase.setUsageCount(-1);
            letters.add(letterUUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLocale(locale);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(locale, "v"));
            letterVUpperCase.setUsageCount(-1);
            letters.add(letterVUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLocale(locale);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(locale, "w"));
            letterWUpperCase.setUsageCount(-1);
            letters.add(letterWUpperCase);
            
            Letter letterXUpperCase = new Letter();
            letterXUpperCase.setLocale(locale);
            letterXUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterXUpperCase.setText("X");
            letterXUpperCase.setAllophones(getAllophones(locale, "k", "s"));
            letterXUpperCase.setUsageCount(-1);
            letters.add(letterXUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLocale(locale);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(locale, "j"));
            letterYUpperCase.setUsageCount(-1);
            letters.add(letterYUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLocale(locale);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(locale, "z")); // TODO: can also be /s/
            letterZUpperCase.setUsageCount(-1);
            letters.add(letterZUpperCase);
        } else if (locale == Locale.SW) {
            Letter letterA = new Letter();
            letterA.setLocale(locale);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setAllophones(getAllophones(locale, "ɑ"));
            letterA.setUsageCount(994);
            letters.add(letterA);
            
            Letter letterI = new Letter();
            letterI.setLocale(locale);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setAllophones(getAllophones(locale, "i"));
            letterI.setUsageCount(608);
            letters.add(letterI);
            
            Letter letterU = new Letter();
            letterU.setLocale(locale);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setAllophones(getAllophones(locale, "u"));
            letterU.setUsageCount(393);
            letters.add(letterU);
            
            Letter letterN = new Letter();
            letterN.setLocale(locale);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setAllophones(getAllophones(locale, "n"));
            letterN.setUsageCount(334);
            letters.add(letterN);
            
            Letter letterK = new Letter();
            letterK.setLocale(locale);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setAllophones(getAllophones(locale, "k"));
            letterK.setUsageCount(305);
            letters.add(letterK);
            
            Letter letterE = new Letter();
            letterE.setLocale(locale);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setAllophones(getAllophones(locale, "ɛ"));
            letterE.setUsageCount(270);
            letters.add(letterE);
            
            Letter letterL = new Letter();
            letterL.setLocale(locale);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setAllophones(getAllophones(locale, "l"));
            letterL.setUsageCount(269);
            letters.add(letterL);
            
            Letter letterM = new Letter();
            letterM.setLocale(locale);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setAllophones(getAllophones(locale, "m"));
            letterM.setUsageCount(224);
            letters.add(letterM);
            
            Letter letterW = new Letter();
            letterW.setLocale(locale);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setAllophones(getAllophones(locale, "w"));
            letterW.setUsageCount(190);
            letters.add(letterW);
            
            Letter letterO = new Letter();
            letterO.setLocale(locale);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setAllophones(getAllophones(locale, "ɔ"));
            letterO.setUsageCount(159);
            letters.add(letterO);
            
            Letter letterB = new Letter();
            letterB.setLocale(locale);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setAllophones(getAllophones(locale, "ɓ"));
            letterB.setUsageCount(130);
            letters.add(letterB);
            
            Letter letterS = new Letter();
            letterS.setLocale(locale);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setAllophones(getAllophones(locale, "s"));
            letterS.setUsageCount(110);
            letters.add(letterS);
            
            Letter letterT = new Letter();
            letterT.setLocale(locale);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setAllophones(getAllophones(locale, "t"));
            letterT.setUsageCount(108);
            letters.add(letterT);
            
            Letter letterH = new Letter();
            letterH.setLocale(locale);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setAllophones(getAllophones(locale, "h"));
            letterH.setUsageCount(97);
            letters.add(letterH);
            
            Letter letterG = new Letter();
            letterG.setLocale(locale);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setAllophones(getAllophones(locale, "ɠ"));
            letterG.setUsageCount(96);
            letters.add(letterG);
            
            Letter letterR = new Letter();
            letterR.setLocale(locale);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setAllophones(getAllophones(locale, "ɾ"));
            letterR.setUsageCount(52);
            letters.add(letterR);
            
            Letter letterD = new Letter();
            letterD.setLocale(locale);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setAllophones(getAllophones(locale, "ɗ"));
            letterD.setUsageCount(80);
            letters.add(letterD);
            
            Letter letterY = new Letter();
            letterY.setLocale(locale);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setAllophones(getAllophones(locale, "j"));
            letterY.setUsageCount(77);
            letters.add(letterY);
            
            Letter letterP = new Letter();
            letterP.setLocale(locale);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setAllophones(getAllophones(locale, "p"));
            letterP.setUsageCount(64);
            letters.add(letterP);
            
            Letter letterZ = new Letter();
            letterZ.setLocale(locale);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setAllophones(getAllophones(locale, "z"));
            letterZ.setUsageCount(64);
            letters.add(letterZ);
            
            Letter letterJ = new Letter();
            letterJ.setLocale(locale);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setAllophones(getAllophones(locale, "ʄ"));
            letterJ.setUsageCount(39);
            letters.add(letterJ);
            
            Letter letterF = new Letter();
            letterF.setLocale(locale);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setAllophones(getAllophones(locale, "f"));
            letterF.setUsageCount(23);
            letters.add(letterF);
            
//            // TODO: replace 'c' with 'ch'?
//            Letter letterC = new Letter();
//            letterC.setLocale(locale);
//            letterC.setTimeLastUpdate(Calendar.getInstance());
//            letterC.setText("c");
//            letterC.setAllophones(getAllophones(locale, "tʃ"));
//            letterC.setUsageCount(21);
//            letters.add(letterC);
            
            Letter letterV = new Letter();
            letterV.setLocale(locale);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setAllophones(getAllophones(locale, "v"));
            letterV.setUsageCount(17);
            letters.add(letterV);
            
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLocale(locale);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(locale, "n"));
            letterNUpperCase.setUsageCount(43);
            letters.add(letterNUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLocale(locale);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(locale, "m"));
            letterMUpperCase.setUsageCount(34);
            letters.add(letterMUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLocale(locale);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(locale, "p"));
            letterPUpperCase.setUsageCount(15);
            letters.add(letterPUpperCase);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLocale(locale);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(locale, "ɑ"));
            letterAUpperCase.setUsageCount(13);
            letters.add(letterAUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLocale(locale);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(locale, "t"));
            letterTUpperCase.setUsageCount(13);
            letters.add(letterTUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLocale(locale);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(locale, "ɓ"));
            letterBUpperCase.setUsageCount(12);
            letters.add(letterBUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLocale(locale);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(locale, "ɛ"));
            letterEUpperCase.setUsageCount(12);
            letters.add(letterEUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLocale(locale);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(locale, "k"));
            letterKUpperCase.setUsageCount(12);
            letters.add(letterKUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLocale(locale);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(locale, "s"));
            letterSUpperCase.setUsageCount(11);
            letters.add(letterSUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLocale(locale);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(locale, "u"));
            letterUUpperCase.setUsageCount(10);
            letters.add(letterUUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLocale(locale);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(locale, "w"));
            letterWUpperCase.setUsageCount(10);
            letters.add(letterWUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLocale(locale);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(locale, "f"));
            letterFUpperCase.setUsageCount(9);
            letters.add(letterFUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLocale(locale);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(locale, "l"));
            letterLUpperCase.setUsageCount(8);
            letters.add(letterLUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLocale(locale);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(locale, "h"));
            letterHUpperCase.setUsageCount(7);
            letters.add(letterHUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLocale(locale);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(locale, "ʄ"));
            letterJUpperCase.setUsageCount(6);
            letters.add(letterJUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLocale(locale);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(locale, "ɗ"));
            letterDUpperCase.setUsageCount(4);
            letters.add(letterDUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLocale(locale);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(locale, "j"));
            letterYUpperCase.setUsageCount(3);
            letters.add(letterYUpperCase);
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLocale(locale);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(locale, "i"));
            letterIUpperCase.setUsageCount(2);
            letters.add(letterIUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLocale(locale);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(locale, "ɠ"));
            letterGUpperCase.setUsageCount(1);
            letters.add(letterGUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLocale(locale);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(locale, "ɾ"));
            letterRUpperCase.setUsageCount(1);
            letters.add(letterRUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLocale(locale);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(locale, "v"));
            letterVUpperCase.setUsageCount(1);
            letters.add(letterVUpperCase);
            
            // TODO: replace 'C' with 'CH'?
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLocale(locale);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(locale, "ɔ"));
            letterOUpperCase.setUsageCount(0);
            letters.add(letterOUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLocale(locale);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(locale, "z"));
            letterZUpperCase.setUsageCount(0);
            letters.add(letterZUpperCase);
        }
        
        return letters;
    }
    
    /**
     * As an example, the letter 'x' is represented by two Allophones; /k/ and /s/. So these two would be combined into 
     * /ks/.
     */
    private List<Allophone> getAllophones(Locale locale, String... ipaValues) {
        List<Allophone> allophones = new ArrayList<>();
        
        for (String ipaValue : ipaValues) {
            Allophone allophone = allophoneDao.readByValueIpa(locale, ipaValue);
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
