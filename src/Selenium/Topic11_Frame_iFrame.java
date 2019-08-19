package Selenium;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic11_Frame_iFrame {
	WebDriver driver;
	JavascriptExecutor javascript; // khai bao bien
	Actions action;

	String workingDirectory = System.getProperty("user.dir");
	String jsFilePath = workingDirectory + "\\helper\\drag_and_drop_helper.js";
	String jQueryFilePath = workingDirectory + "\\helper\\drag_load_helper.js";

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		action = new Actions(driver);
		javascript = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Popup_Frame_iFrame() throws Exception {
		// Step 01 - Truy cập vào trang: http://www.hdfcbank.com/
		driver.get("http://www.hdfcbank.com/");

		// Step 02 - Close popup nếu có hiển thị (switch qua iframe nếu có) - F5 (refresh page) nhiều lần thì sẽ xuất hiện popup
		// TH1 - Chay vao co popup sau do click Close icon de dong popup
		// TH2 - Chay vao ko gap popup thi chay tiep step tiep theo
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

		// Step 03 - Verify đoạn text được hiển thị: What are you looking for? (switch qua iframe nếu có)
		WebElement lookingForIframe = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(lookingForIframe);

		WebElement lookingForText = driver.findElement(By.xpath("//span[@id='messageText' and text()='What are you looking for?']"));
		Assert.assertTrue(lookingForText.isDisplayed());
		driver.switchTo().defaultContent();

		// Step 04 - Verify banner có đúng 6 images (switch qua iframe nếu có)
		WebElement slidingBannerIframe = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(slidingBannerIframe);

		List<WebElement> slidingBannerImages = driver.findElements(By.xpath("//div[@class='bannerimage-container']//img"));
		Assert.assertEquals(slidingBannerImages.size(), 6);
		driver.switchTo().defaultContent();

		// Step 05 - Verify flipper banner được hiển thị và có 8 items
		List<WebElement> flipperBannerImages = driver.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));
		Assert.assertEquals(flipperBannerImages.size(), 8);

		for (int i = 0; i < flipperBannerImages.size(); i++) {
			System.out.println("Check image thu = " + i);
			Assert.assertTrue(flipperBannerImages.get(i).isDisplayed());
		}

		for (WebElement bannerImage : flipperBannerImages) {
			Assert.assertTrue(bannerImage.isDisplayed());
		}

	}

	@Test
	public void TC_02_ClickAndHold() throws Exception {
		// Step 01 - Truy cập vào trang: http://jqueryui.com/resources/demos/selectable/display-grid.html
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		// Step 02 - Click and hold từ 1-> 4
		List<WebElement> numberIcon = driver.findElements(By.xpath("//ol[@id='selectable']/li"));

		action.clickAndHold(numberIcon.get(0)).moveToElement(numberIcon.get(3)).release().perform();

		// Step 03 - Sau khi chọn kiểm tra rằng có đúng 4 phần tử đã được chọn thành công với xpath:
		List<WebElement> numberSelected = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,'ui-selected')]"));
		System.out.println("Phan tu duoc chon = " + numberSelected.size());
		Assert.assertEquals(4, numberSelected.size());

		driver.navigate().refresh();

		List<WebElement> elements = driver.findElements(By.xpath("//*[@id='selectable']/li"));
		action.keyDown(Keys.CONTROL).build().perform();
		elements.get(1).click();
		elements.get(3).click();
		elements.get(5).click();
		elements.get(7).click();
		elements.get(9).click();
		action.keyUp(Keys.CONTROL).build().perform();
	}

	@Test
	public void TC_03_DoubleClick() throws Exception {
		// Step 01 - Truy cập vào trang: http://www.seleniumlearn.com/double-click
		driver.get("http://www.seleniumlearn.com/double-click");

		// Step 02 - Double click vào element: Double-Click Me!
		WebElement doubleButton = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		action.doubleClick(doubleButton).perform();

		// Step 03 - Verify text trong alert được hiển thị: 'The Button was double-clicked.'
		Alert doubleAlert = driver.switchTo().alert();
		Assert.assertEquals(doubleAlert.getText(), "The Button was double-clicked.");

		// Step 04 - Accept Javascript alert
		doubleAlert.accept();
	}

	@Test
	public void TC_04_RightOrContextClick() throws Exception {
		// Step 01 - Truy cập vào trang: http://swisnl.github.io/jQuery-contextMenu/demo.html
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

		// Step 02 - Right click vào element: right click me
		WebElement rightClickButton = driver.findElement(By.xpath("//span[text()='right click me']"));
		action.contextClick(rightClickButton).perform();

		// Step 03 - Hover chuột vào element: Quit
		WebElement quitVisible = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit')]"));
		action.moveToElement(quitVisible).perform();

		// Step 04 - Verify element Quit (visible + hover) với xpath:
		// Check quit hovered success
		Assert.assertTrue(driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]")).isDisplayed());

		// Click to Quit
		quitVisible.click();

		Alert doubleAlert = driver.switchTo().alert();
		doubleAlert.accept();
	}

	@Test
	public void TC_05_DragAndDrop() throws Exception {
		// Step 01 - Truy cập vào trang: http://demos.telerik.com/kendo-ui/dragdrop/angular
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");

		// Step 02 - Kéo hình tròn nhỏ vào hình tròn lớn
		WebElement sourceElement = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement targetElement = driver.findElement(By.xpath("//div[@id='droptarget']"));
		action.dragAndDrop(sourceElement, targetElement).build().perform();

		// Step 03 - Verify message đã thay đổi: You did great!
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='droptarget' and text()='You did great!']")).isDisplayed());

	}

	@Test
	public void TC_06_DrapAndDropHTML5_Css() throws Exception {
		// Step 01 - Truy cập vào trang: https://html5demos.com/drag/#
		driver.get("https://html5demos.com/drag/");

		// Step 02 - Sử dụng Javascript/ Jquery để kéo thả element: one/ two/ three/.. vào thùng rác
		String oneCss = "#one";
		String targetCss = "#bin";
		String java_script = readFile(jsFilePath);
		String jQueryLoader = readFile(jQueryFilePath);

		// Thuc thi mot doan lenh Havascript -> Inject Jquery vao browser
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(jQueryLoader);

		java_script = java_script + "$(\"" + oneCss + "\").simulateDrapDrop({ dropTarget: \"" + targetCss + "\"});";
		je.executeScript(java_script);

		Thread.sleep(3000);
	}

	// Ham doc file
	public String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}