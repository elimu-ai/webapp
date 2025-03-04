package selenium.content.emoji;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmojiCreatePage {
    
    private WebDriver driver;

    public EmojiCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("emojiCreatePage"));
    }
}
