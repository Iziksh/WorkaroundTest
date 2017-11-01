package Utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.io.FileUtils;

import org.w3c.dom.Document;

import static org.testng.Assert.fail;

import java.io.File;


public class Base {
	 public static WebDriver driver;
	 public static File fXmlFile;
	 public  File SignInFile;
	 public static Document doc;
	 public static String browser;
	 public static String ExtentReportsPath;
	 public static ExtentReports extent;
	 public static ExtentTest test;
	 public static File scrFile;
	 public static File destFile;
	 public static Base xml_i = new Base();
	 public static String ImagePath;
	 public static Screen screen;
	 
	 //######################## INITIALIZE ########################
	 
	 public static void BrowserProperty()
	 {
		 try {
			 browser = xml_i.getData("browser",0,"init");
			 switch (browser.toLowerCase()) 
			 {
			 case "chrome": 
				 Chrome(xml_i.getData("chromedriver", 0,"init"));
				 break;
			 case "ff": 
				 FF(xml_i.getData("ff", 0,"init"));
				 break;
			 }
			
	
			 driver.get(xml_i.getData("url", 0,"init"));
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
		 } 
		 catch (ParserConfigurationException | SAXException | IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }	
	 }
	 
	 public static void Chrome(String wedriverpath)
	 {
		System.setProperty("webdriver.chrome.driver", wedriverpath);	
		driver = new ChromeDriver();
	 }
	 
	 public static WebDriver FF(String wedriverpath)
	 {
		System.setProperty("webdriver.geckodriver.driver", wedriverpath);
		return driver = new FirefoxDriver();
	 }
	 
	 
	 //############################################################
	 //########################   XML  ###########################
		  
	    //retrieving data from xml file 
	    // nodeNumber start from 0
	    public String getData(String nodeName,int nodeNumber,String FileName ) throws ParserConfigurationException, SAXException, IOException
	    { 
	    	 switch (FileName) 
			  {
		         case "init": 
		        	 fXmlFile=new File("/Users/izik/java workspace/workaround/init.xml‬");
		          break;
		         case "sign_in": 
		        	 fXmlFile=new File("/Users/izik/java workspace/workaround/sign_in.xml‬");
		          break;
		         default: fXmlFile=new File("Pl insert a valid XML file name");
	             break;
		          
			  }   
		        // create the object of doc
		        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
		        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
		        doc=dBuilder.parse(fXmlFile);
		        doc.getDocumentElement().normalize();
		     
	            return doc.getElementsByTagName(nodeName).item(nodeNumber).getTextContent();
	    }
	    
	    
	 // set the content into xml file
	    public static void setData(String nodeName,int nodeNumber ,String textContent  ) throws ParserConfigurationException, SAXException, IOException, Exception
	    {
	       // doc = xmlElement();
	        doc.getElementsByTagName(nodeName).item(nodeNumber).setTextContent(textContent);
	    }
	    
	    //retrieving nodes number
	    public int getNodesNumber(String nodeName) throws ParserConfigurationException, SAXException, IOException
	    {
	     //   doc = xmlElement();
	        int length = doc.getElementsByTagName(nodeName).getLength();
	        return length ;
	    }
		 //########################  END XML  ########################
		 //###########################################################
	    
	    
		 //############################################################
		 //####################   Reports & Screenshot ################
	    
	    public static void ReportsInstance () throws ParserConfigurationException, SAXException, IOException
	    {
	    	 // Reports - new instance
	    	 String ReportName = "WorkaroundReport.html";
	    	 ExtentReportsPath = xml_i.getData("ExtentReportsPath", 0,"init");
	    	 System.out.println("Report - Start test " + ExtentReportsPath+ReportName);
			 extent= new ExtentReports(ExtentReportsPath+ReportName ,true);
	    }
	    
	    public static String Screenshot() throws ParserConfigurationException, SAXException, IOException
	    {   
	    	String ImagePath = xml_i.getData("ImagePath", 0,"init") + "wa.png";
	    	scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    	destFile = new File(ImagePath);
	    	FileUtils.copyFile(scrFile, destFile);
	    	return ImagePath;
	    }
	    
	    public static void StartReportTest(String TestName, String Description)
	    {
	    	 test = extent.startTest(TestName,Description);
	    }
	    
	    public static void EndTest()
	    {
	    	extent.endTest(test);
	    }
	    
	    public static void CloseInstance()
	    {
	    	extent.endTest(test);
	    	extent.flush();
	    	extent.close();
	    }
		 //###################  END Reports & Screenshot #############
		 //###########################################################
	    
	    //############################################################
		 //####################   Visual Testing      ################
	    
	    public static void ImagesInstance()
	    {
	    	 // Reports - new instance
	    	screen = new Screen();
	    	
	    }
	    
	    public static void FindImage() 
	    {
				try 
				{
					//screen = new Screen();
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				    screen.find("/Users/izik/Desktop/wa/wa_image.png");
					test.log(LogStatus.PASS, "Image exist" );
				} 
				catch (FindFailed e) 
				{
					// TODO Auto-generated catch block
					test.log(LogStatus.FAIL, "Image not exist " + e);
				}	
		
	    }
	    
	   
	  
	    
	    //###################  END RVisual Testing       #############
		 //###########################################################
	    
}
