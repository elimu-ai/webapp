package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/number/delete/{id}")
@RequiredArgsConstructor
@Slf4j
public class NumberDeleteController {

  private final NumberDao numberDao;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id) {
    log.info("handleRequest");

//        Number number = numberDao.read(id);
//        numberDao.delete(number);

    return "redirect:/content/number/list";
  }
}
