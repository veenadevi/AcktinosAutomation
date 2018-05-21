package com.acktinos.helper;

import org.openqa.selenium.WebElement;



import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class ATUReportHelper {
	
	public static void addScreenShotOfElement(String stepName,WebElement element) {
		ATUReports.add(stepName, LogAs.INFO, new CaptureScreen(element));
		
		
	}
	
	public static void captureScreen(String stepName) {
		ATUReports.add(stepName, LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		
		
	}

}
