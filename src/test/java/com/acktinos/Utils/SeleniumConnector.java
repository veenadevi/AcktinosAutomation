package com.acktinos.Utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.Charsets;
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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.io.Files;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class SeleniumConnector {
	
	public static WebDriver webdriver;
	
	
	
	public static WebDriver getbrowserInstance(String browserType){
		
		String userDir = System.getProperty("user.dir");

		String driverPath = userDir + "\\src\\test\\resources\\drivers";
		

		if(browserType.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver",driverPath+"\\geckodriver.exe");
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			
		caps.setCapability("dom.w3c_pointer_events.enabled",true);
			
			webdriver=new FirefoxDriver(caps) ;	
		}
		
		else if(browserType.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver", driverPath+"\\IEDriverServer.exe");
			
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();

			caps.setCapability("ignoreZoomSetting", true);
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			webdriver = new InternetExplorerDriver(caps);	
		}
		
		else if(browserType.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", driverPath+"\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("headless");
	       // options.addArguments("window-size=1200x600");
			String downloadFilepath = "D:\\TestDownloads";
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", downloadFilepath);
			options.setExperimentalOption("prefs", prefs);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("elementScrollBehavior", 1);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			webdriver = new ChromeDriver(capabilities);
			//webdriver.manage().window().maximize();
			
			
		}
		else if(browserType.equalsIgnoreCase("Html")){
			webdriver= new HtmlUnitDriver();
		}
		
		
	return webdriver;	
	}
	
	public static void launchUrl(String url){
		webdriver.get(url);
		webdriver.manage().window().maximize();
	}
	
	public static WebDriver getDriver(){
		return webdriver;
	}
	
	
	
	public static WebElement getLocatorObject(String locatorString){//css=#inputname id=username
	
		WebElement locatorElement=null;	
		
		
		
		String locatorTye=locatorString.substring(0, locatorString.indexOf("=")); //css
		String Locator=locatorString.substring((locatorString.indexOf("=")+1));//#inputname
		
		
			if(locatorTye.equalsIgnoreCase("css")){
				//waitforElementToLoad(webdriver.findElement(By.cssSelector(Locator)));
				locatorElement= webdriver.findElement(By.cssSelector(Locator));
			}
			
			else if(locatorTye.equalsIgnoreCase("id")){
				//waitforElement(webdriver.findElement(By.id(Locator)));
				
				locatorElement=webdriver.findElement(By.id(Locator));
			}
			else if (locatorTye.equalsIgnoreCase("xpath")){
				locatorElement=webdriver.findElement(By.xpath(Locator))	;
			}
			else if (locatorTye.equalsIgnoreCase("linkText")){
				locatorElement=webdriver.findElement(By.linkText(Locator))	;
			}
				
			
		
	
	return locatorElement;
	}
	
	public static WebElement getChildElement(WebElement parentElement,String locatorString){
		WebElement childElement=null;	
		String locatorTye=locatorString.substring(0, locatorString.indexOf("=")); //css
		String Locator=locatorString.substring((locatorString.indexOf("=")+1));//#inputname	
		

		if(locatorTye.equalsIgnoreCase("css")){
			//waitforElement(parentElement.findElement(By.cssSelector(Locator)));
			childElement= parentElement.findElement(By.cssSelector(Locator));
		}
		
		else if(locatorTye.equalsIgnoreCase("id")){
			//waitforElement(webdriver.findElement(By.id(Locator)));
			
			childElement=parentElement.findElement(By.id(Locator));
		}
		else if (locatorTye.equalsIgnoreCase("xpath")){
			childElement=parentElement.findElement(By.xpath(Locator))	;
		}
		else if (locatorTye.equalsIgnoreCase("linkText")){
			childElement=webdriver.findElement(By.linkText(Locator))	;
		}
		
		return childElement;
		
	}
	
	public static List<WebElement>  getChildElements(WebElement parentElement,String locatorString){
		List<WebElement>  childElement=null;	
		String locatorTye=locatorString.substring(0, locatorString.indexOf("=")); //css
		String Locator=locatorString.substring((locatorString.indexOf("=")+1));//#inputname	
		

		if(locatorTye.equalsIgnoreCase("css")){
			//waitforElement(parentElement.findElement(By.cssSelector(Locator)));
			childElement= parentElement.findElements(By.cssSelector(Locator));
		}
		
		else if(locatorTye.equalsIgnoreCase("id")){
			//waitforElement(webdriver.findElement(By.id(Locator)));
			
			childElement=parentElement.findElements(By.id(Locator));
		}
		else if (locatorTye.equalsIgnoreCase("xpath")){
			childElement=parentElement.findElements(By.xpath(Locator))	;
		}
		else if (locatorTye.equalsIgnoreCase("linkText")){
			childElement=webdriver.findElements(By.linkText(Locator))	;
		}
		
		return childElement;
		
	}
	//For Multiple Object 
	public static List<WebElement> getLocatorObjects(String locatorString){//css=#inputname
		
		List<WebElement> locatorElement=new ArrayList<WebElement>();	
		
		String locatorTye=locatorString.substring(0, locatorString.indexOf("=")); //css
		String Locator=locatorString.substring((locatorString.indexOf("=")+1));//#inputname
		
		
			if(locatorTye.equalsIgnoreCase("css")){
				locatorElement= webdriver.findElements(By.cssSelector(Locator));//#inputname
				//locatorElement= webdriver.findElement(By.cssSelector(#inputname));
			}
			
			else if(locatorTye.equalsIgnoreCase("id")){
				
				
				locatorElement=webdriver.findElements(By.id(Locator));
			}
			else if (locatorTye.equalsIgnoreCase("xpath")){
				locatorElement=webdriver.findElements(By.xpath(Locator))	;
			}
				
			
		
		
		return locatorElement;
		}
	
	
	
	public static void waitforElement(WebElement element ){	
		
		WebDriverWait wait = new WebDriverWait(webdriver, 120);
		
		wait.until(ExpectedConditions.elementToBeClickable(element));
		
	
	}
	
public static void waitforElementToLoad(WebElement element ){	
		
		WebDriverWait wait = new WebDriverWait(webdriver, 120);
		
		
		wait.until(ExpectedConditions.visibilityOf(element));
		
	
	}

public static boolean waitUntilelementInvisible(String locatorString){
	
	boolean isStillSearching=true;
	WebDriverWait wait = new WebDriverWait(webdriver, 180);
	
	String locatorTye=locatorString.substring(0, locatorString.indexOf("=")); //css
	String locator=locatorString.substring((locatorString.indexOf("=")+1));//#inputname
	
	
	if(locatorTye.equalsIgnoreCase("css")){
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(locator)));
	}
	
	if(locatorTye.equalsIgnoreCase("id")){
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(locator)));
	}
	
	if(locatorTye.equalsIgnoreCase("xpath")){
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
	}
	
	isStillSearching=true;
	
	return isStillSearching;
	
	
}
	
	public static void waitForElements(final List<WebElement> element){
		
		WebDriverWait wait = new WebDriverWait(webdriver, 100);
		
		wait.until(new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				// TODO Auto-generated method stub
				return !element.isEmpty();
			}
		});
		
	}
	
	public static void doWait() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void closeBrowser(){
		
		//webdriver.quit();
	}
	
	public static void waitForPageLoaded()
	{
	    ExpectedCondition<Boolean> expectation = new
	ExpectedCondition<Boolean>() 
	    {
	        public Boolean apply(WebDriver driver)
	        {
	            return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	        }
	    };
	    Wait<WebDriver> wait = new WebDriverWait(webdriver,30);
	    try
	    {
	        wait.until(expectation);
	    }
	    catch(Throwable error)
	    {
	        Assert.assertFalse(true,"Timeout waiting for Page Load Request to complete.");
	    }
	}
	
	public static void doDragAndDropJS(String sourceIndetifier,String  dest) throws IOException {
		String userDir = System.getProperty("user.dir");

		String filePath = userDir + "\\src\\test\\resources\\testData\\";
		
		System.out.println("sourceIndetifier  "+sourceIndetifier);
		
		String fileContents = Files.toString( new File( filePath+"dragDrop.js" ), Charsets.UTF_8 );
		JavascriptExecutor js = ( JavascriptExecutor ) webdriver;
		js.executeScript( fileContents + "Drag('"+sourceIndetifier+"').simulate('"+dest+"');" );
		
		/*Actions myaction=new Actions(webdriver);
		myaction.clickAndHold(source).build().perform();
		myaction.moveToElement(dest).release(source).build().perform();
		
		//myaction.dragAndDrop(source,dest);
		
		
		//myaction.build().perform();
*/	}
	public static void doDragAndDrop(WebElement source,WebElement dest) {
		Actions myaction=new Actions(webdriver);
		myaction.clickAndHold(source).moveToElement(dest).release(source).build().perform();
		
		//myaction.dragAndDrop(source,dest).build().perform();
		
		
		
		
		//myaction.build().perform();
		
	}
