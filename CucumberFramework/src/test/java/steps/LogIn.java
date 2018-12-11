package steps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import resources.Browser;

public class LogIn extends RegisterAccountSteps {

	@Test(priority = 16, description = "Navigate to Trello site")
	public void navigateToTrelloSite() throws InterruptedException {
		RegisterAccountSteps.navigateToTrelloWebsite();
	}
	
	@Test(priority = 17, description = "Try to log in withempty fields")
	public static void logInWithEmptyFields () {
		driver.findElement(By.cssSelector(SelectorsResources.LOG_IN_BUTTON)).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(SelectorsResources.ERROR_MESSAGE)).isDisplayed());
	}
	
	@Test(priority = 18, description = "Try to log in with not existing e-mail adress")
	public static void logInWithWrongEmail () throws InterruptedException {
		driver.findElement(By.cssSelector(SelectorsResources.USER_INPUT)).sendKeys("unexisting"+RegisterAccountSteps.number+"@gmail.com");
		driver.findElement(By.cssSelector(SelectorsResources.LOG_IN_BUTTON)).click();
		Thread.sleep(1000);
		String errorMsg = driver.findElement(By.cssSelector(SelectorsResources.ERROR_MESSAGE)).getText().trim();
		Assert.assertEquals("There isn't an account for this email", errorMsg);
	}
	
	@Test(priority = 19, description = "Try to log in with incorrect password")
	public static void logInWithWrongPassword () throws InterruptedException {
		driver.findElement(By.cssSelector(SelectorsResources.PASSWORD)).sendKeys("wrongPassword");
		WebElement userInput = driver.findElement(By.cssSelector(SelectorsResources.USER_INPUT));
		userInput.clear();
		userInput.sendKeys(RegisterAccountSteps.eMail);
		driver.findElement(By.cssSelector(SelectorsResources.LOG_IN_BUTTON)).click();
		Thread.sleep(1000);
		String errorMsg = driver.findElement(By.cssSelector(SelectorsResources.ERROR_MESSAGE)).getText();
		Assert.assertEquals("Invalid password", errorMsg);

	}

	@Test(priority = 20, description = "Log in")
	public static void logIn() {
		driver.findElement(By.cssSelector(SelectorsResources.USER_INPUT)).clear();
		driver.findElement(By.cssSelector(SelectorsResources.USER_INPUT)).sendKeys(RegisterAccountSteps.eMail);
		driver.findElement(By.cssSelector(SelectorsResources.PASSWORD)).clear();
		driver.findElement(By.cssSelector(SelectorsResources.PASSWORD)).sendKeys(RegisterAccountSteps.password);
		driver.findElement(By.cssSelector(SelectorsResources.LOG_IN_BUTTON)).click();

		// the loop is to workaround StaleElementException, try catch is good but this
		// one does not fail the test
		int loop = 0;
		boolean isInitailasDispalyed = true;
		while (loop > 2) {
			isInitailasDispalyed = driver.findElement(By.cssSelector(SelectorsResources.INITIALS_ICON)).isDisplayed();
			loop++;
		}
		Assert.assertTrue(isInitailasDispalyed);
	}

	@AfterSuite()
	public void tearDown() throws Exception {
		System.out.println("LOGGER: cleaning up");
		Browser.closeBrowser();
	}
}
