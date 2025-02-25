package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/delete")
@RequiredArgsConstructor
@Slf4j
public class NumberDeleteController {

  private final NumberDao numberDao;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(Model model, @PathVariable Long id) {
    log.info("handleRequest");

//        Number number = numberDao.read(id);
//        numberDao.delete(number);

    return "redirect:/content/number/list";
  }
}
