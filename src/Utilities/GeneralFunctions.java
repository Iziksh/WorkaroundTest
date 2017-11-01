package Utilities;



import java.io.IOException;
import static org.junit.Assert.fail;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import Utilities.Base;

public class GeneralFunctions extends Base
{

  public void selectByVisibleText1(WebElement element,String text) throws IOException
  {
	  try 
	  {
		  Select mySelect = new Select(element);
		  mySelect.selectByVisibleText(text);
		  System.out.println("The element selected");
	  }
	  catch(AssertionError ie){
	      System.out.println("The element not selected"+ ie.getMessage());
	      fail("The element not selected");
	  }
  }
  
  public void DisplayObject (WebElement element) throws IOException
  {
	  try 
	  {
		  element.isDisplayed();
		  System.out.println("The element displayed");
		  test.log(LogStatus.PASS, "The element displayed ");
	  }
	  catch(AssertionError ie){
	      System.out.println("The element not displayed"+ ie.getMessage());
	      fail("The element not displayed");
	      test.log(LogStatus.FAIL, "The element not displayed ");
	  }
  }
  
}


