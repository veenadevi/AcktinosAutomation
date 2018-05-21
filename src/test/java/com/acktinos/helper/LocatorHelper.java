package com.acktinos.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.acktinos.constants.ApplicationConstants;






public class LocatorHelper {
	 static Logger log;

	public static String fetchLocatorString() {
		log= Logger.getLogger("logfile");
		
		
		Properties locatorProperties = new Properties();
		String locatorString = null;
		String filePath = ApplicationConstants.LOCATOR_FILE_PATH;
		String parantPath = System.getProperty("user.dir");

		// Computing the full path of Locator Repostory 
		File absDirPath = new File(parantPath + File.separator + filePath);		
		
		

		if (absDirPath.isDirectory()) {
			String[] files = absDirPath.list();
			// List of Full File Name
			for (String fileName : files) {
				File fullFileName = new File(absDirPath + File.separator+fileName);

				if (!fullFileName.isDirectory()
						&& fullFileName.getAbsolutePath().endsWith(
								".properties")) {


					InputStream thePropFile;
					try {

						// File newFile = new File(fullFileName);
						thePropFile = new FileInputStream(fullFileName);
						locatorProperties.load(thePropFile);
						

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			// Property File is Loaded in to Property Object
			/**
			 * Fetch Property Key value using the strategy - Calling classname.Calling MethodName
			 */

			StackTraceElement[] elements = new Throwable().getStackTrace();
			String fetchClassName=elements[1].getClassName().substring(elements[1].getClassName().lastIndexOf(".")+1, elements[1].getClassName().length());
			String keysToGetValue = fetchClassName + "."+ elements[1].getMethodName();
			
			System.out.println("keysToGetValue "+keysToGetValue);

			locatorString = (String) locatorProperties.get(keysToGetValue);

		}

		return locatorString;

	}

}
