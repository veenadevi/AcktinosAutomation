package com.acktinos.constants;

import java.io.File;

public class ApplicationConstants {
	
	public static final String SEPERATOR = File.separator;
	
	public static final String KEY_BROWSER_TYPE = "browserType";
	public static final String KEY_SELENIUM_HOST = "seleniumHost";
	public static final String KEY_SELENIUM_PORT = "seleniumPort";
	public static final String KEY_IS_RUN_ON_REMOTE = "isRunOnRemote";
	public static final String KEY_URL = "url";
	public static final String KEY_USERNAME = "userName";
	public static final String KEY_PASSWORD = "password";
	
	
	public static final String LOG4J_PROPERTY_FILE = SEPERATOR+"log4j.properties";
	public static final String LOGGER_LOCATION = SEPERATOR+"Logger";
	
	 //source directory constant
    public static final String SOURCE_DIR ="src";
	

	
	public static final String TEST_RESULT_FILE_LOC=SOURCE_DIR+SEPERATOR+"test"+SEPERATOR+"resources"+SEPERATOR+"testData"+SEPERATOR;
	
	public static String MASTER_BOOK="serviecnow_testcase_master";
	public static String MASTER_MODULE_CLMN_NAME="ModuleName";
	public static String MASTER_EXEFLAG_CLMN_NAME="Execution_Flag";
	
	public static String MASTER_TEST_METHOD_NAME="TestMethodName";
	public static String TEST_RESULT_PASS="Pass";
	public static String TEST_RESULT_FAIL="Fail";
	public static String TEST_METHOD_STATUS="testResult";
	 public static final String RESOURCE_LOCATION =SEPERATOR+"test"+SEPERATOR+"resources";
	 
	 public static final String LOCATOR_FILE_PATH=SOURCE_DIR+SEPERATOR+RESOURCE_LOCATION+SEPERATOR+"uiRepository";
	 public static final String LOG4J_PROPERITY_FILE_PATH=SOURCE_DIR+RESOURCE_LOCATION+SEPERATOR+"logProperties";
	 public static final String REPORTER_DIR="TestReporter"+SEPERATOR+"ATU Reporter";
	 
	 //test Configuration Excel Columns
	 
	 public static String TEST_DATA_WORKBOOK="serviecnow_testcase_master.xlsx";
	 public static String TEST_DATA_SHEETNAME="Configuration";
	 public static String TEST_ENVIRONMENT="Environment";
	 public static String TEST_URL="URL";
	 public static String TEST_BROWSER="Browser";
	 public static String TEST_USER="UserName";
	 public static String TEST_PASSWORD="Password";
	 public static String TEST_ADMIN_USER="AdminUser";
	 public static String TEST_ADMIN_PASSWORD="AdminPassword";
	 public static String TEST_ISACTIVE="isActive";
	 
	 
	

}



