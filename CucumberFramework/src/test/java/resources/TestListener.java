package resources;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestListener
		extends TestListenerAdapter {

	// Please update OUTPUT_FORMAT if message over 20 chars
	public static final String SUCCESS_MESSAGE = "Test Passed";
	public static final String FAILURE_MESSAGE = "Test Failed";
	public static final String SKIPPED_MESSAGE = "Test Skipped";

	public static final String CLASS_STARTED_MESSAGE = "Test Batch Start";
	public static final String CLASS_FINISHED_MESSAGE = "Test Batch Complete";

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
	public static final String OUTPUT_FORMAT = "\n%s : %-25s : %-40s";
	public static final String LINEBREAK = "\n===================================================================";

	private WebDriver driver;

	/**
	 * When a test class is started output time and class name
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onStart (ITestContext textConext) {
		Date date = new Date ();
		String s = String.format (OUTPUT_FORMAT, DATE_FORMAT.format (date.getTime ()), CLASS_STARTED_MESSAGE, textConext.getName ());
		s += LINEBREAK;
		log (s);
		Reporter.log (CLASS_STARTED_MESSAGE + LINEBREAK + "\n");
	}

	/**
	 * On a class finshed simply output a linebreak
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onFinish (ITestContext textConext) {
		Date date = new Date ();
		String s = String.format (OUTPUT_FORMAT, DATE_FORMAT.format (date.getTime ()), CLASS_FINISHED_MESSAGE, textConext.getName ());
		s += LINEBREAK;
		log (s);
		Reporter.log (CLASS_FINISHED_MESSAGE + LINEBREAK + "\n");
	}

	/**
	 * Output time, test name and test failure message
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure (ITestResult testResult) {
		Date date = new Date ();
		String s = String.format (OUTPUT_FORMAT, DATE_FORMAT.format (date.getTime ()), FAILURE_MESSAGE, testResult.getName ());
		log (s);
		driver = Browser.getInstance ();
		Reporter.log (testResult.getThrowable ().getMessage ());
		log (testResult.getThrowable ().getMessage ());

		/*
		 * Grab screenshot
		 */
		try {
			driver = Browser.getInstance ();
			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs (OutputType.FILE);

			DateFormat dateFormat = new SimpleDateFormat ("dd_MMM_yyyy__hh_mm_ssaa");
			String destFile = "./screenshots" + testResult.getName () + dateFormat.format (new Date ()) + ".png";
			// new File(Config.SCREENSHOT_DIR).mkdirs();

			try {
				FileUtils.copyFile (screenshotFile, new File (destFile));
			}
			catch (IOException e) {
				Reporter.log ("Failed to save screenshot.");
				Reporter.log (e.getMessage ());
			}
			Reporter.setEscapeHtml (false);
			Reporter.log ("Saved <a href=../screenshots/" + destFile + " >Screenshot</a>");
		}
		catch (NoSuchWindowException e) {
			Reporter.log ("Unable to save screenshot as new window closed prematurely.");
		}

	}

	/**
	 * Output time, test name and test skipped message, also saves a screenshot of browser
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped (ITestResult testResult) {
		Date date = new Date ();
		String s = String.format (OUTPUT_FORMAT, DATE_FORMAT.format (date.getTime ()), SKIPPED_MESSAGE, testResult.getName ());
		log (s);
	}

	/**
	 * Output time, test name and test success message
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess (ITestResult testResult) {
		Date date = new Date ();
		String s = String.format (OUTPUT_FORMAT, DATE_FORMAT.format (date.getTime ()), SUCCESS_MESSAGE, testResult.getName ());
		log (s);
	}

	/*
	 * Print to standard output and testng report
	 * 
	 */
	private void log (String s) {
		System.out.print (s + "\n");
		Reporter.log (s + "\n");
	}
}
