package selenium.contributor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.util.ErrorHelper;

public class ContributorListPage {

    private Logger logger = LogManager.getLogger();

    private WebDriver driver;

    public ContributorListPage(WebDriver driver) {
        this.driver = driver;

        driver.findElement(By.id("contributorListPage"));
        
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
    }

    public void pressRandomContributor() {
        List<WebElement> contributorLinks = driver.findElements(By.className("contributorCardImageLink"));
        logger.info("contributorLinks.size(): " + contributorLinks.size());
        int randomIndex = (int) (Math.random() * contributorLinks.size());
        logger.info("randomIndex: " + randomIndex);
        WebElement contributorLink = contributorLinks.get(randomIndex);
        contributorLink.click();
    }
}
