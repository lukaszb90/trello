//
//package resources;
//
//import java.util.concurrent.TimeUnit;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Optional;
//import org.testng.annotations.Parameters;
//
//public class BrowserInitializer {
//
//	protected static WebDriver driver;
//	protected static WebDriverWait wait;
//
//	@Parameters({"browserOption"})
//	@BeforeSuite(alwaysRun = true)
//	public void init (@Optional(Config.DEFAULT_BROSWER_OPTION) String browserOption)
//			throws Exception {
//		System.out.println ("LOGGER: Initialising");
//		System.setProperty ("webdriver.chrome.driver", Config.CHROME_DRIVER_LOCATION);
//		System.setProperty ("webdriver.gecko.driver", Config.FIREFOX_BROWSER_LOCATION);
//
//		// Start browser, user Browser.getInstance() all other times to set browser
//		int option = Integer.parseInt (browserOption);
//		driver = Browser.getInstance (option);
//		driver.manage ().timeouts ().implicitlyWait (Config.VISIBILTY_TIMEOUT, TimeUnit.SECONDS);
//		wait = new WebDriverWait (driver, Config.TIMEOUT);
//		driver.manage ().window ().maximize ();
//	}
//
//	@AfterSuite()
//	public void tearDown ()
//			throws Exception {
//		System.out.println ("LOGGER: cleaning up");
//		Browser.closeBrowser ();
//	}
//}
