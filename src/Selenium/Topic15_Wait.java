package Selenium;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic15_Wait {
	WebDriver driver;
	WebDriverWait waitExplicit;
	By startButton = By.xpath("//div[@id='start']/button");
	By loadingIcon = By.xpath("//div[@id='loading']/img");
	By helloText = By.xpath("//div[@id='finish']/h4[text()='Hello World!']");

	@BeforeClass
	public void beforeClass() {

		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		}
		driver = new ChromeDriver();
		// driver = new FirefoxDriver();

		driver.manage().window().maximize();
		waitExplicit = new WebDriverWait(driver, 30);

	}

	public void TC_01_ImplicitWait() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");

		driver.findElement(startButton).click(); // sau khi click Start button thi mat 5s de render ra "Hello World"

		Assert.assertTrue(driver.findElement(helloText).isDisplayed());

	}

	public void TC_02_ExplicitWait() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		waitExplicit = new WebDriverWait(driver, 2);

		driver.findElement(startButton).click(); // sau khi click Start button thi mat 5s de render ra "Hello World"

		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		Assert.assertTrue(driver.findElement(helloText).isDisplayed());

	}

	public void TC_03_ExplicitWait_5s() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		waitExplicit = new WebDriverWait(driver, 2);

		driver.findElement(startButton).click(); // sau khi click Start button thi mat 5s de render ra "Hello World"

		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(helloText));
		Assert.assertTrue(driver.findElement(helloText).isDisplayed());

	}

	@Test
	public void TC_03_ExplicitWait_3s() {

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		waitExplicit = new WebDriverWait(driver, 4);

		driver.findElement(startButton).click(); // sau khi click Start button thi mat 5s de render ra "Hello World"
		System.out.println("Wait start time = " + getDateTimeSecond());
		// waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(helloText));

		waitExplicit.until(ExpectedConditions.visibilityOf(driver.findElement(helloText)));
		System.out.println("Wait end time = " + getDateTimeSecond());

		Assert.assertTrue(driver.findElement(helloText).isDisplayed());

	}

	public Date getDateTimeSecond() {
		Date date = new Date();
		date = new Timestamp(date.getTime());
		return date;
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}