package selenium.analytics.students;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.util.ErrorHelper;

public class StudentListPage {
    
    private WebDriver driver;

    public StudentListPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("studentListPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }

    public void pressRandomStudent() {
        List<WebElement> links = driver.findElements(By.className("studentLink"));
        int randomIndex = (int) (Math.random() * links.size());
        WebElement randomLink = links.get(randomIndex);
        randomLink.click();
    }
}
