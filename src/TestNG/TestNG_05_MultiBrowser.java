package TestNG;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNG_05_MultiBrowser {
	WebDriver driver;
	
@Parameters("browser")
		
@BeforeTest
public void preCondition(String browserName) {
	if(browserName.equals("chrome")) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver");
		driver = new ChromeDriver();
	}else if(browserName.equals("firefox")) {
		driver = new FirefoxDriver();
	}else if(browserName.equalsIgnoreCase("headless")) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/lib/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("window-size=1600x900");
		driver = new ChromeDriver(options);
	}
	
	driver.get("http://live.guru99.com/");
	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	
}
	
	@Parameters({"username","password"})
	@Test
  public void Login_01_LoginWithValidInformation(String userName, String passWord){
	  driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	  driver.findElement(By.xpath("//input[@title='Email Address']")).sendKeys(userName);
	  driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(passWord);
	  driver.findElement(By.xpath("//button[@id='send2']")).click();
	  
	  Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'My Dashboard')]")).isDisplayed());
	  
	  driver.findElement(By.xpath("//div[@class='page-header-container']//span[text()='Account']")).click();
	  
	  //driver.findElement(By.xpath("//span[text()='Account']")).click();
	  
	  driver.findElement(By.xpath("//a[@title='Log Out']")).click();
	  
	  Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]")).isDisplayed()); 
  }
@AfterTest
public void postCondition() {
	driver.quit();
}
}
