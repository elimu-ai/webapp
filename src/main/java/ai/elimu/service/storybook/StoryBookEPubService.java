package ai.elimu.service.storybook;

import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class StoryBookEPubService {

    public boolean isTableOfContentsFileHtmlLike(String fileName) {
        if (isNull(fileName) || fileName.isBlank()) {
            return false;
        }

        return fileName.endsWith(".xhtml") || fileName.endsWith(".html");
    }

}
