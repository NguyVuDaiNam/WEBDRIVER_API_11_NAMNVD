package Selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic00_TestClassTemplate {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_() throws InterruptedException {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples/#basic.html");
	    Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@class='ms-choice']")).click();
		driver.findElement(By.xpath("//input[@value='2']")).click();

	}

	@Test
	public void TC_02_() {
		driver.get("");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}