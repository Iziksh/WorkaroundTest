package Utilities;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

public class Base {
	public static String User="";
	public static String UserIm="";
	public static String UserFi="";


	public static String SSpath;
	public static WebDriver driver;
	public static JavascriptExecutor jse;
	public static Statement sqlStatement;
	public static Connection myConnection;
	public static String dbURL;
	public static String strUserID;
	public static String strPassword;
	public static String db;
	public static String environment;
	public static ExtentReports extent;
	public static ExtentTest test;  
	
	public static String tests[];
	
	// UI TESTS CASES:
	public static String CASE_1 = "Search only by sailing 'date to' field";
	public static String CASE_2 = "Search only by sailing 'date from' field";
	public static String CASE_3 = "Search 'Date to' greater 'Date from' field";
	public static String CASE_4 = "Try to print over 5 documents";
	public static String CASE_5 = "Insert not valid value to POL field";
	public static String CASE_6 = "Insert not valid value to POD field";
	public static String CASE_7 = "Insert not valid value to VESSEL field";
	public static String CASE_8 = "Insert not valid value to VOYAGE field";
	public static String CASE_9 = "Filtering results by POL from list";
	public static String CASE_10 = "Filtering results by POD from list";
	public static String CASE_11 = "Filter by BL but with irrelevante date range";
	public static String CASE_12 = "Filter by date range";
		

	public static String url;
	
	public static String xmlPath=  System.getProperty("user.dir") + "\\sanity.xml";
	public static String ReportFilePath=System.getProperty("user.dir")+ "\\report\\";
	public static String PDfPath=System.getProperty("user.dir")+ "\\PDF";
	public static String ReportImagePath=System.getProperty("user.dir")+"\\report\\images\\";
	

	public static String user_name= System.getProperty("user.name"); 
	
	
	public static String getData(String xmlFile,String nodeName,Integer indx) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile= new File(xmlFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder =dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc.getElementsByTagName(nodeName).item(indx).getTextContent();
		
	}
	
	public static void setData(String xmlFile,String nodeName,Integer indx,String value) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile= new File(xmlFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder =dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		doc.getElementsByTagName(nodeName).item(indx).setTextContent(value);
		
	}
	
