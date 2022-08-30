package runner;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources/features/login.feature"
	, glue = {"definitions" }
	, monochrome = true
//	, tags = "@SmokeTest"
	, plugin = { "pretty"
//		, "junit:target/JUnitReports/report.xml"
//		, "json:target/JSONReports/report.json"
		, "json:target/JSONReports/cucumber.json"
		, "html:target/HtmlReport.html" })

public class TestRunner extends AbstractTestNGCucumberTests {
	private TestNGCucumberRunner testNGCucumberRunner;
	public static RemoteWebDriver conn;
	public static String testURL;
	
	@BeforeClass(alwaysRun = true)
	public void setUpCucumber() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser", "browser_version", "platform" })
	public void setUpClass(String browser, String browser_version, String platform) throws Exception {
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
		String username = "username";
		String accesskey = "accesskey";
		String gridURL = "@hub.lambdatest.com/wd/hub";
		String strDate = DateFor.format(date);
		String strEnv = System.getenv("LT_BUILD_NAME");

		// setup build options
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("browserVersion", browser_version);
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("platform", platform);
		ltOptions.put("projectName", "SeBDDTestNGLambdaTest");
		
		if (strEnv == null) {
			ltOptions.put("build", strDate);
		} else {
			ltOptions.put("build", strEnv);
		}

		capabilities.setCapability("LT:Options", ltOptions);

		conn = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);

		// URL may differ UAT, Staging or other env; may be passed in from command line
		if (System.getProperty("URL") == null) {
			testURL = "https://opensource-demo.orangehrmlive.com/";
		} else {
			testURL = System.getProperty("URL");
		}
		
		// Go to testURL
		conn.get(testURL);
		conn.manage().window().maximize();
		conn.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		conn.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		testNGCucumberRunner.finish();
	}

}