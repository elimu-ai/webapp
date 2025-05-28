package selenium.analytics.students;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StudentPage {
    
    private WebDriver driver;

    public StudentPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("studentPage"));
    }
}
