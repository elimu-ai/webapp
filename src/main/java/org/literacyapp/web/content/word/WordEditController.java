package org.literacyapp.web.content.word;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.dao.AudioDao;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.dao.WordRevisionEventDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.content.multimedia.Audio;
import org.literacyapp.model.content.multimedia.Image;
import org.literacyapp.model.contributor.WordRevisionEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.model.enums.content.WordType;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/edit")
public class WordEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private WordRevisionEventDao wordRevisionEventDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id, HttpSession session) {
    	logger.info("handleRequest");
        
        Word word = wordDao.read(id);
        model.addAttribute("word", word);
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        model.addAttribute("allophones", allophones);
        
        model.addAttribute("wordTypes", WordType.values());
        
        model.addAttribute("wordRevisionEvents", wordRevisionEventDao.readAll(word));
        
        Audio audio = audioDao.read(word.getText(), contributor.getLocale());
        model.addAttribute("audio", audio);
        
        // Look up Multimedia content that has been labeled with this Word
        List<Image> labeledImages = imageDao.readAllLabeled(word, contributor.getLocale());
        // TODO: labeled Audios
        model.addAttribute("labeledImages", labeledImages);
        // TODO: labeled Videos

        return "content/word/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Word word,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
    	logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByText(word.getLocale(), word.getText());
        if ((existingWord != null) && !existingWord.getId().equals(word.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        
        // Verify that only valid Allophones are used
        String allAllophonesCombined = "";
        for (Allophone allophone : allophones) {
            allAllophonesCombined += allophone.getValueIpa();
        }
        if (StringUtils.isNotBlank(word.getPhonetics())) {
            for (char allophoneCharacter : word.getPhonetics().toCharArray()) {
                String allophone = String.valueOf(allophoneCharacter);
                if (!allAllophonesCombined.contains(allophone)) {
                    result.rejectValue("phonetics", "Invalid");
                    break;
                }
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("allophones", allophones);
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("wordRevisionEvents", wordRevisionEventDao.readAll(word));
            Audio audio = audioDao.read(word.getText(), contributor.getLocale());
            model.addAttribute("audio", audio);
            return "content/word/edit";
        } else {
            if (!"I".equals(word.getText())) {
                word.setText(word.getText().toLowerCase());
            }
            word.setTimeLastUpdate(Calendar.getInstance());
            word.setRevisionNumber(word.getRevisionNumber() + 1);
            wordDao.update(word);
            
            WordRevisionEvent wordRevisionEvent = new WordRevisionEvent();
            wordRevisionEvent.setContributor(contributor);
            wordRevisionEvent.setCalendar(Calendar.getInstance());
            wordRevisionEvent.setWord(word);
            wordRevisionEvent.setText(word.getText());
            wordRevisionEvent.setPhonetics(word.getPhonetics());
            if (StringUtils.isNotBlank(request.getParameter("comment"))) {
                wordRevisionEvent.setComment(request.getParameter("comment"));
            }
            wordRevisionEventDao.create(wordRevisionEvent);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                    contributor.getFirstName() + " just updated a Word:\n" + 
                    "• Language: \"" + word.getLocale().getLanguage() + "\"\n" +  
                    "• Text: \"" + word.getText() + "\"\n" + 
                    "• Phonetics (IPA): /" + word.getPhonetics() + "/\n" + 
                    "• Word type: " + word.getWordType() + "\n" + 
                    "• Comment: \"" + wordRevisionEvent.getComment() + "\"\n" +         
                    "See ") + "http://literacyapp.org/content/word/edit/" + word.getId();
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
            }
            
            return "redirect:/content/word/list#" + word.getId();
        }
    }
}
