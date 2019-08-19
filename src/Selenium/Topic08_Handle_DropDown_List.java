package Selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic08_Handle_DropDown_List {
	WebDriver driver;
	// Select dropdownList; --khai bao

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		// dropdownList = new Select(driver.findElement(By.xpath(""))); -- khoi tao
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void Topic_08_Default_Dropdown() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		WebElement jobRoleDropdown = driver.findElement(By.xpath("//select[@id='job1']")); // vua khai bao vua khoi tao

		Select jobRole = new Select(jobRoleDropdown); // jobRole duoc goi la instant cua thu vien Select
		System.out.println("Dropdown status = " + jobRole.isMultiple());
		Assert.assertFalse(jobRole.isMultiple()); // isMultiple la kieu boolean(dung hoac sai), thuoc thu vien

		// Step 03 - Chọn giá trị Automation Tester trong dropdown bằng phương thức selectVisible
		jobRole.selectByVisibleText("Automation Tester");
		Thread.sleep(3000);
		// Step 04 - Kiểm tra giá trị đã được chọn thành công
		Assert.assertEquals(jobRole.getFirstSelectedOption().getText(), "Automation Tester");
		// Step 05 - Chọn giá trị Manual Tester trong dropdown bằng phương thức selectValue
		jobRole.selectByValue("manual");
		Thread.sleep(3000);
		// Step 06 - Kiểm tra giá trị đã được chọn thành công
		Assert.assertEquals(jobRole.getFirstSelectedOption().getText(), "Manual Tester");
		// Step 07 - Chọn giá trị Mobile Tester trong dropdown bằng phương thức selectIndex
		jobRole.selectByIndex(3);
		Thread.sleep(3000);
		// Step 08 - Kiểm tra giá trị đã được chọn thành công
		Assert.assertEquals(jobRole.getFirstSelectedOption().getText(), "Mobile Tester");
		// Step 09 - Kiểm tra dropdown có đủ 5 giá trị
		Assert.assertEquals(jobRole.getOptions().size(), 5);
		System.out.println(jobRole.getOptions().size());

		// Ket luan:
		// 1. Ko nen dung index vi thu tu se de thay doi
		// 2. value - day la gia tri option nen co nhieu dev co the them hoac ko, dybamic dropdown: ko tu dong sinh ra cai value
		// 3. text - se duoc dung nhieu nhat va gan giu voi user nhat, thuong la uniqe(duy nhat)

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}