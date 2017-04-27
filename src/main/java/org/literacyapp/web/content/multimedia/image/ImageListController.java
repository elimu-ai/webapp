package org.literacyapp.web.content.multimedia.image;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.content.multimedia.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/image/list")
public class ImageListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // Label Images with Words of matching title
        // TODO: move to ImageCreateController
        List<Image> images = imageDao.readAllOrdered(contributor.getLocale());
        for (Image image : images) {
            Word matchingWord = wordDao.readByText(contributor.getLocale(), image.getTitle());
            if (matchingWord != null) {
                Set<Word> labeledWords = image.getWords();
                if (!labeledWords.contains(matchingWord)) {
                    labeledWords.add(matchingWord);
                    image.setWords(labeledWords);
                    imageDao.update(image);
                }
            }
        }
        
        images = imageDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("images", images);

        return "content/multimedia/image/list";
    }
}
