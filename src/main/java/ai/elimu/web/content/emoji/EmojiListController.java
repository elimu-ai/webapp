package ai.elimu.web.content.emoji;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/emoji/list")
public class EmojiListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private EmojiDao emojiDao;

    @GetMapping
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<Emoji> emojis = emojiDao.readAllOrdered();
        model.addAttribute("emojis", emojis);

        return "content/emoji/list";
    }
}
