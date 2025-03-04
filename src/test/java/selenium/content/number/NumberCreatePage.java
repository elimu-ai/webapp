package selenium.content.number;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NumberCreatePage {
    
    private WebDriver driver;

    public NumberCreatePage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("numberCreatePage"));
    }
}
