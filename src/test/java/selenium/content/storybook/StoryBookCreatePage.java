package selenium.content.storybook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoryBookCreatePage {
    
    private WebDriver driver;

    public StoryBookCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("storyBookCreatePage"));
    }
}
