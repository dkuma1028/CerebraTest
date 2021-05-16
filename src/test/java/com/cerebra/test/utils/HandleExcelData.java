package com.cerebra.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.sun.xml.internal.fastinfoset.tools.XML_SAX_StAX_FI;

public class HandleExcelData {


	
	public String[][] readFromExcel(String sheetName) throws Exception{
		String[][] dataToReturn = null;
		String XLSX_FILE_PATH = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\DataSheet.xlsx";
		File file = new File(XLSX_FILE_PATH);
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(inputStream);


//		Fetch the sheet 
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(0);
		int noOfRows = sheet.getPhysicalNumberOfRows();
		dataToReturn = new String[noOfRows-1][2];
		//get column numbers of user id and password
		int noOfCols = row.getLastCellNum();
		int userIdCol = 0;
		int pwdCol = 0;
		for(int i = 0;i<noOfCols;i++) {
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase("User Id"))
				userIdCol = i;
			else if(row.getCell(i).getStringCellValue().equalsIgnoreCase("Password"))
				pwdCol = i;
			else
				continue;
		}
  	   	for(int i = 1;i<noOfRows;i++) {
  	   		String userId = sheet.getRow(i).getCell(userIdCol).getStringCellValue();
  	   		String password = sheet.getRow(i).getCell(pwdCol).getStringCellValue();
  	   		dataToReturn[i-1][0] = userId;
  	   		dataToReturn[i-1][1] = password;
  	   	}
  	   	
//  	   	System.out.println("The data returned is>>"+dataToReturn);
			
		workbook.close();
		return dataToReturn;
	}

	public void writeToExcel(String sheetName,String columnName,String userId,String password,String dataToWrite) throws Exception{

		String XLSX_FILE_PATH = System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\DataSheet.xlsx";
		File file = new File(XLSX_FILE_PATH);
		FileInputStream inputStream = new FileInputStream(file);
		
		Workbook workbook = new XSSFWorkbook(inputStream);

	    
		Sheet sheet = workbook.getSheet(sheetName);    
		Row row = sheet.getRow(0);
		int lastCellNum = row.getLastCellNum();
		int noOfRows = sheet.getPhysicalNumberOfRows();
		//get column numbers of column to update i e 'Result'
		int noOfCols = row.getLastCellNum();
		int resultColNumber = 0;
		int rowNumberToUpdate = 0;
		for(int i = 0;i<noOfCols;i++) {
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase("Result (Pass/Fail)"))
				resultColNumber = i;
			else
				continue;
		}
		
		//get row number to update
		for(int i = 1;i<noOfRows;i++) {
			if(sheet.getRow(i).getCell(0).getStringCellValue().equals(userId) && sheet.getRow(i).getCell(1).getStringCellValue().equals(password))
				rowNumberToUpdate = i;
			else
				continue;
		}
		
		sheet.getRow(rowNumberToUpdate).createCell(resultColNumber);
		sheet.getRow(rowNumberToUpdate).getCell(resultColNumber).setCellValue(dataToWrite);
		FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\DataSheet.xlsx");
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}
	


}
