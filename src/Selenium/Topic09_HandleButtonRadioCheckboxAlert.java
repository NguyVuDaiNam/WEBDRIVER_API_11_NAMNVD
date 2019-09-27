package Selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic09_HandleButtonRadioCheckboxAlert {
	WebDriver driver;
	JavascriptExecutor javascript; // khai bao bien

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//111111111
	@Test
	public void TC_01_HandleButton() {
		// Step 01 - Truy cập vào trang: http://live.guru99.com/
		driver.get("http://live.guru99.com");

		// Step 02 - Click vào link My Account dưới footer (Sử dụng Javascript Executor code)
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		javascript.executeScript("arguments[0].click();", myAccountLink);

		// Step 03 - Kiểm tra url của page sau khi click là: http://live.guru99.com/index.php/customer/account/login/
		String myAccountURL = driver.getCurrentUrl();
		Assert.assertEquals(myAccountURL, "http://live.guru99.com/index.php/customer/account/login/");

		// Step ssertEquals04 - Click vào button CREATE AN ACCOUNT (Sử dụng Javascript Executor code)
		WebElement createAnAccountBtn = driver.findElement(By.xpath("//span[text()='Create an Account']"));
		javascript.executeScript("arguments[0].click();", createAnAccountBtn);

		// Step 06 - Kiểm tra url của page sau khi click là: http://live.guru99.com/index.php/customer/account/create/
		String createAnAccountUrl = driver.getCurrentUrl();
		Assert.assertEquals(createAnAccountUrl, "http://live.guru99.com/index.php/customer/account/create/");

	}

	@Test
	public void TC_02_HandleCheckbox() throws Exception {
		/*
		 * //Step 01 - Truy cập vào trang: http://demos.telerik.com/kendo-ui/styling/checkboxes driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		 * 
		 * //Step 02 - Click vào checkbox: Dual-zone air conditioning (Thẻ input ko được sử dụng thuộc tính id) //Case 1: Thẻ label click được nhưng ko check isSelected WebElement dualZoneLable = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']")); dualZoneLable.click();
		 * Thread.sleep(3000);
		 * 
		 * WebElement dualZoneCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")); //Step 03 - Kiểm tra checkbox đó đã chọn //Case 2: Thẻ input có thể kiểm tra isSelected được nhưng lại ko click được
		 * Assert.assertTrue(dualZoneCheckbox.isSelected()); //Step 04 - Sau khi checkbox đã được chọn - deselect nó và kiểm tra nó chưa được chọn //Case 3: Dùng cả thẻ label với input(2 biến cho 1 element) if(dualZoneCheckbox.isSelected()) { dualZoneLable.click(); }
		 */

		// Step 01 - Truy cập vào trang: http://demos.telerik.com/kendo-ui/styling/checkboxes
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");

		// Step 02 - Click vào checkbox: Dual-zone air conditioning (Thẻ input ko được sử dụng thuộc tính id)
		WebElement dualZoneCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		javascript.executeScript("arguments[0].click();", dualZoneCheckbox);
		Thread.sleep(3000);

		// Step 03 - Kiểm tra checkbox đó đã chọn
		Assert.assertTrue(dualZoneCheckbox.isSelected());

		// Step 04 - Sau khi checkbox đã được chọn - deselect nó và kiểm tra nó chưa được chọn
		if (dualZoneCheckbox.isSelected()) {
			javascript.executeScript("arguments[0].click();", dualZoneCheckbox);
		}
		Assert.assertFalse(dualZoneCheckbox.isSelected());

	}

	@Test
	public void TC_03_HandleRadiobutton() throws Exception {

		// Step 01 - Truy cập vào trang: http://demos.telerik.com/kendo-ui/styling/radios
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");

		// Step 02 - Click vào radiobutton: 2.0 Petrol, 147kW (Thẻ input ko được sử dụng thuộc tính id)
		WebElement petroRadiobutton = driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"));
		javascript.executeScript("arguments[0].click();", petroRadiobutton);
		Thread.sleep(3000);

		// Step 03 - Kiểm tra radio button đó đã chọn hay chưa/ nếu chưa chọn lại
		if (petroRadiobutton.isSelected()) {
			javascript.executeScript("arguments[0].click();", petroRadiobutton);
		}
	}

	@Test
	public void TC_04_HandleAlert() throws Exception {

		// Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		// Step 02 - Click vào button: Click for JS Alert
		WebElement alertButton = driver.findElement(By.xpath("//button[@onclick='jsAlert()']"));
		alertButton.click();
		Thread.sleep(3000);

		// Step 03 - Verify message hiển thị trong alert là: I am a JS Alert
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		Assert.assertEquals(alertText, "I am a JS Alert");

		// Step 04 - Accept alert và verify message hiển thị tại Result là: You clicked an alert successfully
		alert.accept();
		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(), "You clicked an alert successfully");

	}

	@Test
	public void TC_05_HandleConfirm() throws Exception {

		// Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
		driver.get("https://daominhdam.github.io/basic-form/index.html																																							");

		// Step 02 - Click vào button: Click for JS Confirm
		WebElement alertButton = driver.findElement(By.xpath("//button[@onclick='jsConfirm()']"));
		alertButton.click();
		Thread.sleep(3000);

		// Step 03 - Verify message hiển thị trong alert là: I am a JS Confirm
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		Assert.assertEquals(alertText, "I am a JS Confirm");

		// Step 04 - Cancel alert và verify message hiển thị tại Result là: You clicked: Cancel
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(), "You clicked: Cancel");

	}

	@Test
	public void TC_06_HandlePrompt() throws Exception {

		// Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		// Step 02 - Click vào button: Click for JS Prompt
		driver.findElement(By.xpath("//button[@onclick='jsPrompt()']")).click();
		Thread.sleep(5000);

		// Step 03 - Verify message hiển thị trong alert là: I am a JS prompt
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		Assert.assertEquals(alertText, "I am a JS prompt");
		Thread.sleep(5000);

		// Step 04 - Nhập vào text bất kì (daominhdam) và verify message hiển thị tại Result là: You entered: Nhập vào text bất kì (daominhdam) và verify message hiển thị tại Result là: You entered: daominhdam
		alert.sendKeys("daominhdam");
		Thread.sleep(5000);
		alert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='result' and text()='You entered: daominhdam']")).isDisplayed());

	}

	@Test
	public void TC_07_AuthenticationAlert() throws Exception {

		// Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
		// and Step 02 - Handle authentication alert vs user/pass: admin/ admin
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

		// Step 03 - Verify message hiển thị sau khi login thành công:
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credenti')]")).isDisplayed());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}