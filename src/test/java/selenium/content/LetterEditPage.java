package selenium.content;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LetterEditPage {
    
    private WebDriver driver;

    public LetterEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("letterEditPage"));
    }
}