public static void fclick(WebElement theElement) {
		
		((JavascriptExecutor)webdriver).executeScript("arguments[0].onclick();", theElement);
	}

public static boolean isAttribtuePresent(WebElement element, String attribute) {
    Boolean result = false;
    try {
        String value = element.getAttribute(attribute);
        if (value != null){
            result = true;
        }
    } catch (Exception e) {}

    return result;
}
public static void mouseover(WebElement theElement){
	if ((webdriver instanceof SafariDriver)) {
		try{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) webdriver;
			js.executeScript(mouseOverScript,theElement);
		}catch (Exception e){
			e.printStackTrace();
		}

	}else{
		new Actions(webdriver).moveToElement(theElement).pause(Duration.ofSeconds(60)).build().perform();
	}

}

public static void scrollToElement(WebElement element) {
	JavascriptExecutor js = (JavascriptExecutor) webdriver;
	js.executeScript("arguments[0].scrollIntoView();",element);
}

	public static void mouseOverJS(WebElement element) {
	try{
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		js.executeScript(mouseOverScript,element);
	}catch (Exception e){
		e.printStackTrace();
	}
}

public static Object executeJavaScript(WebElement theElement ) {
	JavascriptExecutor js = (JavascriptExecutor) webdriver;
	
	return ((JavascriptExecutor)webdriver).executeScript("arguments[0].textContent", theElement);
	//return js.executeScript(script);
}

