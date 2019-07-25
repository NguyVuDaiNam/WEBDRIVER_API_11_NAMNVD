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

public class Topic07_Textbox_TextArea_Dropdown_CustomDropdown {
	WebDriver driver;
	// Khai báo biến data
	String name, dateOfBirth, address, city, state, pin, phone, email, password, customerId;
//	Khai báo biên New Edit
	String Newaddress, Newcity, Newstate, Newpin, Newphone, Newemail;

	By userIdTextBox = By.xpath("//input[@name='uid']");
	By passwordTextBox = By.xpath("//input[@name = 'password']");
	By loginButon = By.xpath("//input[@name='btnLogin']");
	By WellComeHeadingMNG = By.xpath("//marquee[contains(text(),'Welcome To Manager')]");
	By NewCustomerButon = By.xpath("//a[text()='New Customer']");
	By AddNewCustomerButon = By.xpath("//p[text()='Add New Customer']");
	By CustomerNameTextBox = By.xpath("//input[@name='name']");
	By GenderRadio = By.xpath("//input[@name='rad1']");
	By AddressTextBox = By.xpath("//textarea[@name='addr'] ");
	By CityTextbox = By.xpath("//input[@name='city']");
	By StateTextbox = By.xpath("//input[@name='state']");
	By PINTextbox = By.xpath("//input[@name='pinno']");
	By MobileNumberTextbox = By.xpath("//input[@name='telephoneno']");
	By EmailTextbox = By.xpath("//input[@name='emailid']");
	By PasswordTextbox = By.xpath("//input[@name='password']");
	By dateOfBirthTextbox = By.xpath("//input[@name='dob']");
	By submitButon = By.xpath("//input[@name='sub']");

	By CustomerNameRow = By.xpath("//td[text()='Customer Name']/following-sibling::td");
	By Gender = By.xpath("//td[text()='Gender']/following-sibling::td");
	By dateOfBirthRow = By.xpath("//td[text()='Birthdate']/following-sibling::td");
	By CityRow = By.xpath("//td[text()='City']/following-sibling::td");
	By StateRow = By.xpath("//td[text()='State']/following-sibling::td");
	By PinRow = By.xpath("//td[text()='Pin']/following-sibling::td");
	By MobileNoRow = By.xpath("//td[text()='Mobile No.']/following-sibling::td");
	By EmailRow = By.xpath("//td[text()='Email']/following-sibling::td");
	By AddressRow = By.xpath("//td[text()='Address']/following-sibling::td");

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
//		Khai báo Data
		name = "Geno";
		dateOfBirth = "2011-02-10";
		address = "5 Union Lane";
		city = "Flushing";
		state = "New York";
		pin = "100000";
		phone = "7181575494";
		email = getSaltString() + "@gmail.com";
		password = "9o9jA8ourfd";
//		Khai báo NewData
		Newaddress = "5 Union Laneaaaa";
		Newcity = "Flushingssssss";
		Newstate = "New Yorkssssss";
		Newpin = "222222";
		Newphone = "2222222222";
		Newemail = getSaltString() + "@gmail.com";
		driver = new FirefoxDriver();

//		2 Test case đều mở ra URL 
		driver.get("http://demo.guru99.com/v4");
//		Wait cho element đầu tiên được hiển thị
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

//		Login website  
		driver.findElement(userIdTextBox).sendKeys("mngr210488");
		driver.findElement(passwordTextBox).sendKeys("zunuseb");
		driver.findElement(loginButon).click();
		Assert.assertTrue(driver.findElement(WellComeHeadingMNG).isDisplayed());
		Assert.assertEquals(driver.getTitle(), "Guru99 Bank Manager HomePage");
	}

	@Test
	public void TC_01_NewCustomer() throws InterruptedException {
//		Click NewCustomerButon
		driver.findElement(NewCustomerButon).click();
		Assert.assertTrue(driver.findElement(AddNewCustomerButon).isDisplayed());
		driver.findElement(CustomerNameTextBox).sendKeys(name);
		driver.findElement(GenderRadio).click();
		driver.findElement(AddressTextBox).sendKeys(address);
		driver.findElement(CityTextbox).sendKeys(city);
		driver.findElement(StateTextbox).sendKeys(state);
		driver.findElement(PINTextbox).sendKeys(pin);
		driver.findElement(MobileNumberTextbox).sendKeys(phone);
		driver.findElement(EmailTextbox).sendKeys(email);
		driver.findElement(PasswordTextbox).sendKeys(dateOfBirth);
		driver.findElement(dateOfBirthTextbox).sendKeys(dateOfBirth);
//		Submit
		driver.findElement(submitButon).click();
//		verify NewCustomer tao thành công
//		Verify by AssertEqual(Gettext)
		Assert.assertEquals(driver.findElement(By.xpath("//table[@id='customer']//p")).getText(), "Customer Registered Successfully!!!");
//		verify By AsserTrue
		Assert.assertTrue(driver.findElement(By.xpath("//table[@id='customer']//p[text()='Customer Registered Successfully!!!']")).isDisplayed());
//		Verify cái field điền đúng
		Assert.assertEquals(driver.findElement(CustomerNameRow).getText(), name);
		Assert.assertEquals(driver.findElement(Gender).getText(), "male");
		Assert.assertEquals(driver.findElement(dateOfBirthRow).getText(), dateOfBirth);
		Assert.assertEquals(driver.findElement(CityRow).getText(), city);
		Assert.assertEquals(driver.findElement(StateRow).getText(), state);
		Assert.assertEquals(driver.findElement(PinRow).getText(), pin);
		Assert.assertEquals(driver.findElement(MobileNoRow).getText(), phone);
		Assert.assertEquals(driver.findElement(AddressRow).getText(), address);

//		Get dynamic Customer ID
		customerId = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
	}

	@Test
	public void TC_02_EditCustomer() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerId);
		driver.findElement(By.name("AccSubmit")).click();
