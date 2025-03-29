package selenium.content.number;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NumberListPage {
    
    private WebDriver driver;

    public NumberListPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("numberListPage"));
    }

    public void pressRandomNumber() {
        List<WebElement> links = driver.findElements(By.className("editLink"));
        int randomIndex = (int) (Math.random() * links.size());
        WebElement randomLink = links.get(randomIndex);
        randomLink.click();
    }

    public void pressCreateButton() {
        WebElement button = driver.findElement(By.id("createButton"));
        button.click();
    }
}
