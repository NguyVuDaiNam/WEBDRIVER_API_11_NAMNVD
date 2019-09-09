package TestNG;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNG_04_DataProvider {
	WebDriver driver;
	
@BeforeTest
public void preCondition() {
	driver = new FirefoxDriver();
	driver.get("http://live.guru99.com/");
	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	
}
  @Test(dataProvider = "userPassInfo")
  public void Login_01_LoginWithValidInformation(String username, String password) {
	  driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	  driver.findElement(By.xpath("//input[@title='Email Address']")).sendKeys(username);
	  driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(password);
	  driver.findElement(By.xpath("//button[@id='send2']")).click();
	  
	  Assert.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'My Dashboard')]")).isDisplayed());
	  
	  driver.findElement(By.xpath("//div[@class='page-header-container']//span[text()='Account']")).click();
	  
	  //driver.findElement(By.xpath("//span[text()='Account']")).click();
	  
	  driver.findElement(By.xpath("//a[@title='Log Out']")).click();
	  
	  Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]")).isDisplayed()); 
  }
  
  @DataProvider
  public Object[][] userPassInfo(){
	return new Object[][]{
		{"auto_test_05@gmail.com","123123"},
		{"auto_test_06@gmail.com","123123"},
		{"auto_test_07@gmail.com","123123"}};
  }
@AfterTest
public void postCondition() {
	driver.quit();
}
}
