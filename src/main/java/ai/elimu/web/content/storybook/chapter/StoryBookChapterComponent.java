package ai.elimu.web.content.storybook.chapter;

import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class StoryBookChapterComponent {

    private final Logger logger = LogManager.getLogger();

    public void checkEditorRole(HttpSession session) {
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor.getRoles(): " + contributor.getRoles());
        if (!contributor.getRoles().contains(Role.EDITOR)) {
            // TODO: return HttpStatus.FORBIDDEN
            throw new IllegalAccessError("Missing role for access");
        }
    }

}
