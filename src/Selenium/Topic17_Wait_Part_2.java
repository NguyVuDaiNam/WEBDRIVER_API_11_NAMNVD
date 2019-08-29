package Selenium;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic17_Wait_Part_2 {
	WebDriver driver;
	WebDriverWait waitExplicit;

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
		waitExplicit = new WebDriverWait(driver, 40);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void TC_01_AjaxLoading_WithoutExplicitWait() {

		// Step 01 - Truy cập vào trang:
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

		// Step 02 - Wait cho "Date Time Picker" được hiển thị (sử dụng: presence or visibility)
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='calendarContainer']")).isDisplayed());

		// Step 03 - In ra ngày đã chọn (Before AJAX call) -> hiện tại chưa chọn nên in ra = "No Selected Dates to display."
		String beforeDateSeleted = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText();
		System.out.println("Ngày trước khi chọn = " + beforeDateSeleted);

		// Step 04 - Chọn ngày hiện tại (VD: 23/09/2017) (hoặc 1 ngày bất kì tương ứng trong tháng/ năm hiện tại)
		driver.findElement(By.xpath("//a[text()=13]")).click();

		// Step 05 - Wait cho đến khi "loader ajax" không còn visible (sử dụng: invisibility)
		// wait cho 1 element kế tiếp hiển thị
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1' and contains(text(),'Saturday, April 13, 2019')]")).isDisplayed());

		String afterDateSeleted = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText().trim();
		System.out.println("Ngày sau khi chọn = " + afterDateSeleted);

		// Step 06 - Wait cho selected date = 23 được visible ((sử dụng: visibility)
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()=13]/parent::td[@class='rcSelected']")).isDisplayed());

		// Step 07 - Verify ngày đã chọn bằng = Saturday, September 23, 2017
		Assert.assertEquals(afterDateSeleted, "Saturday, April 13, 2019");
	}

	public void TC_02_AjaxLoading_ExplicitWait() {

		// Step 01 - Truy cập vào trang:
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

		// Step 02 - Wait cho "Date Time Picker" được hiển thị (sử dụng: presence or visibility)
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='calendarContainer']")));

		// Step 03 - In ra ngày đã chọn (Before AJAX call) -> hiện tại chưa chọn nên in ra = "No Selected Dates to display."
		String beforeDateSeleted = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText();
		System.out.println("Ngày trước khi chọn = " + beforeDateSeleted);

		// Step 04 - Chọn ngày hiện tại (VD: 23/09/2017) (hoặc 1 ngày bất kì tương ứng trong tháng/ năm hiện tại)
		waitExplicit.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=13]")));
		driver.findElement(By.xpath("//a[text()=13]")).click();

		// Step 05 - Wait cho đến khi "loader ajax" không còn visible (sử dụng: invisibility)
		// wait cho Ajax loading icon biến mất
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));

		String afterDateSeleted = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText().trim();
		System.out.println("Ngày sau khi chọn = " + afterDateSeleted);

		// Step 06 - Wait cho selected date = 23 được visible ((sử dụng: visibility)
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()=13]/parent::td[@class='rcSelected']")));

		// Step 07 - Verify ngày đã chọn bằng = Saturday, September 23, 2017
		Assert.assertEquals(afterDateSeleted, "Saturday, April 13, 2019");
	}

	@Test
	public void TC_03_FluentWait() {
		// Step 01 - Truy cập vào trang:
		driver.get("https://daominhdam.github.io/fluent-wait/");

		// Step 02 - Wait cho đến khi countdown time được visible (visibility)
		WebElement countdount = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		waitExplicit.until(ExpectedConditions.visibilityOf(countdount));

		// Khởi tạo Fluent wait
		new FluentWait<WebElement>(countdount)
				// Tổng time wait là 15s
				.withTimeout(15, TimeUnit.SECONDS)
				// Tần số mỗi 1s check 1 lần
				.pollingEvery(1, TimeUnit.SECONDS)
				// Nếu gặp exception là find ko thấy element sẽ bỏ qua
				.ignoring(NoSuchElementException.class)
				// Kiểm tra điều kiện
				.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement element) {
						// Kiểm tra điều kiện countdount = 00
						boolean flag = element.getText().endsWith("00");
						System.out.println("Time = " + element.getText());
						// return giá trị cho function apply
						return flag;
					}
				});
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}