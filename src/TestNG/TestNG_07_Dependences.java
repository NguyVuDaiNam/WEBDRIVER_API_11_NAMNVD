package TestNG;

import org.junit.Assert;
import org.testng.annotations.Test;

public class TestNG_07_Dependences {
	
  @Test(groups = "payment")
  public void TC_01() {
	  System.out.println("Run Testcase 01");
	  Assert.assertTrue(false);
  }
  
  @Test(groups = "payment",dependsOnMethods = "TC_01")
  public void TC_02() {
	  System.out.println("Run Testcase 02");
  }
  
  @Test(groups = "Customer",description = "Create customer - TestNG_02_Group_Priority",dependsOnMethods = "TC_02")
  public void TC_03() {
	  System.out.println("Run Testcase 03");
  }
  
  @Test(groups = "Customer",description = "Edit customer - TestNG_02_Group_Priority",dependsOnMethods = "TC_03")
  public void TC_04() {
	  System.out.println("Run Testcase 04");
  }
  @Test(groups = "order",dependsOnMethods = "TC_04")
  public void TC_05() {
	  System.out.println("Run Testcase 05");
  }
  
  @Test(groups = "order",dependsOnMethods = "TC_05")
  public void TC_06() {
	  System.out.println("Run Testcase 06");
  }
  
}
