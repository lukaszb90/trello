package steps;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import resources.BaseSetup;
import resources.Browser;

public class RegisterAccountSteps extends BaseSetup{

	@Test(priority = 3, description = "???")
	public void aaa() throws InterruptedException {
		driver.get("https://trello.com/");
		Thread.sleep(5000);
	}
	

	@AfterSuite()
	public void tearDown ()
			throws Exception {
		System.out.println ("LOGGER: cleaning up");
		Browser.closeBrowser ();
	}

}
