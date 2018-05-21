package com.acktinos.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class XLReaderFilloHelper {
	
	private final Fillo fillo;
    private final String filePath;

    private Connection connection;
    

	static String xmlfilePath = "src/test/resources/testData/";
	static String baseFilePath = System.getProperty("user.dir");
	static String fullPath=baseFilePath+File.separator+xmlfilePath;

    public XLReaderFilloHelper(String xlFilePath) {
        fillo = new Fillo();
        this.filePath = xlFilePath;
    }
    
    public List<String> getAllClasses(){
    	 try {
    		 
    		 System.out.println(" get All classes from   "+this.filePath);
			connection = fillo.getConnection(this.filePath);
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 connection.getMetaData().getTableNames().size();
    	 
    	 return connection.getMetaData().getTableNames();
    }
    
    public List<TestMethod> fetchTestMethods(String query) {
    	List<TestMethod> testMethods = new ArrayList<TestMethod>();
        try {
            connection = fillo.getConnection(this.filePath);
            System.out.println("query  "+query);
            Recordset recordset = connection.executeQuery(query);
          testMethods=this.createTestMethods(recordset);
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return testMethods;
    }
    
    
    public String fetchPageElements(String query) {
    	String selectedElement=null;
        try {
            connection = fillo.getConnection(this.filePath);
            Recordset recordset = connection.executeQuery(query);
            
            while (recordset.next()) {
            	String elementType=recordset.getField("IdentifierType");
            	
            	String elementIdentifier=recordset.getField("Element");
            	
            	selectedElement=elementType+"="+elementIdentifier;
            	
            }
           
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return selectedElement;
        
    }
    
    
    public void executeLoginPage(String pageName,String query) {
    	List < TestStep > steps = new ArrayList < > ();
    	String browserType=null;
        try {
        	
        	System.out.println("readign from file executeLoginPage "+this.filePath);
            connection = fillo.getConnection(this.filePath);
            String browserTypeQuery="Select * from "+ pageName +" where FieldName= 'browserType'";
            
            System.out.println("browserTypeQuery  "+browserTypeQuery);
            Recordset browserRecordset = connection.executeQuery(browserTypeQuery);
            
            System.out.println("browserRecordset  "+browserRecordset.getCount());
            while (browserRecordset.next()) {
            	browserType	=browserRecordset.getField("Value");
            	System.out.println("browserType  "+browserType);
            	String action=browserRecordset.getField("Action");
            	TestStep step = new TestStep(null, action, browserType);
            	steps.add(step);
            }
            
            System.out.println("query  to get Other Elements lOgin "+query);
            Recordset recordset = connection.executeQuery(query);
            while (recordset.next()) {
            String fieldName=recordset.getField("FieldName");	
            String Action=recordset.getField("Action");
            String Value=recordset.getField("Value");
            TestStep step = new TestStep(fieldName, Action, Value);
        	steps.add(step);
            
            }
           
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        steps.stream() .forEach(TestStep::perform);
    }

    
    
    
    public void executePage(String pageName,String moduleName,String query) {
    	List < TestStep > steps = new ArrayList < > ();
    	String loginQuery=null;
        try {
           // connection = fillo.getConnection(this.filePath);
            
            if(moduleName.equalsIgnoreCase("User")) {
            	loginQuery="Select * from UserLogin";	
            	executeLoginPage("UserLogin",loginQuery);
            }
            connection = fillo.getConnection(this.filePath);
            Recordset recordset = connection.executeQuery(query);
            while (recordset.next()) {
            String fieldName=recordset.getField("FieldName");	
            String Action=recordset.getField("Action");
            String Value=recordset.getField("Value");
            
            String elementQuery="Select * from "+ pageName +" where FieldName= '"+fieldName+"'";
            
            XLReaderFilloHelper toreadElement=new XLReaderFilloHelper(fullPath+"ElementReposity.xlsx");
            String selectedElement=toreadElement.fetchPageElements(elementQuery);
            TestStep step = new TestStep(elementQuery, Action, Value);
        	steps.add(step);
            }
           
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        steps.stream()
        .forEach(TestStep::perform);
        
    }

public List < TestMethod > createTestMethods(Recordset recordset) {
	List < TestMethod > methodList = new ArrayList < > ();

	try {
		while (recordset.next()) {

		    String testCaseId = recordset.getField("TestId");
		    String testMethodName = recordset.getField("TestMethodName");
		    String moduleName = recordset.getField("ModuleName");

		    //create new TestStep
		    TestMethod testMethod = new TestMethod(testCaseId, testMethodName, moduleName);

		    methodList.add(testMethod);
		}
	} catch (FilloException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return methodList;
}
}
