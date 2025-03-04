package selenium.content.letter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LetterCreatePage {
    
    private WebDriver driver;

    public LetterCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("letterCreatePage"));
    }
}
