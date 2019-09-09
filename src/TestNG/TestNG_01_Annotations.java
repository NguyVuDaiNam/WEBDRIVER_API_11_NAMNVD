package TestNG;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG_01_Annotations {
	
  @Test(groups = "Customer",description = "Create customer - TestNG_01_Annotations")
  public void TC_01() {
	  System.out.println("Run Testcase 01");
  }
  
  @Test(groups = "Customer",description = "Edit customer - TestNG_01_Annotations")
  public void TC_02() {
	  System.out.println("Run Testcase 02");
  }
  
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("Run Testcase beforeMethod");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("Run Testcase afterMethod");
  }

  @BeforeClass
  public void beforeClass() {
	  System.out.println("Run Testcase beforeClass");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("Run Testcase afterClass");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("Run Testcase beforeTest");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("Run Testcase afterTest");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("Run Testcase beforeSuite");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("Run Testcase afterSuite");
  }

}
