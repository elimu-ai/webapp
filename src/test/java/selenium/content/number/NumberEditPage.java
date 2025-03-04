package selenium.content.number;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NumberEditPage {
    
    private WebDriver driver;

    public NumberEditPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("numberEditPage"));
    }
}
