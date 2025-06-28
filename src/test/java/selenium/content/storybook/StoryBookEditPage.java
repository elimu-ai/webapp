package selenium.content.storybook;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StoryBookEditPage {
    
    private WebDriver driver;

    public StoryBookEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("storyBookEditPage"));
    }

    public void pressRandomAutoFillWordLink() {
        List<WebElement> links = driver.findElements(By.className("autoFillWordLink"));
        int randomIndex = (int) (Math.random() * links.size());
        WebElement randomLink = links.get(randomIndex);
        randomLink.click();

        // Switch to the new window
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}
