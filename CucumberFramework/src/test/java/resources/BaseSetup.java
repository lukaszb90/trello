package resources;

import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseSetup {

	protected static Reporter report;
	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static JavascriptExecutor js;

	/**
	 * Param browserOption 1 Chrome 2 Firefox 3
	 * 
	 * @param browserOption
	 * @throws Exception
	 */
	@Parameters({ "browserOption" })
	@BeforeSuite(alwaysRun = true)
	public void init(@Optional(Config.DEFAULT_BROSWER_OPTION) String browserOption) throws Exception {
		initializeWebdrivers();

		// Start browser, user Browser.getInstance() all other times to set browser

		browserOption = JOptionPane
				.showInputDialog("Please select a web browser. " + "\n 1. \t Chrome" + "\n 2. \t Firefox");

		int option = Integer.parseInt(browserOption);
		driver = Browser.getInstance(option);

		driver.manage().timeouts().implicitlyWait(Config.VISIBILTY_TIMEOUT, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, Config.TIMEOUT);

		// Check browser can execute javascript
		if (driver instanceof JavascriptExecutor) {
			js = (JavascriptExecutor) driver;
		} else {
			Exception e = new Exception("Browser is not capable of running javascript");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Initializes the webdrivers.
	 */
	public void initializeWebdrivers() {
		System.setProperty("webdriver.chrome.driver", Config.CHROME_DRIVER_LOCATION);
		System.setProperty("webdriver.gecko.driver", Config.FIREFOX_BROWSER_LOCATION);
	}

	/**
	 * Runs after the suite is complete. Cleans ups after tests.
	 * 
	 * @throws Exception
	 * 
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		System.out.println("LOGGER: cleaning up");
		Browser.closeBrowser();
	}
}
