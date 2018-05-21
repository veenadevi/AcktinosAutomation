package com.acktinos.pageObjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.acktinos.helper.*;



public class AdminUpdateIncidentPage {
	
	public WebElement getServicefilterText() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public List<WebElement> getFilterResultModuleList() {
		 return SeleniumConnector.getLocatorObjects(LocatorHelper.fetchLocatorString());
	}
	
	public List<WebElement> getServiceDeskIncidents() {
		 return SeleniumConnector.getLocatorObjects(LocatorHelper.fetchLocatorString());
	}
	
	public List<WebElement> getFilterResultList() {
		 return SeleniumConnector.getLocatorObjects(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement incidentListFrame() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	public WebElement incidentTable() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	public List<WebElement> incidentList() {
		 return SeleniumConnector.getLocatorObjects(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement incidenStatusSelect() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement incidentWorkNotes() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public WebElement pageHeader() {
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
	}
	
	public List<WebElement> contextClickList(){
		 return SeleniumConnector.getLocatorObjects(LocatorHelper.fetchLocatorString());
		
	}
	
	public WebElement userDropDown(){
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
		
	}
	
	public WebElement logOutLink(){
		 return SeleniumConnector.getLocatorObject(LocatorHelper.fetchLocatorString());
		
	}

}
