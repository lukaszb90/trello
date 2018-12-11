package steps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import resources.BaseSetup;
import resources.Browser;

public class RegisterAccountSteps extends BaseSetup {

	static int number = (int) Math.ceil(Math.random() * 50000);
	static String userName = "Name" + number;
	static String eMail = "mail" + number + "@wp.pl";
	static String password = "Passw0rd1";

	@Test(priority = 3, description = "Navigate to Trello website")
	public static void navigateToTrelloWebsite() throws InterruptedException {
		driver.get("https://trello.com/");
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(SelectorsResources.TRELLO_LOGO))));
		Assert.assertEquals("Trello", driver.getTitle());
	}

	@Test(priority = 4, description = "Close GDPR bar if present")
	public static void closeGDPR() {
		try {
			WebElement gdprButton = driver.findElement(By.className("gdpr-cookie-consent-button"));
			gdprButton.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// TODO: handle exception
		}
	}

	@Test(priority = 5, description = "Switch langauge to English")
	public static void switchLanguageToEnglish() {
		Select selectLanguage = new Select(driver.findElement(By.cssSelector(SelectorsResources.SELECT_LANGUAGE)));
		selectLanguage.selectByVisibleText("English");
	}

	@Test(priority = 6, description = "Click on sign up")
	public static void clickOnSignUp() throws InterruptedException {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/signup?returnUrl=%2Fen']")));
		driver.findElement(By.cssSelector("a[href*='/signup?returnUrl=%2Fen']")).click();
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.cssSelector(SelectorsResources.SIGN_IN_LINK_ON_CREATE)));
		Thread.sleep(2000);
	}

	@Test(priority = 7, description = "Try to create account o empty fields")
	// this method will be used on each step to check if the create button become
	// active
	public static void createAccountOnEmptyField() {
		WebElement createButton = driver.findElement(By.cssSelector(SelectorsResources.CREATE_NEW_ACCOUNT));
		Assert.assertFalse(createButton.isEnabled());
	}

	@Test(priority = 8, description = "Enter name")
	public static void enterName() {
		WebElement name = driver.findElement(By.cssSelector(SelectorsResources.NAME_INPUT));
		name.click();
		name.sendKeys(userName);
		createAccountOnEmptyField();
	}

	@Test(priority = 9, description = "Enter invaild e-mail")
	public static void enterInvalidEmail() throws InterruptedException {
		WebElement email = driver.findElement(By.cssSelector(SelectorsResources.EMAIL_INPUT));
		email.click();
		email.sendKeys("invalid@localhost.com");
		WebElement passwordInput = driver.findElement(By.cssSelector(SelectorsResources.PASSWORD_INPUT));
		passwordInput.click();
		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.cssSelector(SelectorsResources.INVALID_EMAIL_MESSAGE)).isDisplayed());
	}

	@Test(priority = 10, description = "Enter e-mail")
	public static void enterEMail() {
		WebElement email = driver.findElement(By.cssSelector(SelectorsResources.EMAIL_INPUT));
		email.click();
		email.clear();
		email.sendKeys(eMail);
		createAccountOnEmptyField();
	}

	@Test(priority = 11, description = "Enter invalid password")
	public static void enterInvalidPassword() throws InterruptedException {
		WebElement passwordInput = driver.findElement(By.cssSelector(SelectorsResources.PASSWORD_INPUT));
		passwordInput.click();
		passwordInput.sendKeys("1");
		WebElement createButton = driver.findElement(By.cssSelector(SelectorsResources.CREATE_NEW_ACCOUNT));
		createButton.click();
		Thread.sleep(2000);
		Assert.assertTrue(
				driver.findElement(By.cssSelector(SelectorsResources.INCORRECT_PASSWORD_MESSAGE)).isDisplayed());
	}

	@Test(priority = 12, description = "Enter password")
	public static void enterPassword() {
		WebElement passwordInput = driver.findElement(By.cssSelector(SelectorsResources.PASSWORD_INPUT));
		passwordInput.click();
		passwordInput.clear();
		passwordInput.sendKeys(password);
	}

	@Test(priority = 13, description = "Check if google button is invisible")
	public static void gogleButtonInvisible() {
		WebElement googleButton = driver.findElement(By.cssSelector(SelectorsResources.GOOGLE_BUTTON));
		Assert.assertFalse(googleButton.isDisplayed());
	}

	@Test(priority = 14, description = "Click on create new account button")
	public static void clickOnCreateNewAccountButton() {
		WebElement createButton = driver.findElement(By.cssSelector(SelectorsResources.CREATE_NEW_ACCOUNT));
		createButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("global-header-section-logo-image")));
		try {
			driver.findElement(By.className("global-header-section-button")).click();
		} catch (Exception e) {
			driver.findElement(By.partialLinkText("Go to your boards.")).click();
		}
		try {
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.cssSelector(SelectorsResources.BOARD_NAME_INPUT)));
			driver.findElement(By.cssSelector(SelectorsResources.BOARD_NAME_INPUT)).sendKeys("Test_Board" + number);
			Thread.sleep(200);
			WebElement arrow = driver.findElement(By.cssSelector(SelectorsResources.CONTINUE_ARROW));
			arrow.click();
			Thread.sleep(200);
			arrow.click();
			Thread.sleep(200);
			driver.findElement(By.cssSelector(SelectorsResources.FIRST_INPUT_BOARD)).sendKeys("TEST1");
			arrow.click();
			Thread.sleep(200);
			driver.findElement(By.cssSelector(SelectorsResources.NOW_YOU_ARE_PRO_BUTTON)).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SelectorsResources.LOGOUT)));
		} catch (Exception ex) {

		}
	}

	@Test(priority = 15, description = "Logout")
	public static void logout() {
		driver.findElement(By.cssSelector(SelectorsResources.INITIALS_ICON)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SelectorsResources.LOGOUT)));
		driver.findElement(By.cssSelector(SelectorsResources.LOGOUT)).click();
	}

	@AfterSuite()
	public void tearDown() throws Exception {
		System.out.println("LOGGER: cleaning up");
		Browser.closeBrowser();
	}

}
