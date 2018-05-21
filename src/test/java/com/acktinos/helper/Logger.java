package com.acktinos.helper;


import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

import com.acktinos.constants.ApplicationConstants;







/**
 *  Logger Wrapper Class
 * 
 *
 */
public class Logger {
	
				private static boolean isConfigured = false;
	   
				/**
                 * 
                 * @return org.apache.log4j.Logger
                 */
                public static org.apache.log4j.Logger getLogger(String logFileName) {
                	
                    if (!isConfigured){
                         	PropertyConfigurator.configure("log4j.properties");
                         	isConfigured = true;
                    }
                	return org.apache.log4j.Logger.getLogger(getClassName());
                }
                
                
                /**
                 * 
                 * @return org.apache.log4j.Logger
                 */
                public static org.apache.log4j.Logger getLogger() {
                	
                	String filePath = ApplicationConstants.LOG4J_PROPERITY_FILE_PATH;
            		String parantPath = System.getProperty("user.dir");
                	
                    if (!isConfigured){
                         	PropertyConfigurator.configure(parantPath+File.separator+filePath+File.separator+"log4j.properties");
                         	isConfigured = true;
                    }
                	return org.apache.log4j.Logger.getLogger(getClassName());
                }
                
             
                /**
                 * 
                 * @return the class name as a String object
                 */
                public synchronized static String getClassName() {
              		try {
						return Thread.currentThread().getContextClassLoader().loadClass(Thread.currentThread().getStackTrace()[4].getClassName()).getName();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					return "";
	
                }
                
                     
                /**
                 * 
                 * @param message
                 */
                public static void trace(Object message) {
                	getLogger().trace(message);Reporter.log(message+"");
                }
                
                /**
                 * 
                 * @param message
                 * @param t
                 */
                public static void trace(Object message, Throwable t) {
                	getLogger().trace(message, t);
                }
                
                /**
                 * 
                 * @return
                 */
                public static boolean isTraceEnabled() {
                    return getLogger().isTraceEnabled();
                }
                
                /**
                 * 
                 * @param message
                 */
                public static void debug(Object message) {
                	getLogger().debug(message);Reporter.log(message+"");
                }
                
                /**
                 * 
                 * @param message
                 * @param t
                 */
                public static void debug(Object message, Throwable t) {
                	getLogger().debug(message, t);
                }
                
                /**
                 * 
                 * @param message
                 */
                public static void error(Object message) {
                	getLogger().error(message);Reporter.log(message+"");
                }
                
                /**
                 * 
                 * @param message
                 * @param t
                 */
                public static void error(Object message, Throwable t) {
                	getLogger().error(message, t);
                }
                
                /**
                 * 
                 * @param message
                 */
                public static void fatal(Object message) {
                	getLogger().fatal(message);Reporter.log(message+"");
                }
                
                /**
                 * 
                 * @param message
                 * @param t
                 */
                public static void fatal(Object message, Throwable t) {
                	getLogger().fatal(message, t);
                }
                
                /**
                 * 
                 * @param message
                 */
                public static void info(Object message) {
                	getLogger().info(message);Reporter.log(message+"");
                }
                
                /**
                 * 
                 * @param message
                 * @param t
                 */
                public static void info(Object message, Throwable t) {
                	getLogger().info(message, t);
                }
                
                /**
                 * 
                 * @return
                 */
                public static boolean isDebugEnabled() {
                    return getLogger().isDebugEnabled();
                }
                
                /**
                 * 
                 * @param level
                 * @return
                 */
                public static boolean isEnabledFor(Priority level) {
                    return getLogger().isEnabledFor(level);
                }
                
                /**
                 * 
                 * @return
                 */
                public static boolean isInfoEnabled() {
                    return getLogger().isInfoEnabled();
                }
                
                
                /**
                 * 
                 * @param level
                 */
                public static void setLevel(Level level) {
                	getLogger().setLevel(level);
                }
                
                /**
                 * 
                 * @param message
                 */
                public static void warn(Object message) {
                	getLogger().warn(message);Reporter.log(message+"");
                }
                
                /**
                 * 
                 * @param message
                 * @param t
                 */
                public static void warn(Object message, Throwable t) {
                	getLogger().warn(message, t);
                }
 }

