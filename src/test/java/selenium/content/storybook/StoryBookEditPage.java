package selenium.content.storybook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoryBookEditPage {
    
    private WebDriver driver;

    public StoryBookEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("storyBookEditPage"));
    }
}
