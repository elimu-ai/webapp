package selenium.content.word;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WordEditPage {
    
    private WebDriver driver;

    public WordEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("wordEditPage"));
    }
}
