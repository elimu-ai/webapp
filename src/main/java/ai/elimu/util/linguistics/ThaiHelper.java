package ai.elimu.util.linguistics;

import java.text.BreakIterator;
import java.util.Locale;

public class ThaiHelper {

    public static String splitIntoWords(String paragraph) {
        BreakIterator wordIterator = BreakIterator.getWordInstance(new Locale("th"));
        wordIterator.setText(paragraph);

        String words = "";
        int start = wordIterator.first();
        int end = wordIterator.next();
        while (end != BreakIterator.DONE) {
            if (words.length() > 0) {
                words += " ";
            }
            words += paragraph.substring(start, end);
            start = end;
            end = wordIterator.next();
        }
        return words;
    }
}
