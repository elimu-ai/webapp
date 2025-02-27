package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/number/delete/{id}")
@RequiredArgsConstructor
public class NumberDeleteController {

  private final Logger logger = LogManager.getLogger();

  private final NumberDao numberDao;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id) {
    logger.info("handleRequest");

//        Number number = numberDao.read(id);
//        numberDao.delete(number);

    return "redirect:/content/number/list";
  }
}
