package com.acktinos.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.IAlterSuiteListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.acktinos.Utils.TestMethod;
import com.acktinos.Utils.XLReaderFilloHelper;
import com.acktinos.helper.TestConfigurationHelper;

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

public class CommonTestListener2 implements IAlterSuiteListener{

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
		 
		// List< XmlClass> testClassesList=myTest.getClasses();
		 XLReaderFilloHelper xlReader=new XLReaderFilloHelper(fullPath+"TestMethodMaster.xlsx"); 
			
			List<String> classNames=xlReader.getAllClasses();
			
		 System.out.println("testClassesList  "+classNames.size());
		 XmlClass clazz;
		 /*for(String className :classNames){
		 try {
			System.out.println("creating new class "+className);	
			//Class<?> addXmlRootAnnotationDynamicly(className);
			} catch (InstantiationException | IllegalAccessException | NotFoundException | CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }*/
		 
		 for(String className :classNames){
			 
			 XmlClass currentClass = null;
			try {
				currentClass = new XmlClass(addXmlRootAnnotationDynamicly(className));
						//ClassLoader.getSystemClassLoader().loadClass(parantPath+"."+className));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 System.out.println("currentClass.getName()  "+currentClass.getName());
			// if(currentClass.getName().endsWith("ITILUserTests")){
				 System.out.println("Adding methods to "+className);
				 
				 String query="Select * from " +className +" where IsActive = 'Y'";
				 
				 System.out.println("query  "+query);
				 
				 XLReaderFilloHelper xlReader1=new XLReaderFilloHelper(fullPath+"TestMethodMaster.xlsx"); 
				List<TestMethod>  methodsToExecute=xlReader1.fetchTestMethods(query);
				List<XmlInclude> testMethods=currentClass.getIncludedMethods();
				
				System.out.println("Methods Alread "+testMethods.size());
				
				System.out.println("Methods fetched "+methodsToExecute.size());
				for(TestMethod newMethod:methodsToExecute) {
					String methodName=newMethod.testMethodName;
					XmlInclude newMthod=new XmlInclude(methodName);
					
					newMthod.setDescription(newMethod.testCaseId);
					
					
					testMethods.add(newMthod);
				}
				
				System.out.println("testMethods  in ITILUserTests"+testMethods.size());
				
				currentClass.setIncludedMethods(testMethods);
				myTest.getClasses().add(currentClass);
				
				
				
			 //}
			 
			 /*else if(currentClass.getName().endsWith("AdminUserTestCase")){
				 
				 String query="Select * from AdminUserTestCase where IsActive = 'Y'";
				 
				 XLReaderFilloHelper xlReader2=new XLReaderFilloHelper(fullPath+"TestMethodMaster.xlsx"); 
				List<TestMethod>  methodsToExecute=xlReader2.fetchTestMethods(query);
				List<XmlInclude> testMethods=currentClass.getIncludedMethods();
				
				System.out.println("before add new method  "+testMethods.size());
				for(TestMethod newMethod:methodsToExecute) {
					String methodName=newMethod.testMethodName;
					XmlInclude newMthod=new XmlInclude(methodName);
					
					newMthod.setDescription(newMethod.testCaseId);
					
					testMethods.add(newMthod);
				}
				
				currentClass.setIncludedMethods(testMethods);
				
				
				
				
				
			 }*/
			 
			 
			}
		 
		
		
		
		 
		 System.out.println("currentTests have  "+currentTests.get(0).getClasses().get(0).getIncludedMethods().size());
		 
		 
		 mySuite.setTests(currentTests);
		 suites.clear();
		 suites.add(mySuite);
		 
		 System.out.println("mySuite   :"+mySuite.toString());
		 
		 System.out.println("suites   :"+suites.get(0).getTests().get(0).getClasses().get(0).getIncludedMethods().get(0).getName());
		

		 
	}
	
	public <T>  Class<T> addXmlRootAnnotationDynamicly(String className) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException
	{
		
		 ClassPool pool = ClassPool.getDefault();
		 String newMethodName="CreateIncident";
		 pool.importPackage("org.testng");
		 pool.importPackage("org.testng.annotations");
		 
		 CtClass testClass=pool.makeClass(className);
		 
		 testClass.setSuperclass(pool.get("com.acktinos.tests.CommonTest"));
		 try {
			
					 //pool.getCtClass("com.acktinos.tests.ITILUserTests");
			 CtMethod eventMethod = CtNewMethod.make("public void " + newMethodName + " () { System.out.println ("+"\"reached\""+");}",testClass);
					 
			// testClass.addMethod(eventMethod);
			 ClassFile ccFile = testClass.getClassFile();
			    ConstPool constpool = ccFile.getConstPool();
			 
			 
			 
			 AnnotationsAttribute attr = new AnnotationsAttribute(testClass.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
				Annotation annot = new Annotation("org.testng.annotations.Test", testClass.getClassFile().getConstPool());
			
			 //  AnnotationsAttribute attr = new AnnotationsAttribute(constpool, null);
			//    Annotation annot = new Annotation("org.testng.annotations.Test", constpool);
			    
			    System.out.println("annot  "+annot.getTypeName());
			    
			    //CtMethod methodSetEid = testClass.getDeclaredMethod(newMethodName);
			    
			    System.out.println("Addin gTest for method===="+eventMethod.getName());
			    attr.addAnnotation(annot);
			    eventMethod.getMethodInfo().addAttribute(attr);
			    
			   // testClass.addMethod(eventMethod);
			    
			  testClass.getClassFile().addAttribute(attr);
			    testClass.addMethod(eventMethod);	
				

			 // generate the class
			   // dynamiqueBeanClass = 
			     
			 
			    
		 } catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  testClass.toClass(this.getClass().getClassLoader(), this.getClass().getProtectionDomain());
		 
		 
		 
	}
	
	

}
