package ai.elimu.web.content.emoji;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.EmojiDao;
import ai.elimu.entity.content.Emoji;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/emoji/list")
public class EmojiListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private EmojiDao emojiDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<Emoji> emojis = emojiDao.readAllOrdered();
        model.addAttribute("emojis", emojis);

        return "content/emoji/list";
    }
}
