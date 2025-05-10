package ai.elimu.web.analytics.students;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.WordLearningEvent;
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

  private final WordLearningEventDao wordLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    // Generate Student IDs for pre-existing learning events
    for (WordLearningEvent wordLearningEvent : wordLearningEventDao.readAll()) {
      log.info("wordLearningEvent.getAndroidId(): " + wordLearningEvent.getAndroidId());
      Student existingStudent = studentDao.read(wordLearningEvent.getAndroidId());
      if (existingStudent == null) {
        Student student = new Student();
        student.setAndroidId(wordLearningEvent.getAndroidId());
        studentDao.create(student);
        log.info("Stored Student in database with ID " + student.getId());
      }
    }
    for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEventDao.readAll()) {
      log.info("storyBookLearningEvent.getAndroidId(): " + storyBookLearningEvent.getAndroidId());
      Student existingStudent = studentDao.read(storyBookLearningEvent.getAndroidId());
      if (existingStudent == null) {
        Student student = new Student();
        student.setAndroidId(storyBookLearningEvent.getAndroidId());
        studentDao.create(student);
        log.info("Stored Student in database with ID " + student.getId());
      }
    }
    
    List<Student> students = studentDao.readAll();
    for (Student student : students) {
      student.setAndroidId(AnalyticsHelper.redactAndroidId(student.getAndroidId()));
    }
    model.addAttribute("students", students);

    return "analytics/students/list";
  }
}
