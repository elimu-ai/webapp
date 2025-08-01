package ai.elimu.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.davidashen.text.Hyphenator;
import net.davidashen.util.ErrorHandler;
import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.Language;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyllableHelper {
    
    /**
     * Example (English): "chicken" --> ["chick","en"]
     */
    public static List<String> getSyllables(Word word, Language language) {
        List<String> syllables = new ArrayList<>();
        
        if (language == Language.ENG) {
            String hyphenatedWord = getHyphenatedWord(word.getText());
            log.debug("hyphenatedWord: " + hyphenatedWord);
            String[] syllableArray = hyphenatedWord.split("­");
            for (String syllable : syllableArray) {
                syllables.add(syllable.toLowerCase());
            }
            
            // TODO: extract Consonants/Vocals
            // TODO: split Consonants/Vocals into syllables
            // TODO: convert syllables into letters
        }
        
        // TODO: add support for other languages
        
        return syllables;
    }
    
    private static String getHyphenatedWord(String text) {
        Hyphenator hyphenator = new Hyphenator();
        hyphenator.setErrorHandler(new ErrorHandler() {

            @Override
            public void debug(String string, String string1) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void info(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void warning(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void error(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void exception(String string, Exception excptn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        URL url = SyllableHelper.class.getResource("hyphen.tex");
        File file = new File(url.getFile());
        try {
            hyphenator.loadTable(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        
        return hyphenator.hyphenate(text);
    }
}
