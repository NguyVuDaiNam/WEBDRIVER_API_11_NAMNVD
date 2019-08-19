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

public class Topic10_UserInteraction {
	WebDriver driver;
	Actions action;

	String workingDirectory = System.getProperty("user.dir");
	String jsFilePath = workingDirectory + "\\helper\\drag_and_drop_helper.js";
	String jQueryFilePath = workingDirectory + "\\helper\\jquery_load_helper.js";

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_MoveMouse() throws Exception {
		// Step 01 - Truy cập vào trang: http://www.myntra.com/
		driver.get("http://www.myntra.com/");

		// Step 02 - Hover chuột vào Menu để login
		WebElement avatarIcon = driver.findElement(By.xpath("//div[@class='desktop-user']//div[@class='desktop-userIconsContainer']"));
		action.moveToElement(avatarIcon).perform();

		// Step 03 - Chọn Login button
		driver.findElement(By.xpath("//a[text()='log in']")).click();

		// Step 04 - Verify Login form được hiển thị
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='login-box']")).isDisplayed());

		// Step 05 - Hold Men and click T-shirt
		WebElement menPage = driver.findElement(By.xpath("//a[contains(text(),'Men')]"));
		action.moveToElement(menPage).perform();

		driver.findElement(By.xpath("//div[@data-group='men']//a[text()='T-Shirts']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Men Tshirts']")).isDisplayed());

	}

	@Test
	public void TC_02_ClickAndHold() throws Exception {

		// Test Script 02: Click and hold element - select multiple item
		// Step 01 - Truy cập vào trang: http://jqueryui.com/resources/demos/selectable/display-grid.html
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");

		// Step 02 - Click and hold từ 1-> 4
		List<WebElement> numberIcon = driver.findElements(By.xpath("//ol[@id='selectable']/li")); // khai bao numberIcon la 1 mang dang co 12 elements: 0,1,2,3...
		action.clickAndHold(numberIcon.get(0)).moveToElement(numberIcon.get(3)).release().perform(); // click thang dau tien, hold den thang so 4, nha chuot ra va2 perform

		// Step 03 - Sau khi chọn kiểm tra rằng có đúng 4 phần tử đã được chọn thành công với xpath:
		List<WebElement> numberSelected = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,'ui-selected')]"));
		System.out.println("Phan tu duoc chon = " + numberSelected);
		Assert.assertEquals(4, numberSelected.size());

		driver.navigate().refresh();

		List<WebElement> elements = driver.findElements(By.xpath("//*[@id='selectable']/li"));
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).build().perform();
		elements.get(0).click();
		elements.get(2).click();
		elements.get(4).click();
		elements.get(6).click();
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
		Assert.assertTrue(driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]")).isDisplayed());
		// Step 05 - Click chọn Quit
		quitVisible.click();
		// Step 06 - Accept Javascript alert
		Alert doubleAlert = driver.switchTo().alert();
		doubleAlert.accept();
	}

	@Test
	public void TC_05_DragAndDrop() throws Exception {
		// Step 01 - Truy cập vào trang: http://demos.telerik.com/kendo-ui/dragdrop/angular
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");
		// Step 02 - Kéo hình tròn nhỏ vào hình tròn lớn
		WebElement sourceElement = driver.findElement(By.xpath("//div[@id = 'draggable']"));
		WebElement targetElement = driver.findElement(By.xpath("//div[@id = 'droptarget']"));
		action.dragAndDrop(sourceElement, targetElement).build().perform();
		// Step 03 - Verify message đã thay đổi: You did great!
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id = 'droptarget' and text()='You did great!']")).isDisplayed());
	}

	// bai tap nay chua chay duoc
	@Test
	public void TC_06_DrapAndDropHTML5_Css() throws Exception {
		// Step 01 - Truy cập vào trang: https://html5demos.com/drag/#
		driver.get("https://html5demos.com/drag/#");
		// Step 02 - Sử dụng Javascript/ Jquery để kéo thả element: one/ two/ three/.. vào thùng rác
		String oneCss = "#one";
		String targetCss = "#bin";
		String jQueryLoader = readFile(jQueryFilePath);
		String java_script = readFile(jsFilePath);
		// thuc thi 1 doan lenh Javascript -> Inject Jquery vao browser
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(jQueryLoader);
		// thuc thi doan lenh Javascript de keo the element
		java_script = java_script + "$(\"" + oneCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		je.executeScript(java_script);
		Thread.sleep(3000);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

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
}