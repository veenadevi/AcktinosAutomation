package com.acktinos.pageObjects;

import org.openqa.selenium.WebElement;

import com.acktinos.helper.*;

public class HomePage {
	
	public  WebElement  clickMoreHelp() {
		
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
		
	}
	
	public  WebElement  loadMore() {
		
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
		
	}
	
	public  WebElement  createIncident() {
		
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
		
	}

}
