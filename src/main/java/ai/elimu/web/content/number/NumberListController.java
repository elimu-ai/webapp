package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Number;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/number/list")
@RequiredArgsConstructor
public class NumberListController {
    
    private final Logger logger = LogManager.getLogger();
    
    private NumberDao numberDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    List<Number> numbers = numberDao.readAllOrdered();
    model.addAttribute("numbers", numbers);

    return "content/number/list";
  }
}
