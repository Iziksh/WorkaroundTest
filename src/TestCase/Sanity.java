package TestCase;



import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.LogStatus;

import Utilities.Base;

public class Sanity extends Base
{
	// Create a variables of PageObject type    
	static PageObject.MainPageObject mp;
	static PageObject.sign_in si;
	static Base xml_i = new Base();
	
	@BeforeClass
	public static void initfortest() throws  IOException, Exception, SAXException
	{
		
		
		
		
		BrowserProperty();	
		ReportsInstance();
		////  initElements  
		mp = PageFactory.initElements( driver, PageObject.MainPageObject.class); 
		si = PageFactory.initElements( driver, PageObject.sign_in.class); 
		//////
		
		
	}
	
	@AfterClass
	public static void close()
	{
		CloseInstance();
		driver.quit();
	}
	
	@Test
	public void a_test() throws Exception
	{
	 StartReportTest("Entrance", "Entrance to workaround.co.il");
	  mp.Entrance();
	  
	}
	
	@Test
	public void b_test() throws Exception
	{
	  StartReportTest("SignIn", "SignIn to workaround.co.il");
	  si.VerifySignIn();
	}
	
	@After
	public void after()
	{
		EndTest();
	}
	//  XML file location
   //	sign_in(page) fail() Assert.fail not work
}
