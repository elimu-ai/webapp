package selenium.contributor;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import lombok.extern.slf4j.Slf4j;
import selenium.util.ErrorHelper;

@Slf4j
public class ContributorListPage {

    private WebDriver driver;

    public ContributorListPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("contributorListPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }

    public void pressRandomContributor() {
        List<WebElement> contributorLinks = driver.findElements(By.className("contributorCardImageLink"));
        log.info("contributorLinks.size(): " + contributorLinks.size());
        int randomIndex = (int) (Math.random() * contributorLinks.size());
        log.info("randomIndex: " + randomIndex);
        WebElement contributorLink = contributorLinks.get(randomIndex);
        contributorLink.click();
    }
}
