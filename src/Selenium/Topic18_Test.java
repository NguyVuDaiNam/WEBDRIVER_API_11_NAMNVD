package Selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic18_Test {
	WebDriver driver;
	WebDriverWait waitExplicit;
	JavascriptExecutor javascript;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		waitExplicit = new WebDriverWait(driver, 30);
		javascript = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_Jquery() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");

		selectCustomDropdownList("//span[@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "19");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='19']")).isDisplayed());
		Thread.sleep(3000);

	}

//23123123123
	@Test
	public void TC_02_Angular() throws Exception {
		driver.get("https://material.angular.io/components/select/examples");
		selectCustomDropdownList("//label/mat-label[text()='State']", "//mat-option", "Wisconsin");
		Assert.assertTrue(driver.findElement(By.xpath("//span[contains(text(),'Wisconsin')]")).isDisplayed());
		Thread.sleep(3000);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void selectCustomDropdownList(String parentXpath, String childXpath, String valueExpected) throws Exception {
		// click vao de mo dropdown ra
		WebElement parent = driver.findElement(By.xpath(parentXpath));
		// parent.click();
		javascript.executeScript("arguments[0].click();", parent);
		// wait cho tat ca cac item duoc hien thi
		List<WebElement> child = driver.findElements(By.xpath(childXpath));
		waitExplicit.until(ExpectedConditions.visibilityOfAllElements(child));

		// get text tat ca cac item ra va kiem tra bang gia tri mong muon hay ko
		for (WebElement childItem : child) {
			if (childItem.getText().equals(valueExpected)) {
				// scroll den item can chon
				javascript.executeScript("arguments[0].scrollIntoView(true);", childItem);
				Thread.sleep(1000);
				// click vao item nay
				childItem.click();
				break;
			}
		}

	}

}