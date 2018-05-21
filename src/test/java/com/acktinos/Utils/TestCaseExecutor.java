package com.acktinos.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.acktinos.helper.SeleniumConnector;

public class TestCaseExecutor {

	static WebDriver driver;
	
	private static BiConsumer<String, String> OPEN_BROWSER = (element, browserName) -> {
		driver=SeleniumConnector.getbrowserInstance(browserName);
    };
    
    private static BiConsumer<String, String> NAVIGATE_TO = (element, url) -> {
		driver.get(url);
    };
	
	private static BiConsumer<String, String> SET_VALUE = (locatorString, param) -> {
		WebElement element=SeleniumConnector.getLocatorObject(locatorString);
		element.sendKeys(param);
    };
	
    private static BiConsumer<String, String> CLICK = (locatorString, param) -> {
    	WebElement element=SeleniumConnector.getLocatorObject(locatorString);
    	SeleniumConnector.waitforElementToLoad(element);
		element.click();
    };
    
    private static BiConsumer<String, String> SUBMIT = (locatorString, param) -> {
    	WebElement element=SeleniumConnector.getLocatorObject(locatorString);
    	SeleniumConnector.waitforElementToLoad(element);
		element.submit();
    };
	
    private static BiConsumer<String, String> SELECT_BY_TEXT = (locatorString, param) -> {
    	WebElement element=SeleniumConnector.getLocatorObject(locatorString);
    	SeleniumConnector.waitforElementToLoad(element);
        SeleniumConnector.selectDropDownByText(element, param);
    };
    
private static Map<String, BiConsumer<String, String>> map = null;
    
    static {
        map = new HashMap<String, BiConsumer<String, String>>();
        map.put("SetValue", SET_VALUE);
        map.put("Click", CLICK);
        map.put("SelectByText", SELECT_BY_TEXT);
        map.put("Submit", SUBMIT);
        map.put("openBrowser", OPEN_BROWSER);
        map.put("navigateTo", NAVIGATE_TO);
    }
    
    public static BiConsumer<String, String> get(String action){
        return map.get(action);
    }
}
	
