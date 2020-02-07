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
import ai.elimu.model.enums.Language;
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
        List<Letter> lettersGenerated = generateLetters(contributor.getLanguage());
        for (Letter letter : lettersGenerated) {
            logger.info("letter.getText(): " + letter.getText());
            Letter existingLetter = letterDao.readByText(letter.getLanguage(), letter.getText());
            if (existingLetter == null) {
                letterDao.create(letter);
            }
        }
        
        List<Letter> letters = letterDao.readAllOrdered(contributor.getLanguage());
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
    
    private List<Letter> generateLetters(Language language) {
        List<Letter> letters = new ArrayList<>();
        
        if (language == Language.ENG) {
            Letter letterE = new Letter();
            letterE.setLanguage(language);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setAllophones(getAllophones(language, "ɛ"));
            letterE.setUsageCount(2168);
            letters.add(letterE);
            
            Letter letterA = new Letter();
            letterA.setLanguage(language);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setAllophones(getAllophones(language, "æ"));
            letterA.setUsageCount(1414);
            letters.add(letterA);
            
            Letter letterT = new Letter();
            letterT.setLanguage(language);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setAllophones(getAllophones(language, "t"));
            letterT.setUsageCount(1396);
            letters.add(letterT);
            
            Letter letterO = new Letter();
            letterO.setLanguage(language);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setAllophones(getAllophones(language, "ɔ"));
            letterO.setUsageCount(1373);
            letters.add(letterO);
            
            Letter letterI = new Letter();
            letterI.setLanguage(language);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setAllophones(getAllophones(language, "i"));
            letterI.setUsageCount(1154);
            letters.add(letterI);
            
            Letter letterH = new Letter();
            letterH.setLanguage(language);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setAllophones(getAllophones(language, "h"));
            letterH.setUsageCount(1107);
            letters.add(letterH);
            
            Letter letterS = new Letter();
            letterS.setLanguage(language);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setAllophones(getAllophones(language, "s"));
            letterS.setUsageCount(1083);
            letters.add(letterS);
            
            Letter letterN = new Letter();
            letterN.setLanguage(language);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setAllophones(getAllophones(language, "n"));
            letterN.setUsageCount(967);
            letters.add(letterN);
            
            Letter letterR = new Letter();
            letterR.setLanguage(language);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setAllophones(getAllophones(language, "r"));
            letterR.setUsageCount(844);
            letters.add(letterR);
            
            Letter letterD = new Letter();
            letterD.setLanguage(language);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setAllophones(getAllophones(language, "d"));
            letterD.setUsageCount(745);
            letters.add(letterD);
            
            Letter letterL = new Letter();
            letterL.setLanguage(language);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setAllophones(getAllophones(language, "l"));
            letterL.setUsageCount(722);
            letters.add(letterL);
            
            Letter letterW = new Letter();
            letterW.setLanguage(language);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setAllophones(getAllophones(language, "w"));
            letterW.setUsageCount(498);
            letters.add(letterW);
            
            Letter letterY = new Letter();
            letterY.setLanguage(language);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setAllophones(getAllophones(language, "j"));
            letterY.setUsageCount(482);
            letters.add(letterY);
            
            Letter letterM = new Letter();
            letterM.setLanguage(language);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setAllophones(getAllophones(language, "m"));
            letterM.setUsageCount(436);
            letters.add(letterM);
            
            Letter letterU = new Letter();
            letterU.setLanguage(language);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setAllophones(getAllophones(language, "ʌ"));
            letterU.setUsageCount(435);
            letters.add(letterU);
            
            Letter letterG = new Letter();
            letterG.setLanguage(language);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setAllophones(getAllophones(language, "g"));
            letterG.setUsageCount(402);
            letters.add(letterG);
            
            Letter letterC = new Letter();
            letterC.setLanguage(language);
            letterC.setTimeLastUpdate(Calendar.getInstance());
            letterC.setText("c");
            letterC.setAllophones(getAllophones(language, "k"));
            letterC.setUsageCount(360);
            letters.add(letterC);
            
            Letter letterB = new Letter();
            letterB.setLanguage(language);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setAllophones(getAllophones(language, "b"));
            letterB.setUsageCount(290);
            letters.add(letterB);
            
            Letter letterP = new Letter();
            letterP.setLanguage(language);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setAllophones(getAllophones(language, "p"));
            letterP.setUsageCount(253);
            letters.add(letterP);
            
            Letter letterK = new Letter();
            letterK.setLanguage(language);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setAllophones(getAllophones(language, "k"));
            letterK.setUsageCount(241);
            letters.add(letterK);
            
            Letter letterF = new Letter();
            letterF.setLanguage(language);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setAllophones(getAllophones(language, "f"));
            letterF.setUsageCount(237);
            letters.add(letterF);
            
            Letter letterV = new Letter();
            letterV.setLanguage(language);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setAllophones(getAllophones(language, "v"));
            letterV.setUsageCount(161);
            letters.add(letterV);
            
            Letter letterZ = new Letter();
            letterZ.setLanguage(language);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setAllophones(getAllophones(language, "z"));
            letterZ.setUsageCount(34);
            letters.add(letterZ);
            
            Letter letterJ = new Letter();
            letterJ.setLanguage(language);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setAllophones(getAllophones(language, "dʒ"));
            letterJ.setUsageCount(21);
            letters.add(letterJ);
            
            Letter letterX = new Letter();
            letterX.setLanguage(language);
            letterX.setTimeLastUpdate(Calendar.getInstance());
            letterX.setText("x");
            letterX.setAllophones(getAllophones(language, "k", "s"));
            letterX.setUsageCount(12);
            letters.add(letterX);
            
            Letter letterQ = new Letter();
            letterQ.setLanguage(language);
            letterQ.setTimeLastUpdate(Calendar.getInstance());
            letterQ.setText("q");
            letterQ.setAllophones(getAllophones(language, "k", "w"));
            letterQ.setUsageCount(9);
            letters.add(letterQ);
            
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLanguage(language);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(language, "i"));
            letterIUpperCase.setUsageCount(193);
            letters.add(letterIUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLanguage(language);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(language, "t"));
            letterTUpperCase.setUsageCount(110);
            letters.add(letterTUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLanguage(language);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(language, "w"));
            letterWUpperCase.setUsageCount(71);
            letters.add(letterWUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLanguage(language);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(language, "h"));
            letterHUpperCase.setUsageCount(64);
            letters.add(letterHUpperCase);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLanguage(language);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(language, "æ"));
            letterAUpperCase.setUsageCount(57);
            letters.add(letterAUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLanguage(language);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(language, "m"));
            letterMUpperCase.setUsageCount(53);
            letters.add(letterMUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLanguage(language);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(language, "s"));
            letterSUpperCase.setUsageCount(52);
            letters.add(letterSUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLanguage(language);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(language, "l"));
            letterLUpperCase.setUsageCount(43);
            letters.add(letterLUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLanguage(language);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(language, "g"));
            letterGUpperCase.setUsageCount(30);
            letters.add(letterGUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLanguage(language);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(language, "b"));
            letterBUpperCase.setUsageCount(29);
            letters.add(letterBUpperCase);
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLanguage(language);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(language, "ɔ"));
            letterOUpperCase.setUsageCount(29);
            letters.add(letterOUpperCase);
            
            Letter letterCUpperCase = new Letter();
            letterCUpperCase.setLanguage(language);
            letterCUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterCUpperCase.setText("C");
            letterCUpperCase.setAllophones(getAllophones(language, "k"));
            letterCUpperCase.setUsageCount(27);
            letters.add(letterCUpperCase);
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLanguage(language);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(language, "n"));
            letterNUpperCase.setUsageCount(27);
            letters.add(letterNUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLanguage(language);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(language, "d"));
            letterDUpperCase.setUsageCount(24);
            letters.add(letterDUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLanguage(language);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(language, "k"));
            letterKUpperCase.setUsageCount(19);
            letters.add(letterKUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLanguage(language);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(language, "p"));
            letterPUpperCase.setUsageCount(18);
            letters.add(letterPUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLanguage(language);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(language, "ɛ"));
            letterEUpperCase.setUsageCount(17);
            letters.add(letterEUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLanguage(language);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(language, "j"));
            letterYUpperCase.setUsageCount(17);
            letters.add(letterYUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLanguage(language);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(language, "f"));
            letterFUpperCase.setUsageCount(4);
            letters.add(letterFUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLanguage(language);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(language, "z"));
            letterZUpperCase.setUsageCount(3);
            letters.add(letterZUpperCase);
            
            Letter letterQUpperCase = new Letter();
            letterQUpperCase.setLanguage(language);
            letterQUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterQUpperCase.setText("Q");
            letterQUpperCase.setAllophones(getAllophones(language, "k", "w"));
            letterQUpperCase.setUsageCount(2);
            letters.add(letterQUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLanguage(language);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(language, "v"));
            letterVUpperCase.setUsageCount(2);
            letters.add(letterVUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLanguage(language);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(language, "dʒ"));
            letterJUpperCase.setUsageCount(1);
            letters.add(letterJUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLanguage(language);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(language, "ʌ"));
            letterUUpperCase.setUsageCount(1);
            letters.add(letterUUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLanguage(language);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(language, "r"));
            letterRUpperCase.setUsageCount(0);
            letters.add(letterRUpperCase);
            
            Letter letterXUpperCase = new Letter();
            letterXUpperCase.setLanguage(language);
            letterXUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterXUpperCase.setText("X");
            letterXUpperCase.setAllophones(getAllophones(language, "k", "s"));
            letterXUpperCase.setUsageCount(0);
            letters.add(letterXUpperCase);
        } else if (language == Language.FI) {
            Letter letterA = new Letter();
            letterA.setLanguage(language);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setAllophones(getAllophones(language, "ɑ"));
            letterA.setUsageCount(-1);
            letters.add(letterA);
            
            Letter letterB = new Letter();
            letterB.setLanguage(language);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setAllophones(getAllophones(language, "b"));
            letterB.setUsageCount(-1);
            letters.add(letterB);
            
            Letter letterC = new Letter();
            letterC.setLanguage(language);
            letterC.setTimeLastUpdate(Calendar.getInstance());
            letterC.setText("c");
            letterC.setAllophones(getAllophones(language, "k")); // TODO: can also be /s/
            letterC.setUsageCount(-1);
            letters.add(letterC);
            
            Letter letterD = new Letter();
            letterD.setLanguage(language);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setAllophones(getAllophones(language, "d"));
            letterD.setUsageCount(-1);
            letters.add(letterD);
            
            Letter letterE = new Letter();
            letterE.setLanguage(language);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setAllophones(getAllophones(language, "ɛ"));
            letterE.setUsageCount(-1);
            letters.add(letterE);
            
            Letter letterF = new Letter();
            letterF.setLanguage(language);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setAllophones(getAllophones(language, "f"));
            letterF.setUsageCount(-1);
            letters.add(letterF);
            
            Letter letterG = new Letter();
            letterG.setLanguage(language);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setAllophones(getAllophones(language, "g")); // TODO: can also be /dʒ/ and /h/
            letterG.setUsageCount(-1);
            letters.add(letterG);
            
            Letter letterH = new Letter();
            letterH.setLanguage(language);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setAllophones(getAllophones(language, "h"));
            letterH.setUsageCount(-1);
            letters.add(letterH);
            
            Letter letterI = new Letter();
            letterI.setLanguage(language);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setAllophones(getAllophones(language, "i"));
            letterI.setUsageCount(-1);
            letters.add(letterI);
            
            Letter letterJ = new Letter();
            letterJ.setLanguage(language);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setAllophones(getAllophones(language, "dʒ")); // TODO: can also be /h/
            letterJ.setUsageCount(-1);
            letters.add(letterJ);
            
            Letter letterK = new Letter();
            letterK.setLanguage(language);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setAllophones(getAllophones(language, "k"));
            letterK.setUsageCount(-1);
            letters.add(letterK);
            
            Letter letterL = new Letter();
            letterL.setLanguage(language);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setAllophones(getAllophones(language, "l"));
            letterL.setUsageCount(-1);
            letters.add(letterL);
            
            Letter letterM = new Letter();
            letterM.setLanguage(language);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setAllophones(getAllophones(language, "m"));
            letterM.setUsageCount(-1);
            letters.add(letterM);
            
            Letter letterN = new Letter();
            letterN.setLanguage(language);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setAllophones(getAllophones(language, "n"));
            letterN.setUsageCount(-1);
            letters.add(letterN);
            
            Letter letterÑ = new Letter();
            letterÑ.setLanguage(language);
            letterÑ.setTimeLastUpdate(Calendar.getInstance());
            letterÑ.setText("ñ");
            letterÑ.setAllophones(getAllophones(language, "ɲ"));
            letterÑ.setUsageCount(-1);
            letters.add(letterÑ);
            
            // TODO: add support for 2-character letters?
//            Letter letterNg = new Letter();
//            letterNg.setLanguage(language);
//            letterNg.setTimeLastUpdate(Calendar.getInstance());
//            letterNg.setText("ng");
//            letterNg.setAllophones(getAllophones(language, "ŋ"));
//            letterNg.setUsageCount(-1);
//            letters.add(letterNg);
            
            Letter letterO = new Letter();
            letterO.setLanguage(language);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setAllophones(getAllophones(language, "ɔ"));
            letterO.setUsageCount(-1);
            letters.add(letterO);
            
            Letter letterP = new Letter();
            letterP.setLanguage(language);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setAllophones(getAllophones(language, "p"));
            letterP.setUsageCount(-1);
            letters.add(letterP);
            
            Letter letterQ = new Letter();
            letterQ.setLanguage(language);
            letterQ.setTimeLastUpdate(Calendar.getInstance());
            letterQ.setText("q");
            letterQ.setAllophones(getAllophones(language, "k"));
            letterQ.setUsageCount(-1);
            letters.add(letterQ);
            
            Letter letterR = new Letter();
            letterR.setLanguage(language);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setAllophones(getAllophones(language, "ɾ"));
            letterR.setUsageCount(-1);
            letters.add(letterR);
            
            Letter letterS = new Letter();
            letterS.setLanguage(language);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setAllophones(getAllophones(language, "s")); // TODO: can also be /z/
            letterS.setUsageCount(-1);
            letters.add(letterS);
            
            Letter letterT = new Letter();
            letterT.setLanguage(language);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setAllophones(getAllophones(language, "t"));
            letterT.setUsageCount(-1);
            letters.add(letterT);
            
            Letter letterU = new Letter();
            letterU.setLanguage(language);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setAllophones(getAllophones(language, "u"));
            letterU.setUsageCount(-1);
            letters.add(letterU);
            
            Letter letterV = new Letter();
            letterV.setLanguage(language);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setAllophones(getAllophones(language, "v"));
            letterV.setUsageCount(-1);
            letters.add(letterV);
            
            Letter letterW = new Letter();
            letterW.setLanguage(language);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setAllophones(getAllophones(language, "w"));
            letterW.setUsageCount(-1);
            letters.add(letterW);
            
            Letter letterX = new Letter();
            letterX.setLanguage(language);
            letterX.setTimeLastUpdate(Calendar.getInstance());
            letterX.setText("x");
            letterX.setAllophones(getAllophones(language, "k", "s"));
            letterX.setUsageCount(-1);
            letters.add(letterX);
            
            Letter letterY = new Letter();
            letterY.setLanguage(language);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setAllophones(getAllophones(language, "j"));
            letterY.setUsageCount(-1);
            letters.add(letterY);
            
            Letter letterZ = new Letter();
            letterZ.setLanguage(language);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setAllophones(getAllophones(language, "z")); // TODO: can also be /s/
            letterZ.setUsageCount(-1);
            letters.add(letterZ);
            
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLanguage(language);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(language, "ɑ"));
            letterAUpperCase.setUsageCount(-1);
            letters.add(letterAUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLanguage(language);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(language, "b"));
            letterBUpperCase.setUsageCount(-1);
            letters.add(letterBUpperCase);
            
            Letter letterCUpperCase = new Letter();
            letterCUpperCase.setLanguage(language);
            letterCUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterCUpperCase.setText("C");
            letterCUpperCase.setAllophones(getAllophones(language, "k")); // TODO: can also be /s/
            letterCUpperCase.setUsageCount(-1);
            letters.add(letterCUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLanguage(language);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(language, "d"));
            letterDUpperCase.setUsageCount(-1);
            letters.add(letterDUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLanguage(language);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(language, "ɛ"));
            letterEUpperCase.setUsageCount(-1);
            letters.add(letterEUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLanguage(language);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(language, "f"));
            letterFUpperCase.setUsageCount(-1);
            letters.add(letterFUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLanguage(language);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(language, "g")); // TODO: can also be /dʒ/ and /h/
            letterGUpperCase.setUsageCount(-1);
            letters.add(letterGUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLanguage(language);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(language, "h"));
            letterHUpperCase.setUsageCount(-1);
            letters.add(letterHUpperCase);
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLanguage(language);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(language, "i"));
            letterIUpperCase.setUsageCount(-1);
            letters.add(letterIUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLanguage(language);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(language, "dʒ")); // TODO: can also be /h/
            letterJUpperCase.setUsageCount(-1);
            letters.add(letterJUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLanguage(language);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(language, "k"));
            letterKUpperCase.setUsageCount(-1);
            letters.add(letterKUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLanguage(language);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(language, "l"));
            letterLUpperCase.setUsageCount(-1);
            letters.add(letterLUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLanguage(language);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(language, "m"));
            letterMUpperCase.setUsageCount(-1);
            letters.add(letterMUpperCase);
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLanguage(language);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(language, "n"));
            letterNUpperCase.setUsageCount(-1);
            letters.add(letterNUpperCase);
            
            Letter letterÑUpperCase = new Letter();
            letterÑUpperCase.setLanguage(language);
            letterÑUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterÑUpperCase.setText("Ñ");
            letterÑUpperCase.setAllophones(getAllophones(language, "ɲ"));
            letterÑUpperCase.setUsageCount(-1);
            letters.add(letterÑUpperCase);
            
            // TODO: add support for 2-character letters?
