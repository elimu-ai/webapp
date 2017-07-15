package ai.elimu.web.content.letter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.Contributor;
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
        
        List<String> letterStringArray = new ArrayList<>();
        if (locale == Locale.AR) {
            // TODO
        } else if (locale == Locale.EN) {
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
            letterIUpperCase.setBraille("⠊");
            letterIUpperCase.setUsageCount(193);
            letters.add(letterIUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLocale(locale);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(locale, "t"));
            letterTUpperCase.setBraille("⠞");
            letterTUpperCase.setUsageCount(110);
            letters.add(letterTUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLocale(locale);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(locale, "w"));
            letterWUpperCase.setBraille("⠺");
            letterWUpperCase.setUsageCount(71);
            letters.add(letterWUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLocale(locale);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(locale, "h"));
            letterHUpperCase.setBraille("⠓");
            letterHUpperCase.setUsageCount(64);
            letters.add(letterHUpperCase);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLocale(locale);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(locale, "æ"));
            letterAUpperCase.setBraille("⠁");
            letterAUpperCase.setUsageCount(57);
            letters.add(letterAUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLocale(locale);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(locale, "m"));
            letterMUpperCase.setBraille("⠍");
            letterMUpperCase.setUsageCount(53);
            letters.add(letterMUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLocale(locale);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(locale, "s"));
            letterSUpperCase.setBraille("⠎");
            letterSUpperCase.setUsageCount(52);
            letters.add(letterSUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLocale(locale);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(locale, "l"));
            letterLUpperCase.setBraille("⠇");
            letterLUpperCase.setUsageCount(43);
            letters.add(letterLUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLocale(locale);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(locale, "g"));
            letterGUpperCase.setBraille("⠛");
            letterGUpperCase.setUsageCount(30);
            letters.add(letterGUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLocale(locale);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(locale, "b"));
            letterBUpperCase.setBraille("⠃");
            letterBUpperCase.setUsageCount(29);
            letters.add(letterBUpperCase);
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLocale(locale);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(locale, "ɔ"));
            letterOUpperCase.setBraille("⠕");
            letterOUpperCase.setUsageCount(29);
            letters.add(letterOUpperCase);
            
            Letter letterCUpperCase = new Letter();
            letterCUpperCase.setLocale(locale);
            letterCUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterCUpperCase.setText("C");
            letterCUpperCase.setAllophones(getAllophones(locale, "k"));
            letterCUpperCase.setBraille("⠉");
            letterCUpperCase.setUsageCount(27);
            letters.add(letterCUpperCase);
            
            Letter letterNUpperCase = new Letter();
            letterNUpperCase.setLocale(locale);
            letterNUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterNUpperCase.setText("N");
            letterNUpperCase.setAllophones(getAllophones(locale, "n"));
            letterNUpperCase.setBraille("⠝");
            letterNUpperCase.setUsageCount(27);
            letters.add(letterNUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLocale(locale);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(locale, "d"));
            letterDUpperCase.setBraille("⠙");
            letterDUpperCase.setUsageCount(24);
            letters.add(letterDUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLocale(locale);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(locale, "k"));
            letterKUpperCase.setBraille("⠅");
            letterKUpperCase.setUsageCount(19);
            letters.add(letterKUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLocale(locale);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(locale, "p"));
            letterPUpperCase.setBraille("⠏");
            letterPUpperCase.setUsageCount(18);
            letters.add(letterPUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLocale(locale);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(locale, "ɛ"));
            letterEUpperCase.setBraille("⠑");
            letterEUpperCase.setUsageCount(17);
            letters.add(letterEUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLocale(locale);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(locale, "j"));
            letterYUpperCase.setBraille("⠽");
            letterYUpperCase.setUsageCount(17);
            letters.add(letterYUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLocale(locale);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(locale, "f"));
            letterFUpperCase.setBraille("⠋");
            letterFUpperCase.setUsageCount(4);
            letters.add(letterFUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLocale(locale);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(locale, "z"));
            letterZUpperCase.setBraille("⠵");
            letterZUpperCase.setUsageCount(3);
            letters.add(letterZUpperCase);
            
            Letter letterQUpperCase = new Letter();
            letterQUpperCase.setLocale(locale);
            letterQUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterQUpperCase.setText("Q");
            letterQUpperCase.setAllophones(getAllophones(locale, "q", "w"));
            letterQUpperCase.setBraille("⠟");
            letterQUpperCase.setUsageCount(2);
            letters.add(letterQUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLocale(locale);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(locale, "v"));
            letterVUpperCase.setBraille("⠧");
            letterVUpperCase.setUsageCount(2);
            letters.add(letterVUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLocale(locale);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(locale, "dʒ"));
            letterJUpperCase.setBraille("⠚");
            letterJUpperCase.setUsageCount(1);
            letters.add(letterJUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLocale(locale);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(locale, "ʌ"));
            letterUUpperCase.setBraille("⠥");
            letterUUpperCase.setUsageCount(1);
            letters.add(letterUUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLocale(locale);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(locale, "r"));
            letterRUpperCase.setBraille("⠗");
            letterRUpperCase.setUsageCount(0);
            letters.add(letterRUpperCase);
            
            Letter letterXUpperCase = new Letter();
            letterXUpperCase.setLocale(locale);
            letterXUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterXUpperCase.setText("X");
            letterXUpperCase.setAllophones(getAllophones(locale, "k", "s"));
            letterXUpperCase.setBraille("⠭");
            letterXUpperCase.setUsageCount(0);
            letters.add(letterXUpperCase);
        } else if (locale == Locale.ES) {
//            letterStringArray = new ArrayList<>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z"));
            
            // TODO
        } else if (locale == Locale.SW) {
//            letterStringArray = new ArrayList<>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","v","w","y","z"));
            
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
            letterNUpperCase.setBraille("⠝");
            letterNUpperCase.setUsageCount(43);
            letters.add(letterNUpperCase);
            
            Letter letterMUpperCase = new Letter();
            letterMUpperCase.setLocale(locale);
            letterMUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterMUpperCase.setText("M");
            letterMUpperCase.setAllophones(getAllophones(locale, "m"));
            letterMUpperCase.setBraille("⠍");
            letterMUpperCase.setUsageCount(34);
            letters.add(letterMUpperCase);
            
            Letter letterPUpperCase = new Letter();
            letterPUpperCase.setLocale(locale);
            letterPUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterPUpperCase.setText("P");
            letterPUpperCase.setAllophones(getAllophones(locale, "p"));
            letterPUpperCase.setBraille("⠏");
            letterPUpperCase.setUsageCount(15);
            letters.add(letterPUpperCase);
            
            Letter letterAUpperCase = new Letter();
            letterAUpperCase.setLocale(locale);
            letterAUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterAUpperCase.setText("A");
            letterAUpperCase.setAllophones(getAllophones(locale, "ɑ"));
            letterAUpperCase.setBraille("⠁");
            letterAUpperCase.setUsageCount(13);
            letters.add(letterAUpperCase);
            
            Letter letterTUpperCase = new Letter();
            letterTUpperCase.setLocale(locale);
            letterTUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterTUpperCase.setText("T");
            letterTUpperCase.setAllophones(getAllophones(locale, "t"));
            letterTUpperCase.setBraille("⠞");
            letterTUpperCase.setUsageCount(13);
            letters.add(letterTUpperCase);
            
            Letter letterBUpperCase = new Letter();
            letterBUpperCase.setLocale(locale);
            letterBUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterBUpperCase.setText("B");
            letterBUpperCase.setAllophones(getAllophones(locale, "ɓ"));
            letterBUpperCase.setBraille("⠃");
            letterBUpperCase.setUsageCount(12);
            letters.add(letterBUpperCase);
            
            Letter letterEUpperCase = new Letter();
            letterEUpperCase.setLocale(locale);
            letterEUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterEUpperCase.setText("E");
            letterEUpperCase.setAllophones(getAllophones(locale, "ɛ"));
            letterEUpperCase.setBraille("⠑");
            letterEUpperCase.setUsageCount(12);
            letters.add(letterEUpperCase);
            
            Letter letterKUpperCase = new Letter();
            letterKUpperCase.setLocale(locale);
            letterKUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterKUpperCase.setText("K");
            letterKUpperCase.setAllophones(getAllophones(locale, "k"));
            letterKUpperCase.setBraille("⠅");
            letterKUpperCase.setUsageCount(12);
            letters.add(letterKUpperCase);
            
            Letter letterSUpperCase = new Letter();
            letterSUpperCase.setLocale(locale);
            letterSUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterSUpperCase.setText("S");
            letterSUpperCase.setAllophones(getAllophones(locale, "s"));
            letterSUpperCase.setBraille("⠎");
            letterSUpperCase.setUsageCount(11);
            letters.add(letterSUpperCase);
            
            Letter letterUUpperCase = new Letter();
            letterUUpperCase.setLocale(locale);
            letterUUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterUUpperCase.setText("U");
            letterUUpperCase.setAllophones(getAllophones(locale, "u"));
            letterUUpperCase.setBraille("⠥");
            letterUUpperCase.setUsageCount(10);
            letters.add(letterUUpperCase);
            
            Letter letterWUpperCase = new Letter();
            letterWUpperCase.setLocale(locale);
            letterWUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterWUpperCase.setText("W");
            letterWUpperCase.setAllophones(getAllophones(locale, "w"));
            letterWUpperCase.setBraille("⠺");
            letterWUpperCase.setUsageCount(10);
            letters.add(letterWUpperCase);
            
            Letter letterFUpperCase = new Letter();
            letterFUpperCase.setLocale(locale);
            letterFUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterFUpperCase.setText("F");
            letterFUpperCase.setAllophones(getAllophones(locale, "f"));
            letterFUpperCase.setBraille("⠋");
            letterFUpperCase.setUsageCount(9);
            letters.add(letterFUpperCase);
            
            Letter letterLUpperCase = new Letter();
            letterLUpperCase.setLocale(locale);
            letterLUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterLUpperCase.setText("L");
            letterLUpperCase.setAllophones(getAllophones(locale, "l"));
            letterLUpperCase.setBraille("⠇");
            letterLUpperCase.setUsageCount(8);
            letters.add(letterLUpperCase);
            
            Letter letterHUpperCase = new Letter();
            letterHUpperCase.setLocale(locale);
            letterHUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterHUpperCase.setText("H");
            letterHUpperCase.setAllophones(getAllophones(locale, "h"));
            letterHUpperCase.setBraille("⠓");
            letterHUpperCase.setUsageCount(7);
            letters.add(letterHUpperCase);
            
            Letter letterJUpperCase = new Letter();
            letterJUpperCase.setLocale(locale);
            letterJUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterJUpperCase.setText("J");
            letterJUpperCase.setAllophones(getAllophones(locale, "ʄ"));
            letterJUpperCase.setBraille("⠚");
            letterJUpperCase.setUsageCount(6);
            letters.add(letterJUpperCase);
            
            Letter letterDUpperCase = new Letter();
            letterDUpperCase.setLocale(locale);
            letterDUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterDUpperCase.setText("D");
            letterDUpperCase.setAllophones(getAllophones(locale, "ɗ"));
            letterDUpperCase.setBraille("⠙");
            letterDUpperCase.setUsageCount(4);
            letters.add(letterDUpperCase);
            
            Letter letterYUpperCase = new Letter();
            letterYUpperCase.setLocale(locale);
            letterYUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterYUpperCase.setText("Y");
            letterYUpperCase.setAllophones(getAllophones(locale, "j"));
            letterYUpperCase.setBraille("⠽");
            letterYUpperCase.setUsageCount(3);
            letters.add(letterYUpperCase);
            
            Letter letterIUpperCase = new Letter();
            letterIUpperCase.setLocale(locale);
            letterIUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterIUpperCase.setText("I");
            letterIUpperCase.setAllophones(getAllophones(locale, "i"));
            letterIUpperCase.setBraille("⠊");
            letterIUpperCase.setUsageCount(2);
            letters.add(letterIUpperCase);
            
            Letter letterGUpperCase = new Letter();
            letterGUpperCase.setLocale(locale);
            letterGUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterGUpperCase.setText("G");
            letterGUpperCase.setAllophones(getAllophones(locale, "ɠ"));
            letterGUpperCase.setBraille("⠛");
            letterGUpperCase.setUsageCount(1);
            letters.add(letterGUpperCase);
            
            Letter letterRUpperCase = new Letter();
            letterRUpperCase.setLocale(locale);
            letterRUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterRUpperCase.setText("R");
            letterRUpperCase.setAllophones(getAllophones(locale, "ɾ"));
            letterRUpperCase.setBraille("⠗");
            letterRUpperCase.setUsageCount(1);
            letters.add(letterRUpperCase);
            
            Letter letterVUpperCase = new Letter();
            letterVUpperCase.setLocale(locale);
            letterVUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterVUpperCase.setText("V");
            letterVUpperCase.setAllophones(getAllophones(locale, "v"));
            letterVUpperCase.setBraille("⠧");
            letterVUpperCase.setUsageCount(1);
            letters.add(letterVUpperCase);
            
            // TODO: replace 'C' with 'CH' and/or 'Ch'?
            
            Letter letterOUpperCase = new Letter();
            letterOUpperCase.setLocale(locale);
            letterOUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterOUpperCase.setText("O");
            letterOUpperCase.setAllophones(getAllophones(locale, "ɔ"));
            letterOUpperCase.setBraille("⠕");
            letterOUpperCase.setUsageCount(0);
            letters.add(letterOUpperCase);
            
            Letter letterZUpperCase = new Letter();
            letterZUpperCase.setLocale(locale);
            letterZUpperCase.setTimeLastUpdate(Calendar.getInstance());
            letterZUpperCase.setText("Z");
            letterZUpperCase.setAllophones(getAllophones(locale, "z"));
            letterZUpperCase.setBraille("⠵");
            letterZUpperCase.setUsageCount(0);
            letters.add(letterZUpperCase);
        }
        
        return letters;
    }
    
    private List<Allophone> getAllophones(Locale locale, String... ipaValues) {
        List<Allophone> allophones = new ArrayList<>();
        
        for (String ipaValue : ipaValues) {
            Allophone allophone = allophoneDao.readByValueIpa(locale, ipaValue);
            allophones.add(allophone);
        }
        
        return allophones;
    }
}