//		Verify New Customer Show Correct
		Assert.assertEquals(driver.findElement(CustomerNameTextBox).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(dateOfBirthTextbox).getAttribute("value"), dateOfBirth);
		Assert.assertEquals(driver.findElement(AddressTextBox).getText(), address);
		Assert.assertEquals(driver.findElement(CityTextbox).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(StateTextbox).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(PINTextbox).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(MobileNumberTextbox).getAttribute("value"), phone);
		Assert.assertEquals(driver.findElement(EmailTextbox).getAttribute("value"), email);
//		Edit Form Customer
//		Clear data cũ trước khi sendkey mới
		driver.findElement(AddressTextBox).clear();
		driver.findElement(AddressTextBox).sendKeys(Newaddress);
		driver.findElement(CityTextbox).clear();
		driver.findElement(CityTextbox).sendKeys(Newcity);
		driver.findElement(StateTextbox).clear();
		driver.findElement(StateTextbox).sendKeys(Newstate);
		driver.findElement(PINTextbox).clear();
		driver.findElement(PINTextbox).sendKeys(Newpin);
		driver.findElement(MobileNumberTextbox).clear();
		driver.findElement(MobileNumberTextbox).sendKeys(Newphone);
		driver.findElement(EmailTextbox).clear();
		driver.findElement(EmailTextbox).sendKeys(Newemail);
//		Submit New Data
		driver.findElement(submitButon).click();
//		Verify New Data Customer Edit
		Assert.assertEquals(driver.findElement(By.xpath("//table[@id='customer']//p[@class='heading3']")).getText(), "Customer details updated Successfully!!!");

		Assert.assertEquals(driver.findElement(CityRow).getText(), Newcity);
		Assert.assertEquals(driver.findElement(StateRow).getText(), Newstate);
		Assert.assertEquals(driver.findElement(PinRow).getText(), Newpin);
		Assert.assertEquals(driver.findElement(MobileNoRow).getText(), Newphone);
		Assert.assertEquals(driver.findElement(EmailRow).getText(), Newemail);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}