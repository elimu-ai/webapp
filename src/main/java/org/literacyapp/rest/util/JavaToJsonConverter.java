package org.literacyapp.rest.util;

import org.literacyapp.model.Word;
import org.literacyapp.model.json.NumberJson;
import org.literacyapp.model.json.WordJson;

public class JavaToJsonConverter {

    public static NumberJson getNumberJson(org.literacyapp.model.Number number) {
        if (number == null) {
            return null;
        } else {
            NumberJson numberJson = new NumberJson();
            numberJson.setId(number.getId());
            numberJson.setLocale(number.getLocale());
            numberJson.setValue(number.getValue());
            numberJson.setSymbol(number.getSymbol());
            numberJson.setWord(getWordJson(number.getWord()));
            numberJson.setDominantColor(number.getDominantColor());
            return numberJson;
        }
    }
    
    public static WordJson getWordJson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordJson wordJson = new WordJson();
            wordJson.setId(word.getId());
            wordJson.setLocale(word.getLocale());
            wordJson.setText(word.getText());
            return wordJson;
        }
    }
}