//            Letter letterNgUpperCase = new Letter();
//            letterNgUpperCase.setLanguage(language);
//            letterNgUpperCase.setTimeLastUpdate(Calendar.getInstance());
//            letterNgUpperCase.setText("NG");
//            letterNgUpperCase.setAllophones(getAllophones(language, "ŋ"));
//            letterNgUpperCase.setUsageCount(-1);
//            letters.add(letterNgUpperCase);
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLanguage(language);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(language, "ɔ"));
            letterOUpperCase.setUsageCount(-1);
            letters.add(letterOUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLanguage(language);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(language, "p"));
            letterPUpperCase.setUsageCount(-1);
            letters.add(letterPUpperCase);
            
            Letter letterQUpperCase = new Letter();
            letterQUpperCase.setLanguage(language);
            letterQUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterQUpperCase.setText("Q");
            letterQUpperCase.setAllophones(getAllophones(language, "k"));
            letterQUpperCase.setUsageCount(-1);
            letters.add(letterQUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLanguage(language);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(language, "ɾ"));
            letterRUpperCase.setUsageCount(-1);
            letters.add(letterRUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLanguage(language);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(language, "s")); // TODO: can also be /z/
            letterSUpperCase.setUsageCount(-1);
            letters.add(letterSUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLanguage(language);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(language, "t"));
            letterTUpperCase.setUsageCount(-1);
            letters.add(letterTUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLanguage(language);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(language, "u"));
            letterUUpperCase.setUsageCount(-1);
            letters.add(letterUUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLanguage(language);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(language, "v"));
            letterVUpperCase.setUsageCount(-1);
            letters.add(letterVUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLanguage(language);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(language, "w"));
            letterWUpperCase.setUsageCount(-1);
            letters.add(letterWUpperCase);
            
            Letter letterXUpperCase = new Letter();
            letterXUpperCase.setLanguage(language);
            letterXUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterXUpperCase.setText("X");
            letterXUpperCase.setAllophones(getAllophones(language, "k", "s"));
            letterXUpperCase.setUsageCount(-1);
            letters.add(letterXUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLanguage(language);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(language, "j"));
            letterYUpperCase.setUsageCount(-1);
            letters.add(letterYUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLanguage(language);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(language, "z")); // TODO: can also be /s/
            letterZUpperCase.setUsageCount(-1);
            letters.add(letterZUpperCase);
        } else if (language == Language.SW) {
            Letter letterA = new Letter();
            letterA.setLanguage(language);
            letterA.setTimeLastUpdate(Calendar.getInstance());
            letterA.setText("a");
            letterA.setAllophones(getAllophones(language, "ɑ"));
            letterA.setUsageCount(994);
            letters.add(letterA);
            
            Letter letterI = new Letter();
            letterI.setLanguage(language);
            letterI.setTimeLastUpdate(Calendar.getInstance());
            letterI.setText("i");
            letterI.setAllophones(getAllophones(language, "i"));
            letterI.setUsageCount(608);
            letters.add(letterI);
            
            Letter letterU = new Letter();
            letterU.setLanguage(language);
            letterU.setTimeLastUpdate(Calendar.getInstance());
            letterU.setText("u");
            letterU.setAllophones(getAllophones(language, "u"));
            letterU.setUsageCount(393);
            letters.add(letterU);
            
            Letter letterN = new Letter();
            letterN.setLanguage(language);
            letterN.setTimeLastUpdate(Calendar.getInstance());
            letterN.setText("n");
            letterN.setAllophones(getAllophones(language, "n"));
            letterN.setUsageCount(334);
            letters.add(letterN);
            
            Letter letterK = new Letter();
            letterK.setLanguage(language);
            letterK.setTimeLastUpdate(Calendar.getInstance());
            letterK.setText("k");
            letterK.setAllophones(getAllophones(language, "k"));
            letterK.setUsageCount(305);
            letters.add(letterK);
            
            Letter letterE = new Letter();
            letterE.setLanguage(language);
            letterE.setTimeLastUpdate(Calendar.getInstance());
            letterE.setText("e");
            letterE.setAllophones(getAllophones(language, "ɛ"));
            letterE.setUsageCount(270);
            letters.add(letterE);
            
            Letter letterL = new Letter();
            letterL.setLanguage(language);
            letterL.setTimeLastUpdate(Calendar.getInstance());
            letterL.setText("l");
            letterL.setAllophones(getAllophones(language, "l"));
            letterL.setUsageCount(269);
            letters.add(letterL);
            
            Letter letterM = new Letter();
            letterM.setLanguage(language);
            letterM.setTimeLastUpdate(Calendar.getInstance());
            letterM.setText("m");
            letterM.setAllophones(getAllophones(language, "m"));
            letterM.setUsageCount(224);
            letters.add(letterM);
            
            Letter letterW = new Letter();
            letterW.setLanguage(language);
            letterW.setTimeLastUpdate(Calendar.getInstance());
            letterW.setText("w");
            letterW.setAllophones(getAllophones(language, "w"));
            letterW.setUsageCount(190);
            letters.add(letterW);
            
            Letter letterO = new Letter();
            letterO.setLanguage(language);
            letterO.setTimeLastUpdate(Calendar.getInstance());
            letterO.setText("o");
            letterO.setAllophones(getAllophones(language, "ɔ"));
            letterO.setUsageCount(159);
            letters.add(letterO);
            
            Letter letterB = new Letter();
            letterB.setLanguage(language);
            letterB.setTimeLastUpdate(Calendar.getInstance());
            letterB.setText("b");
            letterB.setAllophones(getAllophones(language, "ɓ"));
            letterB.setUsageCount(130);
            letters.add(letterB);
            
            Letter letterS = new Letter();
            letterS.setLanguage(language);
            letterS.setTimeLastUpdate(Calendar.getInstance());
            letterS.setText("s");
            letterS.setAllophones(getAllophones(language, "s"));
            letterS.setUsageCount(110);
            letters.add(letterS);
            
            Letter letterT = new Letter();
            letterT.setLanguage(language);
            letterT.setTimeLastUpdate(Calendar.getInstance());
            letterT.setText("t");
            letterT.setAllophones(getAllophones(language, "t"));
            letterT.setUsageCount(108);
            letters.add(letterT);
            
            Letter letterH = new Letter();
            letterH.setLanguage(language);
            letterH.setTimeLastUpdate(Calendar.getInstance());
            letterH.setText("h");
            letterH.setAllophones(getAllophones(language, "h"));
            letterH.setUsageCount(97);
            letters.add(letterH);
            
            Letter letterG = new Letter();
            letterG.setLanguage(language);
            letterG.setTimeLastUpdate(Calendar.getInstance());
            letterG.setText("g");
            letterG.setAllophones(getAllophones(language, "ɠ"));
            letterG.setUsageCount(96);
            letters.add(letterG);
            
            Letter letterR = new Letter();
            letterR.setLanguage(language);
            letterR.setTimeLastUpdate(Calendar.getInstance());
            letterR.setText("r");
            letterR.setAllophones(getAllophones(language, "ɾ"));
            letterR.setUsageCount(52);
            letters.add(letterR);
            
            Letter letterD = new Letter();
            letterD.setLanguage(language);
            letterD.setTimeLastUpdate(Calendar.getInstance());
            letterD.setText("d");
            letterD.setAllophones(getAllophones(language, "ɗ"));
            letterD.setUsageCount(80);
            letters.add(letterD);
            
            Letter letterY = new Letter();
            letterY.setLanguage(language);
            letterY.setTimeLastUpdate(Calendar.getInstance());
            letterY.setText("y");
            letterY.setAllophones(getAllophones(language, "j"));
            letterY.setUsageCount(77);
            letters.add(letterY);
            
            Letter letterP = new Letter();
            letterP.setLanguage(language);
            letterP.setTimeLastUpdate(Calendar.getInstance());
            letterP.setText("p");
            letterP.setAllophones(getAllophones(language, "p"));
            letterP.setUsageCount(64);
            letters.add(letterP);
            
            Letter letterZ = new Letter();
            letterZ.setLanguage(language);
            letterZ.setTimeLastUpdate(Calendar.getInstance());
            letterZ.setText("z");
            letterZ.setAllophones(getAllophones(language, "z"));
            letterZ.setUsageCount(64);
            letters.add(letterZ);
            
            Letter letterJ = new Letter();
            letterJ.setLanguage(language);
            letterJ.setTimeLastUpdate(Calendar.getInstance());
            letterJ.setText("j");
            letterJ.setAllophones(getAllophones(language, "ʄ"));
            letterJ.setUsageCount(39);
            letters.add(letterJ);
            
            Letter letterF = new Letter();
            letterF.setLanguage(language);
            letterF.setTimeLastUpdate(Calendar.getInstance());
            letterF.setText("f");
            letterF.setAllophones(getAllophones(language, "f"));
            letterF.setUsageCount(23);
            letters.add(letterF);
            
//            // TODO: replace 'c' with 'ch'?
//            Letter letterC = new Letter();
//            letterC.setLanguage(language);
//            letterC.setTimeLastUpdate(Calendar.getInstance());
//            letterC.setText("c");
//            letterC.setAllophones(getAllophones(language, "tʃ"));
//            letterC.setUsageCount(21);
//            letters.add(letterC);
            
            Letter letterV = new Letter();
            letterV.setLanguage(language);
            letterV.setTimeLastUpdate(Calendar.getInstance());
            letterV.setText("v");
            letterV.setAllophones(getAllophones(language, "v"));
            letterV.setUsageCount(17);
            letters.add(letterV);
            
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLanguage(language);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(language, "n"));
            letterNUpperCase.setUsageCount(43);
            letters.add(letterNUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLanguage(language);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(language, "m"));
            letterMUpperCase.setUsageCount(34);
            letters.add(letterMUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLanguage(language);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(language, "p"));
            letterPUpperCase.setUsageCount(15);
            letters.add(letterPUpperCase);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLanguage(language);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(language, "ɑ"));
            letterAUpperCase.setUsageCount(13);
            letters.add(letterAUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLanguage(language);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(language, "t"));
            letterTUpperCase.setUsageCount(13);
            letters.add(letterTUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLanguage(language);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(language, "ɓ"));
            letterBUpperCase.setUsageCount(12);
            letters.add(letterBUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLanguage(language);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(language, "ɛ"));
            letterEUpperCase.setUsageCount(12);
            letters.add(letterEUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLanguage(language);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(language, "k"));
            letterKUpperCase.setUsageCount(12);
            letters.add(letterKUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLanguage(language);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(language, "s"));
            letterSUpperCase.setUsageCount(11);
            letters.add(letterSUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLanguage(language);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(language, "u"));
            letterUUpperCase.setUsageCount(10);
            letters.add(letterUUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLanguage(language);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(language, "w"));
            letterWUpperCase.setUsageCount(10);
            letters.add(letterWUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLanguage(language);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(language, "f"));
            letterFUpperCase.setUsageCount(9);
            letters.add(letterFUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLanguage(language);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(language, "l"));
            letterLUpperCase.setUsageCount(8);
            letters.add(letterLUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLanguage(language);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(language, "h"));
            letterHUpperCase.setUsageCount(7);
            letters.add(letterHUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLanguage(language);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(language, "ʄ"));
            letterJUpperCase.setUsageCount(6);
            letters.add(letterJUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLanguage(language);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(language, "ɗ"));
            letterDUpperCase.setUsageCount(4);
            letters.add(letterDUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLanguage(language);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(language, "j"));
            letterYUpperCase.setUsageCount(3);
            letters.add(letterYUpperCase);
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLanguage(language);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(language, "i"));
            letterIUpperCase.setUsageCount(2);
            letters.add(letterIUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLanguage(language);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(language, "ɠ"));
            letterGUpperCase.setUsageCount(1);
            letters.add(letterGUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLanguage(language);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(language, "ɾ"));
            letterRUpperCase.setUsageCount(1);
            letters.add(letterRUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLanguage(language);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(language, "v"));
            letterVUpperCase.setUsageCount(1);
            letters.add(letterVUpperCase);
            
            // TODO: replace 'C' with 'CH'?
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLanguage(language);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(language, "ɔ"));
            letterOUpperCase.setUsageCount(0);
            letters.add(letterOUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLanguage(language);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(language, "z"));
            letterZUpperCase.setUsageCount(0);
            letters.add(letterZUpperCase);
        }
        
        return letters;
    }
    
    /**
     * As an example, the letter 'x' is represented by two Allophones; /k/ and /s/. So these two would be combined into 
     * /ks/.
     */
    private List<Allophone> getAllophones(Language language, String... ipaValues) {
        List<Allophone> allophones = new ArrayList<>();
        
        for (String ipaValue : ipaValues) {
            Allophone allophone = allophoneDao.readByValueIpa(language, ipaValue);
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
