package selenium.analytics.students;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StudentPage {
    
    private WebDriver driver;

    public StudentPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("studentPage"));
    }

    public String getLetterSoundAssessmentEventsUrl() {
        WebElement exportToCsvButton = driver.findElement(By.id("exportLetterSoundAssessmentEventsToCsvButton"));
        return exportToCsvButton.getAttribute("href");
    }

    public String getLetterSoundLearningEventsUrl() {
        WebElement exportToCsvButton = driver.findElement(By.id("exportLetterSoundLearningEventsToCsvButton"));
        return exportToCsvButton.getAttribute("href");
    }
    
    public String getWordLearningEventsUrl() {
        WebElement exportToCsvButton = driver.findElement(By.id("exportWordLearningEventsToCsvButton"));
        return exportToCsvButton.getAttribute("href");
    }
}
