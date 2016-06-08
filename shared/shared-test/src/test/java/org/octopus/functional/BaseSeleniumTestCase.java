package org.octopus.functional;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.octopus.selenium.Selenium2;
import org.octopus.selenium.SeleniumSnapshotRule;
import org.octopus.selenium.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BaseSeleniumTestCase extends BaseFunctionalTestCase {

	protected static Selenium2 s;


	@Rule
	public TestRule snapshotRule = new SeleniumSnapshotRule(s);

	@BeforeClass
	public static void initSelenium() throws Exception {
		createSeleniumOnce();
		loginAsUserIfNecessary();
	}

	protected static void createSeleniumOnce() throws Exception {
		if (s == null) {
			String driverName = propertiesLoader.getProperty("selenium.driver");

			WebDriver driver = WebDriverFactory.createDriver(driverName);

			s = new Selenium2(driver, baseUrl);
			s.setStopAtShutdown();
		}
	}


	protected static void loginAsUserIfNecessary() {
		s.open("/task");

		if (s.getTitle().contains("")) {
			s.type(By.name("username"), "user");
			s.type(By.name("password"), "user");
			s.check(By.name("rememberMe"));
			s.click(By.id("submit_btn"));
			s.waitForTitleContains("");
		}
	}
}
