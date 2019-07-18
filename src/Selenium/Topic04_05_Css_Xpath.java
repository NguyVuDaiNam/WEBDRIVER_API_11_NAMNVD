package Selenium;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic04_05_Css_Xpath {
	WebDriver driver;

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();	
		return saltStr;



	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_LoginWithEmailAndPasswordEmpty() {
		driver.get("http://live.guru99.com");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String emailErrorMsg = driver.findElement(By.id("advice-required-entry-email")).getText();
		Assert.assertEquals(emailErrorMsg, "This is a required field.");
		String PasswordErrorMsg = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertEquals(PasswordErrorMsg, "This is a required field.");
	}

	@Test
	public void TC_02_LoginWithInvalidEmail() {
		driver.get("http://live.guru99.com");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.id("pass")).sendKeys("");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String invalidEmailMsg = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertEquals(invalidEmailMsg, "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_LoginWithPasswordLessThan6Char() {
		driver.get("http://live.guru99.com");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String invalidPasswordMsg = driver.findElement(By.id("advice-validate-password-pass")).getText();
		Assert.assertEquals(invalidPasswordMsg,
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_LoginWithPasswordIncorrect() {
		driver.get("http://live.guru99.com");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String invalidPasswordMsg = driver.findElement(By.xpath("//span[text()='Invalid login or password.']")).getText();
		Assert.assertEquals(invalidPasswordMsg, "Invalid login or password.");
	}

	@Test
	public void TC_05_CreateAccount() throws InterruptedException {
		driver.get("http://live.guru99.com");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys("Super");
		driver.findElement(By.id("lastname")).sendKeys("NAMQA");
		driver.findElement(By.id("email_address")).sendKeys(getSaltString() + "@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123123123");
		driver.findElement(By.id("confirmation")).sendKeys("123123123");
		driver.findElement(By.xpath("//label[text()='Sign Up for Newsletter']")).click();
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		String regisSuccesMsg = driver.findElement(By.xpath("//span[text()='Thank you for registering with Main Website Store.']")).getText();
		Assert.assertEquals(regisSuccesMsg, "Thank you for registering with Main Website Store.");
		driver.findElement(By.xpath("//a[@class='skip-link skip-account']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();
		String logoutSuccesMsg = driver.findElement(By.xpath("//div[@class='page-title']")).getText();
		Assert.assertEquals(logoutSuccesMsg, "YOU ARE NOW LOGGED OUT");
		//Navigate Về HomePage
		//Chỉ Cần Check 1 element ở HomePage xuất hiện
		//Verify HomePage logo image displayed
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='page-title']//img[contains(@src,'logo.png')]")).isDisplayed());
		Assert.assertEquals(driver.getTitle(), "Home page");
	}
	
	@AfterClass 	
	public void afterClass() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.quit();
	}

}