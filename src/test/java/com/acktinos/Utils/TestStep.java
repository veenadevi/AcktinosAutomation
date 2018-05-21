package com.acktinos.Utils;

import java.util.function.BiConsumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestStep {
	 private final BiConsumer < String, String > consumer;
	 private final String param;
	// private final By selector;

	 private final String locatorString;
	 WebElement ele=null;
	 
	 
	 public TestStep( String locatorString, String action, String param) {
	        //this.selector = By.name(selector);
	     //   this.driver = driver;
		 System.out.println("action  "+action);
	        this.consumer = TestCaseExecutor.get(action);
	        this.param = param;
	        this.locatorString=locatorString;
	    }
	 
	 public void perform() {
	        //ele = SeleniumConnector.getLocatorObject(locatorString);
		 
		 SeleniumConnector.doWait();
		 
		 System.out.println("locatorString in perfomr   "+locatorString);
		 System.out.println("param in perfomr   "+param);
	        this.consumer.accept(locatorString, param);
	    }
}
