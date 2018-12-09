package steps;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import resources.BaseSetup;
import resources.Browser;

public class RegisterAccountSteps extends BaseSetup{

	@Test(priority = 3, description = "Navigate to Trello website")
	public void navigateToTrelloWebsite() throws InterruptedException {
		driver.get("https://trello.com/");
		try {
		WebElement gdprButton  = driver.findElement(By.className("gdpr-cookie-consent-button"));
		gdprButton.click();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
		Thread.sleep(2000);
		Select selectLanguage = new Select (driver.findElement(By.cssSelector(SelectorsResources.SELECT_LANGUAGE)));
		selectLanguage.selectByVisibleText("English");
		Assert.assertEquals("Trello", driver.getTitle());
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SelectorsResources.SIGN_UP_IT_IS_FREE)));
		WebElement creteNewLink = driver.findElement(By.cssSelector(SelectorsResources.SIGN_UP_LINK));
		wait.until(ExpectedConditions.visibilityOf(creteNewLink));
		creteNewLink.click();
		
	}
	

	@AfterSuite()
	public void tearDown ()
			throws Exception {
		System.out.println ("LOGGER: cleaning up");
		Browser.closeBrowser ();
	}

}
