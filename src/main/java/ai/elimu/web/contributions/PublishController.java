package ai.elimu.web.contributions;

import ai.elimu.model.v2.enums.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/contributions/publish")
@Slf4j
public class PublishController {

    @GetMapping
    public String handleRequest(Model model) {
        log.info("handleRequest");

        Language[] languages = Arrays.stream(Language.values())
                .filter(lang -> lang != Language.ENG)
                .toArray(Language[]::new);
        model.addAttribute("supportedLanguage", languages);

        return "contributions/publish";
    }
}