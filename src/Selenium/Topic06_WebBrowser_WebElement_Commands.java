package Selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic06_WebBrowser_WebElement_Commands {
	WebDriver driver;
//	khởi tạo biến cho locator, 
	By emailTextBox = By.id("mail");
	By ageUnder18Radio = By.id("under_18");
	By educationTextbox = By.id("edu");
	By job01DrodList = By.id("job1");
	By developmentCheckBox = By.id("development");
	By slider01Slider = By.id("slider-1");
	By passwordTextBox = By.id("password");
	By AgeRadioDisabled = By.id("radio-disabled");
	By bioDisableTextBox = By.id("bio");
	By job02DisableDropList = By.id("job2");
	By interestsDisableCheckBox = By.id("check-disbaled");
	By slider02DissableSlider = By.id("slider-2");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Check_Element_Dislayed() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
//		Kiểm tra nếu Element hiển thị thì thực hiện action
		if (isElementDisplayed(emailTextBox)) {
			driver.findElement(emailTextBox).sendKeys("Automation Testing");
		}
		if (isElementDisplayed(educationTextbox)) {
			driver.findElement(educationTextbox).sendKeys("Automation Testing");
		}
		if (isElementDisplayed(ageUnder18Radio)) {
			driver.findElement(ageUnder18Radio).click();
//			kiểm tra xem element đó có đc select chưa
			Assert.assertTrue(driver.findElement(ageUnder18Radio).isSelected());
		}

//		Kiểm tra các Element có displayed
		Assert.assertTrue(isElementDisplayed(emailTextBox));
		Assert.assertTrue(isElementDisplayed(educationTextbox));
		Assert.assertTrue(isElementDisplayed(ageUnder18Radio));

	}

//  Tạo hàm kiểm tra các element có hiển thị hay ko	
	public boolean isElementDisplayed(By by) {
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			System.out.println("----------Element [" + by + "] is displayed------------");
			return true;
		} else {
			System.out.println("----------Element [" + by + "] is un-displayed------------");
			return false;
		}
	}

	@Test
	public void TC_02_Check_Element_Enable_Disable() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		isElementEnabled(emailTextBox);
		isElementEnabled(ageUnder18Radio);
		isElementEnabled(educationTextbox);
		isElementEnabled(job01DrodList);
		isElementEnabled(developmentCheckBox);
		isElementEnabled(slider01Slider);
		isElementEnabled(passwordTextBox);
		isElementEnabled(AgeRadioDisabled);
		isElementEnabled(bioDisableTextBox);
		isElementEnabled(job02DisableDropList);
		isElementEnabled(interestsDisableCheckBox);
		isElementEnabled(slider02DissableSlider);

//		Assert.assertTrue(isElementEnabled(emailTextBox));
//		Assert.assertTrue(isElementEnabled(ageUnder18Radio));
//		Assert.assertTrue(isElementEnabled(educationTextbox));
//		Assert.assertTrue(isElementEnabled(job01DrodList));
//		Assert.assertTrue(isElementEnabled(interestsCheckBox));
//		Assert.assertTrue(isElementEnabled(slider01Slider));
//		Assert.assertTrue(isElementEnabled(passwordTextBox));
//		Assert.assertTrue(isElementEnabled(AgeRadioDisabled));
//		Assert.assertTrue(isElementEnabled(bioDisableTextBox));
//		Assert.assertTrue(isElementEnabled(job02DisableDropList));
//		Assert.assertTrue(isElementEnabled(interestsDisableCheckBox));
//		Assert.assertTrue(isElementEnabled(slider02DissableSlider));
	}

	// Tạo hàm kiểm tra các element có hiển thị hay ko
	public boolean isElementEnabled(By by) {
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			System.out.println("----------Element [" + by + "] is Enabled------------");
			return true;
		} else {
			System.out.println("----------Element [" + by + "] is disabled------------");
			return false;
		}
	}

	@Test
	public void TC_03_Check_Element_Select() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
//		Chọn 2 element
		driver.findElement(ageUnder18Radio).click();
		driver.findElement(developmentCheckBox).click();
//		Verify 2 element ở trên đã được selected
		Assert.assertTrue(isElementSelected(ageUnder18Radio));
		Assert.assertTrue(isElementSelected(developmentCheckBox));
//		Bỏ Select element interests trên 
		driver.findElement(developmentCheckBox).click();
		Assert.assertFalse(isElementSelected(developmentCheckBox));
	}

	public boolean isElementSelected(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			System.out.println("----------Element [" + by + "] is Selected------------");
			return true;
		} else {
			System.out.println("----------Element [" + by + "] is Deselected------------");
			return false;
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}