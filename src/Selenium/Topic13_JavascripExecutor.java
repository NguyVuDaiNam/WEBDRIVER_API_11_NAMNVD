package Selenium;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic13_JavascripExecutor {
	WebDriver driver;

	public void TC_01_JavascriptExcecutor() {
		// Step 01 - Truy cập vào trang: http://live.guru99.com/
		driver.get("http://live.guru99.com/");

		// Step 02 - Sử dụng JE để get domain của page
		String domainName = (String) executeForBrowser("return document.domain");

		// Verify domain = live.guru99.com
		Assert.assertEquals(domainName, "live.guru99.com");

		// Step 03 - Sử dụng JE để get URL của page
		String homepageURL = (String) executeForBrowser("return document.URL");

		// Verify URL = http://live.guru99.com/
		Assert.assertEquals(homepageURL, "http://live.guru99.com/");

		// Step 04 - Open MOBILE page (Sử dụng JE)
		clickToElementByJS("//a[text()='Mobile']");

		// Step 05 - Add sản phẩm SAMSUNG GALAXY vào Cart (click vào ADD TO CART button bằng JE)
		// Hướng dẫn: sử dụng following-sibling
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");

		// Step 06 - Verify message được hiển thị: Samsung Galaxy was added to your shopping cart. (Sử dụng JE - Get innertext of the entire webpage )
		String shoppingCartInnerText = (String) executeForBrowser("return document.documentElement.innerText;");
		System.out.println("In ra chuoi shoppingCartInnerText:" + shoppingCartInnerText);
		Assert.assertTrue(shoppingCartInnerText.contains("Samsung Galaxy was added to your shopping cart."));

		// Step 07 - Open PRIVACY POLICY page (Sử dụng JE)
		clickToElementByJS("//a[text()='Privacy Policy']");

		// Verify title của page = Privacy Policy (Sử dụng JE)
		String privacyTitle = (String) executeForBrowser("return document.title");
		Assert.assertEquals(privacyTitle, "Privacy Policy");

		// Step 08 - Srcoll xuống cuối page
		scrollToBottomPage();
		// Step 09 - Verify dữ liệu có hiển thị với chỉ 1 xpath:
		Assert.assertTrue(driver.findElement(By.xpath("//th[text()='WISHLIST_CNT']/following-sibling::td[text()='The number of items in your Wishlist.' ]")).isDisplayed());

		// Step 10 - Navigate tới domain: http://demo.guru99.com/v4/ (Sử dụng JE)
		// Verify domain sau khi navigate = demo.guru99.com
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		String domainDemoName = (String) executeForBrowser("return document.domain");
		Assert.assertEquals(domainDemoName, "demo.guru99.com");
	}

	public void TC_02_RemoveAttribute() throws InterruptedException {
		// Step 01 - Truy cập vào trang: https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled
		driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled");

		String firstName = "Automation FirstName";
		String lastName = "Automation LastName";

		// Step 02 - Remove thuộc tính disabled của field Last name
		// Switch qua iframe nếu có
		WebElement resultIframe = driver.findElement(By.xpath("//iframe[@id='iframeResult']"));
		driver.switchTo().frame(resultIframe);

		// Remove disabled attribute of Lastname textbox
		removeAttributeInDOM("//input[@name='lname']", "disabled");
		Thread.sleep(3000);
		// Step 03 - Sendkey vào field Last name
		sendkeyToElementByJS("//input[@name='fname']", firstName);
		sendkeyToElementByJS("//input[@name='lname']", lastName);
		/// Eg. Automation Testing

		// Step 04 - Click Submit button
		clickToElementByJS("//input[@value='Submit']");

		// Step 05 - Verify dữ liệu sau khi submit chứa đoạn text đã fill trong field Lastname (Automation Testing)
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'" + firstName + "') and contains(text(),'" + lastName + "')]")).isDisplayed());
	}

	@Test
	public void TC_03_CreateAnAccount() {
		// Step 01 - Truy cập vào trang: http://live.guru99.com/
		driver.get("http://live.guru99.com/");

		// Step 02 - Click vào link "My Account" để tới trang đăng nhập (Sử dụng JE)
		clickToElementByJS("//div[@class='footer']//a[text()='My Account']");

		// Step 03 - Click CREATE AN ACCOUNT button để tới trang đăng kí tài khoản (Sử dụng JE)
		clickToElementByJS("//span[text()='Create an Account']");

		// Step 04 - Nhập thông tin hợp lệ vào tất cả các field: First Name/ Last Name/ Email Address/ Password/ Confirm Password (Sử dụng JE)
		// (Lưu ý: Tạo random cho dữ liệu tại field Email Address)
		sendkeyToElementByJS("//input[@id='firstname']", "Automation");
		sendkeyToElementByJS("//input[@id='lastname']", "Testing");
		sendkeyToElementByJS("//input[@id='email_address']", "automationtest" + randomNumber() + "@gmail.com");
		sendkeyToElementByJS("//input[@id='password']", "123456789");
		sendkeyToElementByJS("//input[@id='confirmation']", "123456789");
		// Step 05 - Click REGISTER button (Sử dụng JE)
		clickToElementByJS("//span[text()='Register']");

		// Step 05 - Verify message xuất hiện khi đăng kí thành công: Thank you for registering with Main Website Store. (Sử dụng JE)
		String mySashboardInnerText = (String) executeForBrowser("return document.documentElement.innerText;");
		Assert.assertTrue(mySashboardInnerText.contains("Thank you for registering with Main Website Store."));

		// Step 06 - Logout khỏi hệ thống (Sử dụng JE)
		clickToElementByJS("//div[@class='account-cart-wrapper']//span[text()='Account']");

		clickToElementByJS("//a[text()='Log Out']");

		// Step 07 - Kiểm tra hệ thống navigate về Home page sau khi logout thành công (Sử dụng JE)
		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src,'logo.png')]")).isDisplayed());
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("http://live.guru99.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int randomNumber() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return number;
	}

	public void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='6px groove red'", element);
	}

	public Object executeForBrowser(String javaSript) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(javaSript);
	}

	public Object clickToElementByJS(String xpathName) {
		WebElement element = driver.findElement(By.xpath(xpathName));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}

	public Object sendkeyToElementByJS(String xpathName, String value) {
		WebElement element = driver.findElement(By.xpath(xpathName));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public Object removeAttributeInDOM(String xpathName, String attribute) {
		WebElement element = driver.findElement(By.xpath(xpathName));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
	}

	public Object scrollToBottomPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public Object navigateToUrlByJS(String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location = '" + url + "'");
	}
}