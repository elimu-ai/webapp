package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Number;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/list")
@RequiredArgsConstructor
@Slf4j
public class NumberListController {

  private final NumberDao numberDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Number> numbers = numberDao.readAllOrdered();
    model.addAttribute("numbers", numbers);

    return "content/number/list";
  }
}
