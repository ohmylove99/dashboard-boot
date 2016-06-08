package org.octopus.selenium;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium2 {

	public static final int DEFAULT_WAIT_TIME = 20;
	private final WebDriver driver;
	private final String baseUrl;

	public Selenium2(WebDriver driver, String baseUrl) {
		this.driver = driver;
		this.baseUrl = baseUrl;
		setTimeout(DEFAULT_WAIT_TIME);
	}

	public Selenium2(WebDriver driver) {
		this(driver, "");
	}

	public void setStopAtShutdown() {
		Runtime.getRuntime().addShutdownHook(new Thread("Selenium Quit Hook") {
			@Override
			public void run() {
				quit();
			}
		});
	}

	public void open(String url) {
		final String urlToOpen = url.indexOf("://") == -1 ? baseUrl + (!url.startsWith("/") ? "/" : "") + url : url;
		driver.get(urlToOpen);
	}

	public String getLocation() {
		return driver.getCurrentUrl();
	}

	public void back() {
		driver.navigate().back();
	}

	public void refresh() {
		driver.navigate().refresh();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void quit() {
		try {
			driver.quit();
		} catch (Exception e) {
			System.err.println("Error happen while quit selenium :" + e.getMessage());
		}
	}

	public void setTimeout(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isVisible(By by) {
		return driver.findElement(by).isDisplayed();
	}

	public void type(By by, String text) {
		WebElement element = driver.findElement(by);
		element.clear();
		element.sendKeys(text);
	}

	public void click(By by) {
		driver.findElement(by).click();
	}

	public void check(By by) {
		WebElement element = driver.findElement(by);
		check(element);
	}

	public void check(WebElement element) {
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheck(By by) {
		WebElement element = driver.findElement(by);
		uncheck(element);
	}

	public void uncheck(WebElement element) {
		if (element.isSelected()) {
			element.click();
		}
	}

	public boolean isChecked(By by) {
		WebElement element = driver.findElement(by);
		return isChecked(element);
	}

	public boolean isChecked(WebElement element) {
		return element.isSelected();
	}

	public Select getSelect(By by) {
		return new Select(driver.findElement(by));
	}

	public String getText(By by) {
		return driver.findElement(by).getText();
	}

	public String getValue(By by) {
		return getValue(driver.findElement(by));
	}

	public String getValue(WebElement element) {
		return element.getAttribute("value");
	}

	public void snapshot(String basePath, String outputFileName) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File targetFile = new File(basePath, outputFileName);
		try {
			FileUtils.copyFile(srcFile, targetFile);
		} catch (IOException ioe) {
		}
	}

	public void waitForTitleIs(String title) {
		waitForCondition(ExpectedConditions.titleIs(title), DEFAULT_WAIT_TIME);
	}

	public void waitForTitleIs(String title, int timeout) {
		waitForCondition(ExpectedConditions.titleIs(title), timeout);
	}

	public void waitForTitleContains(String title) {
		waitForCondition(ExpectedConditions.titleContains(title), DEFAULT_WAIT_TIME);
	}

	public void waitForTitleContains(String title, int timeout) {
		waitForCondition(ExpectedConditions.titleContains(title), timeout);
	}

	public void waitForVisible(By by) {
		waitForCondition(ExpectedConditions.visibilityOfElementLocated(by), DEFAULT_WAIT_TIME);
	}

	public void waitForVisible(By by, int timeout) {
		waitForCondition(ExpectedConditions.visibilityOfElementLocated(by), timeout);
	}

	public void waitForTextPresent(By by, String text) {
		waitForCondition(ExpectedConditions.textToBePresentInElementLocated(by, text), DEFAULT_WAIT_TIME);
	}

	public void waitForTextPresent(By by, String text, int timeout) {
		waitForCondition(ExpectedConditions.textToBePresentInElementLocated(by, text), timeout);
	}

	public void waitForValuePresent(By by, String value) {
		waitForCondition(ExpectedConditions.textToBePresentInElementValue(by, value), DEFAULT_WAIT_TIME);
	}

	public void waitForValuePresent(By by, String value, int timeout) {
		waitForCondition(ExpectedConditions.textToBePresentInElementValue(by, value), timeout);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void waitForCondition(ExpectedCondition conditon, int timeout) {
		(new WebDriverWait(driver, timeout)).until(conditon);
	}

	public boolean isTextPresent(String text) {
		String bodyText = driver.findElement(By.tagName("body")).getText();
		return bodyText.contains(text);
	}

	public String getTable(WebElement table, int rowIndex, int columnIndex) {
		return table.findElement(By.xpath("//tr[" + (rowIndex + 1) + "]//td[" + (columnIndex + 1) + "]")).getText();
	}

	public String getTable(By by, int rowIndex, int columnIndex) {
		return getTable(driver.findElement(by), rowIndex, columnIndex);
	}

}
