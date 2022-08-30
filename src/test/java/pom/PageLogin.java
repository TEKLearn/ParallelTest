package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PageLogin {

	protected RemoteWebDriver driver;
	private By txt_username = By.name("username");
	private By txt_password = By.name("password");
	private By btn_login = By.xpath("//button");
	private By txt_errMsg = By.xpath("//p[contains(.,'Invalid credentials')]"); // error message 
	private By str_homepage = By.xpath("//h6[contains(.,'PIM')]"); // Home page is PIM
	
	public PageLogin(RemoteWebDriver conn) {
		this.driver = conn;

		if (!driver.getTitle().equals("OrangeHRM")) {
			throw new IllegalStateException(
					"=== This is not Login Page. The Current Page is " + driver.getCurrentUrl());
		};
	}

	public void enterUsername(String username) {
		driver.findElement(txt_username).sendKeys(username);
	}

	public void enterPassword(String password) {
		driver.findElement(txt_password).sendKeys(password);
	}

	public void clickLogin() {
		driver.findElement(btn_login).click();
	}

	public void loginValidUser(String username, String password) {
		driver.findElement(txt_username).sendKeys(username);
		driver.findElement(txt_password).sendKeys(password);
		driver.findElement(btn_login).click();
	}

	public boolean errMessage() {
		return driver.findElement(txt_errMsg).isDisplayed();
	}
	
	// Page after a successful login
	public boolean isHomePage() throws InterruptedException {
//		return false;
		return driver.findElement(str_homepage).isDisplayed();
	
	}
}
