package com.acktinos.pageObjects;

import org.openqa.selenium.WebElement;

import com.acktinos.helper.*;

public class LoginPage {
	
	public WebElement typeUserName() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement typePassord() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	
	public WebElement AdminIframe() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	public WebElement typeAdminUserName() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement typeAdminPassord() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement doSubmit() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement buttonAdminLogin() {
		return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}


}
