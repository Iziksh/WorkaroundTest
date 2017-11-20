package TestCase;



import java.io.IOException;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;


import org.openqa.selenium.support.PageFactory;

import org.xml.sax.SAXException;


import Utilities.Base;


import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


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
	public void firstTest() throws Exception
	{
	// Home page
	 StartReportTest("Entrance", "Entrance to workaround.co.il");
	  mp.Entrance();
	  
	}
	
	@Test
	public void secondTest() throws Exception
	{
	// Sign in page 
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
