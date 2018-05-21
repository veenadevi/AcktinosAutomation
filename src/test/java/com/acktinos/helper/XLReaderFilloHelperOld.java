package com.acktinos.helper;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class XLReaderFilloHelperOld {
	
	private final Fillo fillo;
    private final String filePath;

    private Connection connection;

    public XLReaderFilloHelperOld(String xlFilePath) {
        fillo = new Fillo();
        this.filePath = xlFilePath;
    }
    
    public void addTestMethods(String query) {
        try {
            connection = fillo.getConnection(this.filePath);
            Recordset recordset = connection.executeQuery(query);
           
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
    
    
    public void fetchPageElements(String query) {
        try {
            connection = fillo.getConnection(this.filePath);
            Recordset recordset = connection.executeQuery(query);
           
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
    
    public void executePage(String query) {
        try {
            connection = fillo.getConnection(this.filePath);
            Recordset recordset = connection.executeQuery(query);
           
        } catch (FilloException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }


}
