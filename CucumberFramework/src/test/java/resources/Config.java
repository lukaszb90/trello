package resources;

public class Config {

	/*
	 * WebDriver driver locations
	 */
	public static final String CHROME_DRIVER_LOCATION = "./drivers/chromedriver.exe";
	public static final String FIREFOX_BROWSER_LOCATION = "./drivers/geckodriver.exe"; 
	
	// This will only be used if the browser option parameter is not set in the
	// testng.xml file
	public static final String DEFAULT_BROSWER_OPTION = "1";

	/*
	 * Testng suite constants
	 */
	public static final int TIMEOUT = 15;
	public static final int VISIBILTY_TIMEOUT = 15;
}