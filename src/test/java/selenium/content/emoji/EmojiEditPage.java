package selenium.content.emoji;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmojiEditPage {
    
    private WebDriver driver;

    public EmojiEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("emojiEditPage"));
    }
}
