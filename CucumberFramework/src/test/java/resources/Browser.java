package resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Browser {

	private static Browser instance = null;
	private static WebDriver driver;
	DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer ();

	/**
	 * Constructor
	 **/
	protected Browser () {

		instance = this;
	}

	/**
	 * Specify which browser to use
	 * 
	 * @param option first char of browser to use
	 */
	protected Browser (int option) {

		switch (option) {
			case 1:
				Browser.setDriver (new ChromeDriver ());
				driver.manage ().window ().maximize ();
				break;

			case 2:	
				Browser.setDriver (new FirefoxDriver ());
				driver.manage ().window ().maximize ();
				break;

			default:
				System.out.println ("LOGGER: Option not found");
				System.out.println ("LOGGER: Using default browser Chrome");
				Browser.setDriver (new ChromeDriver ());
				driver.manage ().window ().maximize ();
				break;
		}

		instance = this;
	}

	/**
	 * Get Singleton Browser Instance
	 *
	 * @return Browser Instance
	 */
	public static WebDriver getInstance () {
		if (instance == null) {
			instance = new Browser ();
		}
		return Browser.getDriver ();
	}

	/**
	 * Get Singleton Browser Instance with option
	 *
	 * @return Browser Instance
	 */
	public static WebDriver getInstance (int option) {
		if (instance == null) {
			instance = new Browser (option);
		}
		return Browser.getDriver ();
	}

	/**
	 * Close browser in use
	 * 
	 * @throws Exception
	 */
	public static void closeBrowser ()
			throws Exception {
		try {

			if (getDriver () != null) {
				getDriver ().quit ();
			}

		}
		catch (Exception e) {
			throw e;
		}
	}

	public static WebDriver getDriver () {
		return driver;
	}

	public static void setDriver (WebDriver driver) {
		Browser.driver = driver;
	}
}
