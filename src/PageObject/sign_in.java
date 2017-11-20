package PageObject;



import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.AssertionError;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliException;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.LogStatus;

import Utilities.Base;
import Utilities.GeneralFunctions;


public class sign_in extends Base {
	public static Base xml_s = new Base();
	public static Base xml_i = new Base();
	public WebDriver driver;
	
	  
	   GeneralFunctions gf = new GeneralFunctions();
	   // Constractor
	   public sign_in(WebDriver driver) 
	   {
	   this.driver = driver; 
	   } 
	    
	    // Objects declarations
	   @FindBy(how = How.ID, using = "user_email")
	   public WebElement user_email;
	   
	   @FindBy(how = How.ID, using = "user_password")
	   public WebElement user_password;
	   
	   @FindBy(how = How.NAME, using = "commit")
	   public WebElement sign_in;
	   
	   @FindBy(how = How.TAG_NAME, using = "img")
	   public WebElement image;
	   
	   @FindBy(how = How.CLASS_NAME, using = "first_titel")
	   public WebElement first_titel;
	   
	   @FindBy(how = How.CLASS_NAME, using = "alert-danger")
	   public WebElement alert_danger;
	   
	   
	   public void VerifySignIn () throws Exception, SAXException, SikuliException
	   {
		   try {
		   
		   ImagesInstance();
		 
		   int rows = xml_s.getNodesNumber("initialize");
		   System.out.println("Rows number for sanity sign_in test is: " + rows);
			for(int i= 0; i<=rows; i++ )
			{
			    test.log(LogStatus.PASS, "Got the expected result and sign_in",test.addScreenCapture(Screenshot()));
				test.log(LogStatus.INFO, "The XML row is: " + (i+1));
				System.out.println("catent row is: " + i);
				
				 
				 FindImage(xml_s.getData("image_path", i,"sign_in"));
				   
				  gf.DisplayObject(user_email);
				  user_email.clear();
				  user_email.sendKeys(xml_s.getData("user_email", i,"sign_in"));
				  gf.DisplayObject(user_password);
				  user_password.clear();
				  user_password.sendKeys(xml_s.getData("user_password", i,"sign_in"));
				  gf.DisplayObject(sign_in);
				  sign_in.click();
				  if(xml_s.getData("expected_sign_in", i ,"sign_in").equals("true"))
				  {
					  System.out.println(xml_s.getData("expected_sign_in", i ,"sign_in") + "; the row number is " + i);
					  gf.DisplayObject(image);
					  gf.DisplayObject(first_titel);
					 
//					  System.out.println("the path is : " + Screenshot());
					  test.log(LogStatus.PASS, "Got the expected result and sign_in" );
					  
				  }
				  else if(xml_s.getData("expected_sign_in", i ,"sign_in").equals("false"))
				  {   
					  
					  System.out.println(xml_s.getData("expected_sign_in", i ,"sign_in")+ "; the row number is " + i);
					  gf.DisplayObject(alert_danger);
					  if (alert_danger.getText().contains("Invalid"))
						  assertTrue("Invalid mail or pw", true);	
					    test.log(LogStatus.PASS, "Got the expected result and not sign_in");
				  }
				  else
				  {
					  System.out.println(xml_s.getData("expected_sign_in", i ,"sign_in") + " " + i);
					  test.log(LogStatus.FAIL, "XML fail -- The **expected_sign_in** supposed be boolean variable");
				      fail("XML fail -- The **expected_sign_in** supposed be boolean variable");
				  }
			 }
			}	
		   catch (IOException e)
		   {
			   
			   test.log(LogStatus.FAIL, e.getMessage());
			   e.getMessage();
		   }
		   catch(AssertionError ee)
		   {
			   test.log(LogStatus.FAIL,ee.getMessage());
			    System.out.println("failr eee"+ ee);
			    fail("Image not exist ");
			}
		
		 
		}
}
     