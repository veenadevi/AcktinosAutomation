package com.acktinos.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ddf.EscherColorRef.SysIndexSource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.acktinos.constants.ApplicationConstants;

public class TestDataReader {
	// static Workbook masterTestCaseWorkbook = null;
	static String filePath = "src/test/resources/testData/";
	static String parantPath = System.getProperty("user.dir");

	public static Workbook getWorkBookfromFile(String FileName) {
		Workbook masterTestCaseWorkbook = null;
		String fileNameString = parantPath + File.separator + filePath + File.separator + FileName;// "infolease_master.xlsx";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileNameString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fileExtensionName = fileNameString.substring(fileNameString.indexOf("."));
		try {
			if (fileExtensionName.equals(".xlsx")) {

				masterTestCaseWorkbook = new XSSFWorkbook(fis);
			} else if (fileExtensionName.equals(".xls")) {

				masterTestCaseWorkbook = new HSSFWorkbook(fis);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return masterTestCaseWorkbook;
	}

	public static Sheet getSheetName(Workbook book, String sheetName) {

		Sheet currrentSheet = book.getSheet(sheetName);

		return currrentSheet;

	}

	public static List<String> getTestClassesList() {

		Workbook masterTestCaseWorkbook = getWorkBookfromFile(ApplicationConstants.MASTER_BOOK);
		// Fetch the MasterSheet
		Sheet masterSheet = masterTestCaseWorkbook.getSheetAt(0);

		int totalRows = masterSheet.getLastRowNum();
		int executionFlagClmnIndx = readColumnIndx(masterSheet, ApplicationConstants.MASTER_EXEFLAG_CLMN_NAME);
		int moduleClmnIndx = readColumnIndx(masterSheet, ApplicationConstants.MASTER_MODULE_CLMN_NAME);
		List<String> testCaseList = new ArrayList<String>();

		for (int i = 1; i <= totalRows; i++) {
			Row currentRow = masterSheet.getRow(i);

			// Read Each Column of current row and load the Object

			Cell executionFlag = currentRow.getCell(executionFlagClmnIndx);
			Cell moduleName = currentRow.getCell(moduleClmnIndx);
			if (executionFlag.getStringCellValue().equalsIgnoreCase("Yes")){
				//returnCellValue(executionFlag).equalsIgnoreCase("y")) {

				testCaseList.add(moduleName.getStringCellValue());
			}
		}
		System.out.println("testCaseList  size and content   " + testCaseList.size() + "   " + testCaseList.get(0));
		return testCaseList;
	}

	public static List<String> getTestMethodList(String className) {

		Workbook masterTestCaseWorkbook = getWorkBookfromFile(ApplicationConstants.MASTER_BOOK);
		// Fetch the MasterSheet
		Sheet classSheet = masterTestCaseWorkbook.getSheet(className);

		int totalRows = classSheet.getLastRowNum();
		int executionFlagClmnIndx = readColumnIndx(classSheet, ApplicationConstants.MASTER_EXEFLAG_CLMN_NAME);
		int moduleClmnIndx = readColumnIndx(classSheet, ApplicationConstants.MASTER_TEST_METHOD_NAME);
		List<String> testCaseList = new ArrayList<String>();

		for (int i = 1; i <= totalRows; i++) {
			Row currentRow = classSheet.getRow(i);
			System.out.println("curerntRowNum  " + i);
			// Read Each Column of current row and load the Object

			Cell executionFlag = currentRow.getCell(executionFlagClmnIndx);
			Cell methodName = currentRow.getCell(moduleClmnIndx);
			if (executionFlag.getStringCellValue().equalsIgnoreCase("Yes")){

				testCaseList.add(methodName.getStringCellValue());
			}
		}
		System.out.println("testMethodList  for class    " + className + "testCaseList  " + testCaseList);
		return testCaseList;
	}

	private static int readColumnIndx(Sheet DetailsSheet, String ColunName) {

		System.out.println("In Sheet " + DetailsSheet.getSheetName() + " In Cloumn " + ColunName);

		int index = -1;

		String tempColumnValue = DetailsSheet.getRow(0).getCell(0).getStringCellValue();

		int totalColumns = DetailsSheet.getRow(0).getLastCellNum();// Finding the Total Columns based on First Row

		int tempindex = totalColumns - 1;

		while (tempindex >= 0) {

			// System.out.println("tempindex --"+tempindex);
			String columnValue = DetailsSheet.getRow(0).getCell(tempindex).getStringCellValue();
			// String
			// columnValue=DetailsSheet.getRow(0).getCell(tempindex).getStringCellValue();//column,row
			if (columnValue.trim().equalsIgnoreCase(ColunName.trim())) {
				index = tempindex;

			}
			tempindex--;

		}

		return index;

	}

	private static int readRowIndx(Sheet DetailsSheet, int columnIndex,String expectedValue) {

		System.out.println("In Sheet " + DetailsSheet.getSheetName() + " In Cloumn " + columnIndex);

		int index = -1;

		//	String tempColumnValue = DetailsSheet.getRow(0).getCell(columnIndex).getStringCellValue();

		//int totalColumns = DetailsSheet.getRow(0).getLastCellNum();// Finding the Total Columns based on First Row

		int totalRows=DetailsSheet.getLastRowNum();

		int tempindex = totalRows - 1;

		while (tempindex > 0) {

			// System.out.println("tempindex --"+tempindex);
			String rowValue = DetailsSheet.getRow(tempindex).getCell(columnIndex).getStringCellValue();
			// String
			// columnValue=DetailsSheet.getRow(0).getCell(tempindex).getStringCellValue();//column,row
			if (rowValue.trim().equalsIgnoreCase(expectedValue.trim())) {
				index = tempindex;

			}
			tempindex--;

		}

		return index;

	}


	public static  void createTestConfigJson() {
		Workbook testDataBook = getWorkBookfromFile(ApplicationConstants.TEST_DATA_WORKBOOK);

		JSONObject testConfigObject= new JSONObject();

		System.out.println("testDataBook  " + testDataBook.getNumberOfSheets());
		Sheet testConfigSheet=testDataBook.getSheet("Configuration");
		int isActiveColumnIndex= readColumnIndx(testConfigSheet,"isActive");//7
		int activeRowIndex=readRowIndx(testConfigSheet, isActiveColumnIndex, "Yes");
		Row HeaderRow = testConfigSheet.getRow(0);
		Row activeRow=testConfigSheet.getRow(activeRowIndex);
		for(int i=0;i<activeRow.getLastCellNum();i++) {
			String columnName =HeaderRow.getCell(i).getStringCellValue();
			String columnValue = activeRow.getCell(i).getStringCellValue();

			System.out.println("columnName  "+columnName);
			System.out.println("columnValue  "+columnValue);

			testConfigObject.put(columnName, columnValue);
		}

		writeJsonToFile(testConfigObject,testConfigSheet.getSheetName());

	}

	public static  void createTestPagesElementsJson() {
		Workbook testDataBook = getWorkBookfromFile(ApplicationConstants.TEST_DATA_WORKBOOK);

		JSONObject pageObject= new JSONObject();
		JSONArray pageElements= null;
		JSONObject pageElementObject=null;
		int totalSheets=testDataBook.getNumberOfSheets();



		System.out.println("testDataBook  " + testDataBook.getNumberOfSheets());
		//Sheet testConfigSheet=testDataBook.getSheet("Configuration");

		for(int sheetNo=1;sheetNo<totalSheets;sheetNo++) {
			Sheet currentSheet=testDataBook.getSheetAt(sheetNo);

			System.out.println("reading Sheet "+currentSheet.getSheetName());

			int elementNameColumnIndex=readColumnIndx(currentSheet,"ElementName");

			int identifierTypeColumnIndex=readColumnIndx(currentSheet,"IndentifierType");
			int identifierColumnIndex=readColumnIndx(currentSheet,"Identifier");

			Row HeaderRow = currentSheet.getRow(0);
			JSONObject elementNameObject=null;
			JSONArray temp=new JSONArray();
			elementNameObject= new JSONObject();
			for(int i=1;i<=currentSheet.getLastRowNum();i++) {
				pageElementObject=new JSONObject();


				pageElements=new JSONArray();
				Row currentRow = currentSheet.getRow(i);
				String elementName=currentRow.getCell(elementNameColumnIndex).getStringCellValue();
				String indetifierType=currentRow.getCell(identifierTypeColumnIndex).getStringCellValue();
				String indetifier=currentRow.getCell(identifierColumnIndex).getStringCellValue();

				System.out.println("indetifier  "+indetifier);

				System.out.println("elementName  "+elementName);

				pageElementObject.put("indentifierType", indetifierType);
				pageElementObject.put("indentifier", indetifier);
				pageElements.add(pageElementObject);


				//elementNameObject.put("elementName", elementName);
				elementNameObject.put(elementName, pageElements);



			}

			pageObject.put(currentSheet.getSheetName(), elementNameObject);
			writeJsonToFile(pageObject,currentSheet.getSheetName());
		}



	}

	public static void writeJsonToFile(JSONObject jsobObject, String fileName) {
		try {


			FileWriter file = new FileWriter(parantPath + File.separator + filePath +File.separator+"Jsons"+ File.separator+fileName + ".json");

			System.out.println("file  writing "+file.toString());
			file.write(jsobObject.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static  String getTestConfigDataValue(String fieldName) {
		JSONParser parser = new JSONParser();

		String value=null;

		Object obj = null;
		try {
			obj = parser.parse(new FileReader(parantPath + File.separator + filePath +File.separator+"Jsons"+ File.separator+ApplicationConstants.TEST_DATA_SHEETNAME + ".json"));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject testConfigObject = (JSONObject) obj;
		value=testConfigObject.get(fieldName).toString();

		return value;

	}

	public static  String getTestPageElementValue(String pageName,String element) {
		JSONParser parser = new JSONParser();

		String value=null;

		Object obj = null;
		try {
			obj = parser.parse(new FileReader(parantPath + File.separator + filePath +File.separator+"Jsons"+ File.separator+pageName+ ".json"));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject testPageObject = (JSONObject) obj;

		System.out.println("testPageObject  "+testPageObject);





		JSONObject elementObject = (JSONObject) testPageObject.get(pageName);
		//String elementObjectString =(String)elementObject.get(element);
		//System.out.println("elementObjectString  "+elementObjectString);		
		//System.out.println("Details List :" + ((JSONArray) testPageObject.get(element)).size());
		Iterator<JSONObject> rowIterator = ((JSONArray) elementObject.get(element)).iterator();
		while (rowIterator.hasNext())
		{
			JSONObject elementObjectDetails = (JSONObject) rowIterator.next();

			System.out.println("elementObjectDetails  "+elementObjectDetails);
			value=elementObjectDetails.get("indentifierType").toString()+"="+elementObjectDetails.get("indentifier").toString();

		}
		System.out.println("value  "+value);
		return value;

	}


}
