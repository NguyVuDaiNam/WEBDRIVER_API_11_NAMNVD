package TestNG;

import org.testng.annotations.Test;

public class TestNG_02_Group {
	
  @Test(groups = "payment")
  public void TC_01() {
	  System.out.println("Run Testcase 01");
  }
  
  @Test(groups = "payment")
  public void TC_02() {
	  System.out.println("Run Testcase 02");
  }
  
  @Test(groups = "Customer",description = "Create customer - TestNG_02_Group_Priority")
  public void TC_03() {
	  System.out.println("Run Testcase 03");
  }
  
  @Test(groups = "Customer",description = "Edit customer - TestNG_02_Group_Priority")
  public void TC_04() {
	  System.out.println("Run Testcase 04");
  }
  @Test(groups = "order")
  public void TC_05() {
	  System.out.println("Run Testcase 05");
  }
  
  @Test(groups = "order")
  public void TC_06() {
	  System.out.println("Run Testcase 06");
  }
  
}
