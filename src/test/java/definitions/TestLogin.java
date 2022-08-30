package definitions;

import java.time.Duration;

import org.openqa.selenium.remote.RemoteWebDriver;

import io.cucumber.java.en.*;
import runner.TestRunner;
import pom.PageLogin;

public class TestLogin extends TestRunner {

	String errMsg;
	PageLogin login;
	public RemoteWebDriver driver = this.conn;
	
	@Given("browser is open")
	public void browser_is_open() {
		// Handled in TestRunner
	}

	@And("user is on login page")
	public void user_is_on_login_page() {
		// Handled in TestRunner
	}

    @And("^user is logged in as (.*) with (.*)$")
    public void user_is_logged_in_as_(String userName, String passCode)  {
		login = new PageLogin(driver);

		login.loginValidUser(userName, passCode);
    }
    
	@And("^user enters (.*) and (.*)$")
	public void user_enters_and(String userName, String passCode) throws Exception {
		login = new PageLogin(driver);

		login.enterUsername(userName);
		login.enterPassword(passCode);
	}

	@And("user clicks on login button")
	public void clicks_on_login_button() throws InterruptedException {
		login.clickLogin();
		Thread.sleep(2000); // cater for Safari not respecting selenium wait commands
	}

	@Then("error message is displayed")
	public void error_message_is_displayed() throws Exception {
		if (!(login.errMessage())) {
			throw new Exception("+++ No error message shown.");
		}
	}

	@Then("user is navigated to home page")
	public void user_is_navigated_to_home_page() throws Exception {
		if (!(login.isHomePage())) {
			throw new Exception("+++ User is not on home page");
		}
	}

}
