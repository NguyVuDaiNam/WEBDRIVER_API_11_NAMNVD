package Selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic12_HandleNewWindowTab {
	WebDriver driver;
	JavascriptExecutor javascript; // khai bao bien

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		javascript = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_02() throws Exception {
		// Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		// Step 02 - Click "Opening a new window: Click Here" link -> Switch qua tab mới
		// get ra cua so/tab ma no dang active (single)
		String parentWindowID = driver.getWindowHandle();
		System.out.println(parentWindowID);

		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		// get ra tat ca cac cua so/tab dang co

		switchToChildWindow(parentWindowID);

		// Step 03 - Kiểm tra title của window mới = Google
		Assert.assertEquals(driver.getTitle(), "Google");

		// Step 04 - Close window mới and Step 05 - Switch về parent window
		closeAllWithoutParentWindows(parentWindowID);

		// Step 06 - Kiểm tra đã quay về parent window thành công (title/ url)
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

		// Cach 2: lam lai theo cach switchToWindowByTitle
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		switchToChildWindowByTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");
	}

	@Test
	public void TC_03() throws Exception {
		// Step 01 - Truy cập vào trang: http://www.hdfcbank.com/
		driver.get("http://www.hdfcbank.com/");

		String parentID = driver.getWindowHandle();
		// Step 02 - Kiểm tra và close quảng cáo nếu có xuất hiện
		List<WebElement> notificationsIframe = driver.findElements(By.xpath("//div[@id='vizury-notification-container']/iframe"));
		System.out.println("Notification size = " + notificationsIframe.size());
		if (notificationsIframe.size() > 0) {
			Assert.assertTrue(notificationsIframe.get(0).isDisplayed());
			System.out.println("Pass displayed");
			driver.switchTo().frame(notificationsIframe.get(0));
			System.out.println("Pass switch frame");
			javascript.executeScript("arguments[0].click();", driver.findElement(By.xpath("//dive[@id='div-close']")));
			System.out.println("Pass Close icon");
			driver.switchTo().defaultContent();// Top window(Parent)
		}
		// Step 03 - Click Angri link -> Mở ra tab/window mới -> Switch qua tab mới
		driver.findElement(By.xpath("//div[@class='sectionnav']//a[text()='Agri']")).click();
		// Switch to Agri tab
		switchToChildWindowByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");
		// Step 04 - Click Account Details link -> Mở ra tab/window mới -> Switch qua tab mới
		// Click to Account Details link
		driver.findElement(By.xpath("//p[text()='Account Details']")).click();
		// Switch to Account Details
		switchToChildWindowByTitle("Welcome to HDFC Bank NetBaking");
		// Step 05- Click Privacy Policy link (nằm trong frame) -> Mở ra tab/window mới -> Switch qua tab mới
		// Switch to Footer fram
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='footer']")));
		// Click to Privacy Policy link
		driver.findElement(By.xpath("//a[text()='Privacy Policy']")).click();
		// Switch to Privacy Policy
		switchToChildWindowByTitle("HDFC Bank - Leading Bank in India, Banking Services, Private banking, Personal Loan, Car Loan");
		// Step 06- Click CSR link on Privacy Policy page
		driver.findElement(By.xpath("//a[text()='CSR']")).click();
		Assert.assertEquals(driver.getTitle(), "HDFC BANK - CSR - Homepage");
		// Step 07 - Close tất cả popup khác - chỉ giữ lại parent window (http://www.hdfcbank.com/)
		closeAllWithoutParentWindows(parentID);
	}

	@Test
	public void TC_04() throws Exception {
		// Step 01 - Truy cập vào trang: http://live.guru99.com/index.php/
		driver.get("http://live.guru99.com/index.php/");
		// Step 02 - Click vào Mobile tab
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();
		// Step 03 - Add sản phẩm Sony Xperia vào để Compare (Add to Compare)
		driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Sony Xperia has been added to comparison list.']")).isDisplayed());
		// Step 04 - Add sản phẩm Samsung Galaxy vào để Compare (Add to Compare)
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The product Samsung Galaxy has been added to comparison list.']")).isDisplayed());
		// Step 05 - Click to Compare button
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		// Step 06 - Switch qa cửa sổ mới (chứa 2 sản phẩm đã được Add vào để Compare)
		switchToChildWindowByTitle("Products Comparison List - Magento Commerce");
		Thread.sleep(2000);
		// Step 07 - Verify title của cửa sổ bằng: Products Comparison List - Magento Commerce
		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		// Step 08 - Close tab và chuyển về Parent Window
		String parentID = driver.getWindowHandle();
		closeAllWithoutParentWindows(parentID);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// Switch to child Windows (only 2 windows)
	public void switchToChildWindow(String parentID) throws Exception {
		Set<String> allWindows = driver.getWindowHandles();
		// List ko hay bang Set vi truong hop neu get ra windowid trung nhau thi thang Set chi lay ra 1 ID
		// con List cho get ra du lieu trung nhau
		Thread.sleep(2000);
		// duyet qua tung ID cua tat ca cac cua so/tab
		for (String childWindow : allWindows) {
			System.out.println(childWindow);
			// kiem tra neu nhu ID nao khac voi parentID thi no switch qua cua so do
			if (!childWindow.equals(parentID)) {
				driver.switchTo().window(childWindow);
				break;
			}
		}
	}

	// Switch to child Windows (greater than 2 windows and title of the pages are unique)
	public void switchToChildWindowByTitle(String titleExpected) {
		// get ra tat ca cac cua so dang co
		Set<String> allWindows = driver.getWindowHandles();
		// dung vong lap de duyet qua tung ID cua tat ca cac cua so
		for (String runWindows : allWindows) {
			// switch vao tung window truoc
			driver.switchTo().window(runWindows);
			// get ra title cua window/ tab hien tai
			String currentWindowTitle = driver.getTitle();
			// kiem tra neu nhu currentWindowTitle ma bang voi thang titleExpected thi minh se dung lai
			if (currentWindowTitle.equals(titleExpected)) {
				break;
			}
		}
	}

	// Close all windows without parent window
	public boolean closeAllWithoutParentWindows(String parentID) throws Exception {
		// Get ra cac ID cua tat ca cac cua so / tab
		Set<String> allWindows = driver.getWindowHandles();
		Thread.sleep(2000);
		// Dung vong lap de duyet qua tung ID
		for (String childWindowsID : allWindows) {
			// kiem tra neu nhu ID nao khac voi parentID thi cung switch qua ID do
			if (!childWindowsID.equals(parentID)) {
				// switch qua cua so ID do
				driver.switchTo().window(childWindowsID);
				// close no
				driver.close();
			}
		}
		Thread.sleep(2000);
		// sau khi da close het tab thi phai switch ve parent ID
		driver.switchTo().window(parentID);
		// kiem tra chi con duy nhat 1 cua so la parent
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}
}