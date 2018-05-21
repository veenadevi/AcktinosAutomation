package com.acktinos.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.acktinos.helper.SeleniumConnector;

public class TestCaseExecutor {

	static WebDriver driver;
	
	private static BiConsumer<WebElement, String> OPEN_BROWSER = (element, browserName) -> {
		driver=SeleniumConnector.getbrowserInstance(browserName);
    };
    
    private static BiConsumer<WebElement, String> NAVIGATE_TO = (element, url) -> {
		driver.get(url);
    };
	
	private static BiConsumer<WebElement, String> SET_VALUE = (element, param) -> {
		SeleniumConnector.waitforElementToLoad(element);
		element.sendKeys(param);
    };
	
    private static BiConsumer<WebElement, String> CLICK = (element, param) -> {
    	SeleniumConnector.waitforElementToLoad(element);
		element.click();
    };
    
    private static BiConsumer<WebElement, String> SUBMIT = (element, param) -> {
    	SeleniumConnector.waitforElementToLoad(element);
		element.submit();
    };
	
    private static BiConsumer<WebElement, String> SELECT_BY_TEXT = (element, param) -> {
       
        SeleniumConnector.selectDropDownByText(element, param);
    };
    
private static Map<String, BiConsumer<WebElement, String>> map = null;
    
    static {
        map = new HashMap<String, BiConsumer<WebElement, String>>();
        map.put("SetValue", SET_VALUE);
        map.put("Click", CLICK);
        map.put("SelectByText", SELECT_BY_TEXT);
        map.put("Submit", SUBMIT);
        map.put("openBrowser", OPEN_BROWSER);
        map.put("navigateTo", NAVIGATE_TO);
    }
    
    public static BiConsumer<WebElement, String> get(String action){
        return map.get(action);
    }
}
	
