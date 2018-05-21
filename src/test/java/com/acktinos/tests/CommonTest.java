package com.acktinos.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.acktinos.Utils.TestCaseExecutor;
import com.acktinos.Utils.XLReaderFilloHelper;
import com.acktinos.actions.BaseAction;
import com.acktinos.constants.ApplicationConstants;
import com.acktinos.helper.TestDataReader;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;




public class CommonTest {	
	static Logger log;
	static String methodName;
	{
		System.setProperty("atu.reporter.config", "D:\\Veena\\TestingProjects\\AcktinosAutomation\\src\\test\\resources\\properties\\atu.properties");
	}
	BaseAction action;
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date today = Calendar.getInstance().getTime(); 
	
	static String xmlfilePath = "src/test/resources/testData/";
	static String baseFilePath = System.getProperty("user.dir");
	static String fullPath=baseFilePath+File.separator+xmlfilePath;

	
	
	
	@BeforeTest()
	public void doReportSetup(ITestContext context ) {
		
		String filePath = ApplicationConstants.REPORTER_DIR;
		String parantPath = System.getProperty("user.dir");
		
		File absDirPath = new File(parantPath + File.separator + filePath);		
		
		try {
			FileUtils.deleteDirectory(absDirPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date = new Date();
	ATUReports.setAuthorInfo("Automation", df.format(today), "1.2");
		
	ATUReports.currentRunDescription="Sanity Execution of Service Now ";
		
	}
	
	
	@BeforeMethod
	public void beforeMethodSetup(Method method) {

		//Clear Reporter Fodler
		
		String methodName=method.getName();
		
	System.out.println("Executing methodName  "+methodName);
	}
	
	static WebDriver driver = null;
	public void doUserSetup() throws InterruptedException{
		
		/*action=new BaseAction();
		
		System.out.println("Entering user Login");
		
		String browserName=TestDataReader.getTestConfigDataValue(ApplicationConstants.TEST_BROWSER);
	
		
		String url=TestDataReader.getTestConfigDataValue(ApplicationConstants.TEST_URL);
		
		String  userUrl=url+"/sp";
		
		action.initializeBrowser(browserName, userUrl);
		
		
		String userName=TestDataReader.getTestConfigDataValue(ApplicationConstants.TEST_USER);
		
		String password=TestDataReader.getTestConfigDataValue(ApplicationConstants.TEST_PASSWORD);
		System.out.println("userName  "+userName+"  password  "+password);
		action.doLogin(userName,password);*/
	}
	
	
	public void doAdminSetup(ITestContext testContext) throws InterruptedException{
		
		//action=new BaseAction();
		String browserName=testContext.getCurrentXmlTest().getParameter("browserType");
		
		String url=testContext.getCurrentXmlTest().getParameter("url");
		String  adminURL=url+"/nav_to.do";
		//action.initializeBrowser(browserName, adminURL);
		String adminUser=TestDataReader.getTestConfigDataValue(ApplicationConstants.TEST_ADMIN_USER);
		String adminPassword=TestDataReader.getTestConfigDataValue(ApplicationConstants.TEST_ADMIN_PASSWORD);
		action.doAdminLogin(adminUser,adminPassword);
		
	}
	
	public void doPageExecution(String testMethodName,String moduleName) {
		
		System.out.println("PageExecution Started");
		
		String fileName="PageObjectMaster.xlsx";	
		String query="Select * from  "+testMethodName ;
		
		System.out.println("query for PageObjectMaster  "+query);
		
		XLReaderFilloHelper reader=new XLReaderFilloHelper(fullPath+fileName) ;
		reader.executePage(testMethodName, moduleName, query);
		
	}
	
	@AfterMethod
	public void getMethodDetails(ITestResult testResult) {
		
		System.out.println("Runing ====================");
		
		/*String QRCodeGeneratedFor=testResult.getAttribute("itemCode").toString();
		System.out.println("Qr Code generated for "+QRCodeGeneratedFor +  "  is  "+testResult.getStatus());
		if(testResult.getStatus()==2) {
			System.out.println("reached for fail");
		DataHelper.writeResult("Fail", QRCodeGeneratedFor);
		
		}
		else if(testResult.getStatus()==1) {
			System.out.println("reached for Pass");
			DataHelper.writeResult("Pass", QRCodeGeneratedFor);
			
			}*/
	}
	
	@AfterClass
	public void tearDown() {
		//action.closeBrowser();
		
		
	}
	
	
	


}
