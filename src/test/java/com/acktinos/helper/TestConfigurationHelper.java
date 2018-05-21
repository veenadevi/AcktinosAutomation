package com.acktinos.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.acktinos.constants.ApplicationConstants;

public class TestConfigurationHelper {
	
	//static Workbook masterTestCaseWorkbook = null;
		static String filePath = "src\\test\\resources\\testData\\";
		static String parantPath = System.getProperty("user.dir");
		
		public static Workbook getWorkBookfromFile(String FileName){
			Workbook masterTestCaseWorkbook = null;
			String fileNameString=parantPath+File.separator+filePath+File.separator+FileName;//"infolease_master.xlsx";
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(fileNameString);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String fileExtensionName = fileNameString.substring(fileNameString.indexOf("."));
			 try {
			 if(fileExtensionName.equals(".xlsx")){
				
					masterTestCaseWorkbook= new XSSFWorkbook(fis);
				} 
			 else if(fileExtensionName.equals(".xls")){
					
					masterTestCaseWorkbook= new HSSFWorkbook(fis);
				} 
			 }
			 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return masterTestCaseWorkbook;
		}
		
		public static Sheet getSheetName(Workbook book,String sheetName){
			
			Sheet currrentSheet=book.getSheet(sheetName);
			
			return currrentSheet;
			
		}
		
		public static Map<String,String> getTestConfigurationList(){
			
			Map<String,String> testConfigurationMap= new HashedMap<String,String>();
			
			Workbook masterTestCaseWorkbook=getWorkBookfromFile("serviecnow_testcase_master.xlsx");
			//Fetch the MasterSheet
			Sheet masterSheet=masterTestCaseWorkbook.getSheet("Configuration");
			
			 Row currentRow =masterSheet.getRow(1);
			
			return testConfigurationMap;
		}
		
		public static List<String> getTestClassesList(){
			
			Workbook masterTestCaseWorkbook=getWorkBookfromFile("serviecnow_testcase_master.xlsx");
			//Fetch the MasterSheet
			Sheet masterSheet=masterTestCaseWorkbook.getSheet("Master");
			
			int totalRows=masterSheet.getLastRowNum();
			int executionFlagClmnIndx=readColumnIndx(masterSheet,ApplicationConstants.MASTER_EXEFLAG_CLMN_NAME);
			int moduleClmnIndx=readColumnIndx(masterSheet,ApplicationConstants.MASTER_MODULE_CLMN_NAME);
			List<String> testCaseList= new ArrayList<String>();
			
			 for(int i=1;i<=totalRows;i++){
				 Row currentRow =masterSheet.getRow(i);
		            
		            //Read Each Column of current row and load the Object
		            
		            Cell executionFlag=currentRow.getCell(executionFlagClmnIndx); 
		            Cell moduleName=currentRow.getCell(moduleClmnIndx ); 
		            if(returnCellValue(executionFlag).equalsIgnoreCase("y")){
		            	
		            	testCaseList.add(returnCellValue(moduleName));
		            }
			 }
			System.out.println("testCaseList  size and content   "+testCaseList.size()+testCaseList.get(0));
			return testCaseList;
		}
		
		
		private  static int readColumnIndx(Sheet DetailsSheet,String ColunName){
			
			int index=-1;
			
			String tempColumnValue=DetailsSheet.getRow(0).getCell(0).getStringCellValue();
			
			int totalColumns=DetailsSheet.getRow(0).getLastCellNum();//Finding the Total Columns based on First Row
			
			System.out.println("totalColumns  :"+totalColumns);
			
			//start reading from the last column
			
			int tempindex=totalColumns-1;

			
			while(tempindex>=0){
				String columnValue=DetailsSheet.getRow(0).getCell(tempindex).getStringCellValue();//column,row			
				if(columnValue.equalsIgnoreCase(ColunName)){			
				index=tempindex;
				
			}
			tempindex--;
			
			}
			
			return index;
			
			
		}
		
	private static  String returnCellValue(Cell cell){
			
			String cellValue = null;		
			
			
			switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	            cellValue=cell.getStringCellValue();
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:
	        	
	        	cellValue=String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	        	cell.setCellType(Cell.CELL_TYPE_STRING);
	        	cellValue=String.valueOf(cell.getStringCellValue());
	            break; 
	        case Cell.CELL_TYPE_BLANK:
	        	cellValue = "";
	            break;
	        case Cell.CELL_TYPE_ERROR:
	        	cellValue = "";
	            break;
			}
			
			return cellValue;
			
		}
		
	public static String getCellValueAsString(String workbookName,String sheetName,String methodName,String columnName){
		//String fileNameString=parantPath+File.separator+filePath+File.separator+workbookName;
		Workbook testDataBook=getWorkBookfromFile(workbookName);
		
		System.out.println("testDataBook  "+testDataBook.getNumberOfSheets());
		Sheet currentTestCaseSheet=getSheetName(testDataBook, sheetName);
		int totalRows=currentTestCaseSheet.getLastRowNum();
		
		System.out.println("totalRows  :"+totalRows);
		int currentClmnIndx=readColumnIndx(currentTestCaseSheet,columnName);
		int testMethodClmnIndx=readColumnIndx(currentTestCaseSheet,"Test_Case_ID");
		 Cell currentCell=null;
		 String currentCellValue = null;
		 for(int i=1;i<=totalRows;i++){
			 Row currentRow =currentTestCaseSheet.getRow(i);
	            
	           if(returnCellValue(currentRow.getCell(testMethodClmnIndx)).equals(methodName)){           
	          System.out.println("Same row for mentioned test method");
	        	   currentCell=currentRow.getCell(currentClmnIndx); 
	             currentCellValue=returnCellValue(currentCell);
	             break;
	            }
		 }
		System.out.println("Returning cell value from test Data "+currentCellValue);
		return currentCellValue;
	}


}
