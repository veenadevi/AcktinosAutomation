package com.acktinos.Utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebElement;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScreenshotHelper {
	
	public static void  takeWebElementScreenShot(WebElement element,String elementName) {
		 Screenshot screenshot = new AShot().takeScreenshot(SeleniumConnector.getDriver(),element);
	       try {
			ImageIO.write(screenshot.getImage(),"JPEG",new File(System.getProperty("user.dir") +"\\target\\screenShots\\"+elementName+".jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void  takeFulScreenScreenShot(String pageName) {
		 Screenshot screenshot =  new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(SeleniumConnector.getDriver());
	       try {
			ImageIO.write(screenshot.getImage(),"JPEG",new File(System.getProperty("user.dir") +"\\target\\screenShots\\"+pageName+"_"+new SimpleDateFormat("yyyy_MM_dd_hh_mm").format(new Date())+".jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
