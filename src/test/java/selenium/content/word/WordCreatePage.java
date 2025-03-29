package selenium.content.word;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WordCreatePage {
    
    private WebDriver driver;

    public WordCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("wordCreatePage"));
    }
}
