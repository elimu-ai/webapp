package selenium.web.admin.project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.ErrorHelper;

public class ProjectListPage {

	  private WebDriver driver;
	    
	    @FindBy(className = "project")
	    private WebElement projectListItem;

	    public ProjectListPage(WebDriver driver) {
	        this.driver = driver;
	        
	        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
	        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("adminProjectListPage")));
	        
	        ErrorHelper.verifyNoScriptOrMarkupError(driver);
	    }
	    
	    public Boolean checkIfListItemIsEmpty() {
	    	return projectListItem.isDisplayed();
	    }
}
