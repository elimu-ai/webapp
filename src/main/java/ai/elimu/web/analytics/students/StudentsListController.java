package ai.elimu.web.analytics.students;

import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.util.AnalyticsHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/students")
@RequiredArgsConstructor
@Slf4j
public class StudentsListController {

  private final StudentDao studentDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");
    
    List<Student> students = studentDao.readAll();
    for (Student student : students) {
      student.setAndroidId(AnalyticsHelper.redactAndroidId(student.getAndroidId()));
    }
    model.addAttribute("students", students);

    return "analytics/students/list";
  }
}