	public static int getNodeCount(String xmlFile,String nodeName) throws ParserConfigurationException, SAXException, IOException
	{
		File fXmlFile= new File(xmlFile);
		int nodeCount=0;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder =dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return nodeCount=doc.getElementsByTagName(nodeName).getLength();
		
		
	}
	
	
	public static void report_folders()
	{
		mkdir_by_path(ReportFilePath);
		mkdir_by_path(ReportImagePath);
		mkdir_by_path(PDfPath);
	}
	public static void mkdir_by_path(String path)
	{
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}	
	}
	
	
	public static void initBrowser(String browserType) throws ParserConfigurationException, SAXException, IOException
	{
		switch (browserType.toLowerCase())
		{
		case "firefox":
			driver=initFFDriver();
			break;
		case "ie":
			driver=initIEDriver();
			break;
		case "chrome":
			driver=initChromeDriver();
			break;	
			
		}
		
	}
	
	public static WebDriver initFFDriver() throws ParserConfigurationException, SAXException, IOException
	{
		System.setProperty("webdriver.gecko.driver",getData(xmlPath,"fireFoxPath",0));
		WebDriver driverff=new FirefoxDriver();
		return driverff;
	}
	
	public static WebDriver initIEDriver() throws ParserConfigurationException, SAXException, IOException
	{
		System.setProperty("webdriver.ie.driver",getData(xmlPath,"IEPath",0));	
		WebDriver driverie=new InternetExplorerDriver();
		return driverie;
	}
	
	public static WebDriver initChromeDriver()
	{
		
		try {
			System.setProperty("webdriver.chrome.driver",getData(xmlPath,"ChromePath",0));

			ChromeOptions options = new ChromeOptions();

			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			chromeOptionsMap.put("plugins.plugins_disabled", new String[] {"Chrome PDF Viewer"});
			chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
			options.setExperimentalOption("prefs", chromeOptionsMap);
			options.addArguments("--no-sandbox");
			chromeOptionsMap.put("download.default_directory", PDfPath);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(cap);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return driver;
	}
	
	

	
	
	public static String takeSS()
	{
		String SSpath = null;
		try
		{
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy_HHmmss");
			String ds=sdf.format(date);
            SSpath= "images\\" + ds +".png" ;
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("report\\" + SSpath));
			System.out.println(SSpath);
		}
		catch(Exception e)
		{ 
			System.out.println(e);
		}  
		return SSpath;
	}
	
	
	public static void dbConnection(String LCC){  
	try{  
	//step1 load the driver class  
	Class.forName("oracle.jdbc.driver.OracleDriver");  
	  
	//step2 create  the connection object  
	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@//****","****","****");  
	  
	//step3 create the statement object  
	Statement stmt=con.createStatement();  
	  
	//step4 execute query  
	ResultSet rs=stmt.executeQuery("select .........");
	ResultSetMetaData rsmt=rs.getMetaData();
	int colCount=rsmt.getColumnCount();
	
	while(rs.next()){
		for(int i=1; i<=colCount;i++){
			System.out.print(rs.getString(i) + " ");
	
		}
		
	}
	  
	//step5 close the connection object  
	con.close();  
	  
	}catch(Exception e){ 
		
		System.out.println(e);}  
	  
	}  
	
	public static String dbGetContainer(String containerType){  
		
		
		String containerNumber = null;
		try{  
		//step1 load the driver class  
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		  
		//step2 create  the connection object  
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@//hfaexa-scan:1521/****","****","****");  
		  
		//step3 create the statement object  
		Statement stmt=con.createStatement();  
		  
		//step4 execute query  
		ResultSet rs=stmt.executeQuery("SELECT ........................................");
		//ResultSetMetaData rsmt=rs.getMetaData();
		rs.next();
		containerNumber=rs.getString(1);
//		int colCount=rsmt.getColumnCount();
//		while(rs.next()){
//			for(int i=1; i<=colCount;i++){
//				System.out.print(rs.getString(i) + " ");
//		
//			}
//			
//		}
		
		  
		//step5 close the connection object  
		con.close();  
		  
		}catch(Exception e){ 
			
			System.out.println(e);}  
		
		  
	
	
	
	return containerNumber;
	
	
	}
	
	
	public void web_connection_string ()
	  {
     String dbURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=******)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=****)))";
	   String strUserID = "****";
	   String strPassword = "****";
		      try
		      {
		            myConnection=DriverManager.getConnection(dbURL,strUserID,strPassword);
		            sqlStatement = myConnection.createStatement();  
		        }
		      catch (SQLException e) 
		      {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }  
		  }
	
	
	 	  
	  
	  public Connection connaction_string_sqlserver() throws ClassNotFoundException, SQLException
	  {
	        //Loading the required JDBC Driver class
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  

	        // Create a variable for the connection string.  
	        String connectionUrl = "jdbc:sqlserver://*****\\*****;" +  
	                    "databaseName=****;user=****;password=****";  

	        // Declare the JDBC objects.  
	        Connection con = null;  
	        
	        // Establish the connection.  
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	        con = DriverManager.getConnection(connectionUrl);  

	        return con;
	  }    
	  
		public static void InstanceReports(String rn) throws ParserConfigurationException, SAXException, IOException
		{
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy_HHmmss");
			String ds=sdf.format(date);
			String reportName=rn+ " _ " + ds + ".html" ;
			extent=new ExtentReports(ReportFilePath+ reportName,true);
			
		}

		public static void initReportTest(String testName,String testDesc)
		{
			test=extent.startTest(testName,testDesc);
		}
		public static void finalizeReportTest()
		{
			extent.endTest(test);
	}
	
		public static void finalizeExtentReportTest()
		{
			extent.flush();
			extent.close();
		}
		
		
		


}
