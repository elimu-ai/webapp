package ai.elimu.web.content.emoji;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/emoji/create")
public class EmojiCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Emoji emoji = new Emoji();
        model.addAttribute("emoji", emoji);
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        model.addAttribute("allophones", allophones);

        return "content/emoji/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Emoji emoji,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Emoji existingEmoji = emojiDao.readByGlyph(emoji.getGlyph());
        if (existingEmoji != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("emoji", emoji);
            
            List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
            model.addAttribute("allophones", allophones);
            
            return "content/emoji/create";
        } else {
            emoji.setTimeLastUpdate(Calendar.getInstance());
            emojiDao.create(emoji);
            
            return "redirect:/content/emoji/list#" + emoji.getId();
        }
    }
}
