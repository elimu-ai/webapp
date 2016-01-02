package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AjaxHelper {

    private final static int TIMOUT_IN_SECONDS = 10;

    public static void waitForElement(final By by, final WebDriver webDriver) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, TIMOUT_IN_SECONDS);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
