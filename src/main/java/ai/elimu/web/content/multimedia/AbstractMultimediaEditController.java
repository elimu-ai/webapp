package ai.elimu.web.content.multimedia;

import ai.elimu.dao.GenericDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Multimedia;
import ai.elimu.model.enums.Locale;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jakub Ruzicka on 5. 10. 2018.
 */
public abstract class AbstractMultimediaEditController<T extends Multimedia> extends AbstractMultimediaController{

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private GenericDao<T> genericDao;

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private WordDao wordDao;

    @Autowired
    private NumberDao numberDao;


    @RequestMapping(value = "/{id}/add-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleAddContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
        logger.info("handleAddContentLabelRequest");

        logger.info("id: " + id);
        T multimedia = genericDao.read(id);

        String letterIdParameter = request.getParameter("letterId");
        logger.info("letterIdParameter: " + letterIdParameter);
        if (StringUtils.isNotBlank(letterIdParameter)) {
            Long letterId = Long.valueOf(letterIdParameter);
            Letter letter = letterDao.read(letterId);
            Set<Letter> letters = multimedia.getLetters();
            if (!letters.contains(letter)) {
                letters.add(letter);
                multimedia.setRevisionNumber(multimedia.getRevisionNumber() + 1);
                genericDao.update(multimedia);
            }
        }

        String numberIdParameter = request.getParameter("numberId");
        logger.info("numberIdParameter: " + numberIdParameter);

        if (StringUtils.isNotBlank(numberIdParameter)) {
            Long numberId = Long.valueOf(numberIdParameter);
            Number number = numberDao.read(numberId);
            Set<Number> numbers = multimedia.getNumbers();
            if (!numbers.contains(number)) {
                numbers.add(number);
                multimedia.setRevisionNumber(multimedia.getRevisionNumber() + 1);
                genericDao.update(multimedia);
            }
        }

        String wordIdParameter = request.getParameter("wordId");
        logger.info("wordIdParameter: " + wordIdParameter);
        if (StringUtils.isNotBlank(wordIdParameter)) {
            Long wordId = Long.valueOf(wordIdParameter);
            Word word = wordDao.read(wordId);
            Set<Word> words = multimedia.getWords();
            if (!words.contains(word)) {
                words.add(word);
                multimedia.setRevisionNumber(multimedia.getRevisionNumber() + 1);
                genericDao.update(multimedia);
            }
        }

        return "success";
    }

    @RequestMapping(value = "/{id}/remove-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleRemoveContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
        logger.info("handleRemoveContentLabelRequest");

        logger.info("id: " + id);
        T multimedia = genericDao.read(id);

        String letterIdParameter = request.getParameter("letterId");
        logger.info("letterIdParameter: " + letterIdParameter);

        if (StringUtils.isNotBlank(letterIdParameter)) {
            Long letterId = Long.valueOf(letterIdParameter);
            Letter letter = letterDao.read(letterId);
            Set<Letter> letters = multimedia.getLetters();
            Iterator<Letter> iterator = letters.iterator();
            while (iterator.hasNext()) {
                Letter existingLetter = iterator.next();
                if (existingLetter.getId().equals(letter.getId())) {
                    iterator.remove();
                }
            }
            multimedia.setRevisionNumber(multimedia.getRevisionNumber() + 1);
            genericDao.update(multimedia);
        }

        String numberIdParameter = request.getParameter("numberId");
        logger.info("numberIdParameter: " + numberIdParameter);
        if (StringUtils.isNotBlank(numberIdParameter)) {
            Long numberId = Long.valueOf(numberIdParameter);
            Number number = numberDao.read(numberId);
            Set<Number> numbers = multimedia.getNumbers();
            Iterator<Number> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                Number existingNumber = iterator.next();
                if (existingNumber.getId().equals(number.getId())) {
                    iterator.remove();
                }
            }
            multimedia.setRevisionNumber(multimedia.getRevisionNumber() + 1);
            genericDao.update(multimedia);
        }

        String wordIdParameter = request.getParameter("wordId");
        logger.info("wordIdParameter: " + wordIdParameter);
        if (StringUtils.isNotBlank(wordIdParameter)) {
            Long wordId = Long.valueOf(wordIdParameter);
            Word word = wordDao.read(wordId);
            Set<Word> words = multimedia.getWords();
            Iterator<Word> iterator = words.iterator();
            while (iterator.hasNext()) {
                Word existingWord = iterator.next();
                if (existingWord.getId().equals(word.getId())) {
                    iterator.remove();
                }
            }
            multimedia.setRevisionNumber(multimedia.getRevisionNumber() + 1);
            genericDao.update(multimedia);
        }

        return "success";
    }
    protected void addLettersNumbersWords(Model model, Locale locale){
        model.addAttribute("letters", letterDao.readAllOrdered(locale));
        model.addAttribute("numbers", numberDao.readAllOrdered(locale));
        model.addAttribute("words", wordDao.readAllOrdered(locale));
    }


}
