package com.acktinos.pageObjects;

import org.openqa.selenium.WebElement;

import com.acktinos.helper.*;

public class CreateIncidentPage {
	
	public WebElement UrgencyDropDown() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}

	
	public WebElement UrgencyValue() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement descriptionText() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement submitButton() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement newIncidentNumber() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
}
