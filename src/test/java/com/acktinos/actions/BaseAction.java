package com.acktinos.actions;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.SystemUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.acktinos.constants.ApplicationConstants;
import com.acktinos.helper.ATUReportHelper;
import com.acktinos.helper.JSoupHelper;
import com.acktinos.helper.LocatorHelper;
import com.acktinos.helper.ScreenshotHelper;
import com.acktinos.helper.SeleniumConnector;
import com.acktinos.helper.TestDataReader;
import com.acktinos.pageObjects.LoginPage;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;




public class BaseAction {
	
	static String parantPath = System.getProperty("user.dir");
	
	static String currentURL=null;
	
	public  void initializeBrowser(String browserName,String url)  {    	
    	SeleniumConnector.getbrowserInstance(browserName);
    	currentURL=url;
    	SeleniumConnector.launchUrl(url);
    	SeleniumConnector.doWait();
    	  
    	ATUReportHelper.captureScreen("initializeBrowser");
    	
    	//ScreenshotHelper.takeFulScreenScreenShot();
    
    }
	
	private void captureScreen(String string) {
		// TODO Auto-generated method stub
		
	}

	public void closeBrowser() {
		SeleniumConnector.closeBrowser();
	}
	
	public void doLogin(String user_Name,String pass_word) throws InterruptedException{
		
		
		/*System.out.println("currentURL  "+currentURL);
		Document resultPage=JSoupHelper.doPageScrap(currentURL);
		
		Element userName=resultPage.body().selectFirst("input");
		
		
		userName.cssSelector();//
		String userNameXpath="body.windows.chrome.ng-scope.fixed-header:nth-child(2)";
		
		//Trying to Find Way to intract with Element of JSoup
		System.out.println(userName.cssSelector());
		WebElement userNameLement=SeleniumConnector.getDriver().findElement(By.cssSelector(userName.cssSelector()));
		
		userNameLement.sendKeys(user_Name);
		*/
		
		

		String thisPage="LoginPage";
		
		Thread.sleep(2000);
		
		WebElement userName=getWebElement(thisPage,"typeUserName");		
		userName.sendKeys(user_Name);
		
		WebElement password=getWebElement(thisPage,"typePassword");	
		password.sendKeys(pass_word);
		
		ScreenshotHelper.takeWebElementScreenShot(password, "password");
		
		ScreenshotHelper.takeFulScreenScreenShot(thisPage);
		password.submit();
		
		SeleniumConnector.waitForPageLoaded();
		
		
	}
	
public void doAdminLogin(String user_Name,String pass_word) throws InterruptedException{/*
		
		LoginPage loginPage=new LoginPage();
		
		Thread.sleep(1000);
		
		System.out.println("For Admin Login");
		
		WebElement iframeElement =loginPage.AdminIframe();
		
		SeleniumConnector.getDriver().switchTo().frame(iframeElement);
		
		Thread.sleep(2000);
		WebElement userName=loginPage.typeAdminUserName();			
		userName.sendKeys(user_Name);
		
		WebElement password=loginPage.typeAdminPassord();
		password.sendKeys(pass_word);
		
		
		WebElement submitButton=loginPage.buttonAdminLogin();
		submitButton.click();
		
		SeleniumConnector.waitForPageLoaded();
		
		
	*/}
	

	

	
	public void doSleep() {
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
		String formattedDate = dateFormat.format(new Date()).toString();
		System.out.println(formattedDate);
		
		return formattedDate;
	}
	
	
	public WebElement getWebElement(String pageName,String webelement) {
		return SeleniumConnector.getLocatorObject(TestDataReader.getTestPageElementValue(pageName,webelement))	;
	}

}
