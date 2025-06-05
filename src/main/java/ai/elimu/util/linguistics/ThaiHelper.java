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

    /**
     * Replace ๆ with the word preceding it.
     * 
     * The Thai symbol ๆ is known as ไม้ยมก (máiyamok) and is used to denote the 
     * repetition of the previous word or phrase. For example, "บ่อย ๆ" (bɔ̀y bɔ̀y) 
     * means "often often" or simply "very often".
     * 
     * This method must be called <i>after</i> the {@link #splitIntoWords} method.
     */
    public static String replaceๆ(String paragraph) {
        // Add whitespace before the máiyamok: "บ่อยๆ" --> "บ่อย ๆ"
        paragraph = paragraph.replaceAll("ๆ", " ๆ");

        // Remove duplicate whitespaces
        paragraph = paragraph.replaceAll(" +", " ");

        // Replace the máiyamok with the word preceding it
        String[] words = paragraph.split(" ");
        for (int i = 0; i < words.length; i++) {
            if ("ๆ".equals(words[i])) {
                words[i] = words[i - 1];
            }
        }

        // Convert the string array back to a paragraph string
        return String.join(" ", words);
    }
}
