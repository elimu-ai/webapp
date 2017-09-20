package selenium.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import selenium.DomainHelper;

import selenium.ScreenshotOnFailureRule;

public class WelcomePageTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() { 	
    	driver = new FirefoxDriver();    		
        driver.get(DomainHelper.getBaseUrl());
    }

    @Test
    public void testWelcomePage() {
    	WelcomePage welcomePage = PageFactory.initElements(driver, WelcomePage.class);
    }
}