public static void getScreenShot(String filenameToStore){
	File scrFile = ((TakesScreenshot)webdriver).getScreenshotAs(OutputType.FILE);
	
	// Now you can do whatever you need to do with it, for example copy somewhere
	try {
		FileUtils.copyFile(scrFile, new File(filenameToStore+".jpeg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}


	public static void selectDropDownByText(WebElement theSelectElement,String valToSelect)
	{
		
		System.out.println("theSelectElement  "+theSelectElement.getTagName());
		Select select = new Select(theSelectElement);  
		
		System.out.println("Lookijgnfor option "+valToSelect);
		// Get a list of the options 
		List<WebElement> options = select.getOptions();  
		// For each option in the list, verify if it's the one you want and then click it 
		for (WebElement option : options) {     
			if (option.getText().equalsIgnoreCase(valToSelect)) {         
				System.out.println(String.format("Text  is: %s", option.getText()));
				option.click();
				break;     
			} 
		} 

}
	
	public static void selectDropDownByValue(WebElement theSelectElement,String valToSelect)
	{
		
		System.out.println("===Select by vlaue==="+valToSelect);
		
		
		Select select = new Select(theSelectElement);  
		List<WebElement> options = select.getOptions();
		
		for (WebElement option : options) {    
			System.out.println("option.getAttribute(\"value\")"  +option.getAttribute("value"));
			if (option.getAttribute("value").trim().equalsIgnoreCase(valToSelect.trim())) {         
				System.out.println(String.format("Text  is: %s", option.getText()));
				option.click();
				break;     
			} 
		}
			
			
	//	select.selectByValue(valToSelect);
	}
	

	public static void selectDropDownByIndex(WebElement theSelectElement,int index)
	{
		
		System.out.println("===Select by Index==="+index);
		Select select = new Select(theSelectElement);  
		select.selectByIndex(index);
	}
	
	public static void selectDropDownFirstobject(WebElement theSelectElement)
	{
		
		Select select = new Select(theSelectElement);  
		List<WebElement> options = select.getOptions();  
		options.get(1).click();
		
	}
	
	public static String selectedItem(WebElement theSelectElement)
	{ 
		Select select = new Select(theSelectElement); 
	
		WebElement anElement = select.getFirstSelectedOption();
		return anElement.getText();     
	}
	public static boolean isDropDownSelected(WebElement theSelectElement)
	{ 
		Select select = new Select(theSelectElement); 
		if(select.getAllSelectedOptions().size()>0) {
		return true;	
		}
		else
		return false ;  
	}
	
	
	public static List<WebElement> getOptionsFromDropDown(WebElement theSelectElement)
	{
		Select select = new Select(theSelectElement);  
		// Get a list of the options 
		 List<WebElement> options = select.getOptions();
		 
		 return options;
		
	}
	
	public static void doPageScrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor)webdriver;
		jse.executeScript("window.scrollBy(0,250)", "");
		
	}
	
	public static void doPageScrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor)webdriver;
		jse.executeScript("window.scrollBy(0,-250)", "");
		
	}
	
	public static void switchIFrame(WebElement iframe) {
		webdriver.switchTo().frame(iframe);
		
	}
	
	public static void doContextClick(WebElement element) {
		Actions myactions = new Actions(getDriver());
		
		myactions.contextClick(element).build().perform();
		
	}
	
	 public static void waitForAngularLoad() {
		 new NgWebDriver((JavascriptExecutor) webdriver).waitForAngularRequestsToFinish();
		 /*
	        WebDriverWait wait = new WebDriverWait(webdriver,15);
	       
	        JavascriptExecutor jsExec = (JavascriptExecutor) webdriver;
	 
	        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
	 
	        //Wait for ANGULAR to load
	        ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
	                .executeScript(angularReadyScript).toString());
	 
	        //Get Angular is Ready
	        boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());
	 
	        //Wait ANGULAR until it is Ready!
	        if(!angularReady) {
	            System.out.println("ANGULAR is NOT Ready!");
	            //Wait for Angular to load
	            wait.until(angularLoad);
	        } else {
	            System.out.println("ANGULAR is Ready!");
	        }
	    */}
}
