package com.acktinos.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.IAlterSuiteListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.acktinos.Utils.TestMethod;
import com.acktinos.Utils.XLReaderFilloHelper;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class CommonTestListener implements IAlterSuiteListener{

	TestNG myTestNG = new TestNG();
	XmlSuite mySuite ,newSuite;
	String parantPath = this.getClass().getPackage().getName();

	static String xmlfilePath = "src/test/resources/testData/";
	static String baseFilePath = System.getProperty("user.dir");
	static String fullPath=baseFilePath+File.separator+xmlfilePath;

	public void alter(List<XmlSuite> suites) {
		// TODO Auto-generated method stub



		mySuite = suites.get(0);	

		// List<String> testClasses=TestConfigurationHelper.getTestClassesList();


		List<XmlTest> currentTests=mySuite.getTests();
		List<XmlTest> newtests=new  ArrayList<>();

		XmlTest myTest = currentTests.get(0);

		List< XmlClass> testClassesList=myTest.getClasses();
		XLReaderFilloHelper xlReader=new XLReaderFilloHelper(fullPath+"TestMethodMaster.xlsx"); 

		List<String> classNames=xlReader.getAllClasses();

		System.out.println("testClassesList  "+classNames.size());
		
		String query="Select * from TestCases where IsActive = 'Y'";//Query from TestClass Sheet
	
		XLReaderFilloHelper xlReader1=new XLReaderFilloHelper(fullPath+"TestMethodMaster.xlsx"); 
		List<TestMethod>  methodsToExecute=xlReader1.fetchTestMethods(query);
		
		
		
		
		 XmlClass currentClass = null;
			try {
				currentClass = new XmlClass(addTestMethodDynamically(methodsToExecute));
						//new XmlClass(this.getClass().getClassLoader().loadClass(parantPath+"."+"ParentTest"));
			} catch (CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		myTest.getClasses().add(currentClass);
	}



	
	public <T> Class<T> addTestMethodDynamically(List<TestMethod> newMethodNameList) throws CannotCompileException 
	{

		ClassPool pool = ClassPool.getDefault();
		// String newMethodName="CreateIncident";
		pool.importPackage("org.testng");
		pool.importPackage("org.testng.annotations");

		CtClass testClass = null;
		try {
			testClass = pool.getCtClass("com.acktinos.tests.ParentTest");
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			testClass.setSuperclass(pool.get("com.acktinos.tests.CommonTest"));
			testClass.stopPruning(true);
			testClass.defrost();
			
			for(TestMethod method:newMethodNameList ) {
				String newMethodName=method.testMethodName;
				String moduleName=method.moduleName;
				
				System.out.println("newMethodName  "+newMethodName);
			
			CtMethod eventMethod = CtNewMethod.make("public void " + newMethodName + " () {doPageExecution(\""+newMethodName+"\",\""+moduleName+"\"); }",testClass);


			ClassFile ccFile = testClass.getClassFile();
			ConstPool constpool = ccFile.getConstPool();

			//Adding @Test Annotation to the Method

			AnnotationsAttribute attr = new AnnotationsAttribute(testClass.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
			Annotation annot = new Annotation("org.testng.annotations.Test", testClass.getClassFile().getConstPool());			

			System.out.println("Adding Test for method===="+eventMethod.getName());
			attr.addAnnotation(annot);
			eventMethod.getMethodInfo().addAttribute(attr);

			testClass.getClassFile().addAttribute(attr);
			testClass.addMethod(eventMethod);
			
			}
			testClass.writeFile(".");
		/*	testClass.toClass(this.getClass().getClassLoader(), 
				    this.getClass().getProtectionDomain());*/

		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testClass.freeze();
		
		return  testClass.toClass();

	}
	
	

}
