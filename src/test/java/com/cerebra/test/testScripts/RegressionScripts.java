package com.cerebra.test.testScripts;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cerebra.config.TestBase;
import com.cerebra.test.pageScript.LoginPage;
import com.cerebra.test.pageScript.ManageWarehousePage;
import com.cerebra.test.utils.HandleExcelData;

public class RegressionScripts extends TestBase {
	HandleExcelData excelPageObj = new HandleExcelData();
	
	@DataProvider(name = "getData")
	public Object[][] returnPizzaTypeData() throws Exception{
		return excelPageObj.readFromExcel("Sheet1");
	}
	
 
    @Test(dataProvider="getData")
    public void loginTest(String userId,String password) throws Exception {
    	LoginPage loginPageObj = new LoginPage(driver);
    	if(loginPageObj.loginFunc(userId, password)) {
    		excelPageObj.writeToExcel("Sheet1", "Result (Pass/Fail)", userId, password, "Pass");
    		loginPageObj.logout();
    	}
    	else {
    		excelPageObj.writeToExcel("Sheet1", "Result (Pass/Fail)", userId, password, "Fail");
    	}
    }
    
    @Test
    public void verifyForgotPwdFeature() throws Exception {
    	LoginPage loginPageObj = new LoginPage(driver);
    	loginPageObj.forgotPasswordValidation("gurdeep.singh@cerebrahealth.com");
    }
    
    @Test
    public void verifyLoginPageLinks() throws Exception {
    	LoginPage loginPageObj = new LoginPage(driver);
    	loginPageObj.validateBrokenLink("https://cerebratest.com/");
    }
    
    @Test
    public void validateManageWarehouse() throws Exception {
    	ManageWarehousePage manageWaregousePageObj = new ManageWarehousePage(driver);
    	LoginPage loginPageObj = new LoginPage(driver);
    	loginPageObj.loginFunc("gurdeep.singh@cerebrahealth.com", "testsleep1");
    	manageWaregousePageObj.navigateToManageWareHouse();
    	manageWaregousePageObj.createNUpdateWareHouse();
    	loginPageObj.logout();
    }

}
