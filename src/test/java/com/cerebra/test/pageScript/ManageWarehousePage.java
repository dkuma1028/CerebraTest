package com.cerebra.test.pageScript;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.cerebra.test.utils.CommonFunc;

public class ManageWarehousePage {
	private WebDriver _driver;
	WebDriverWait wait;
	CommonFunc cFunc;

	
	public ManageWarehousePage(WebDriver _driver) {
		this._driver = _driver;
		wait = new WebDriverWait(_driver, 10);
		cFunc = new CommonFunc(_driver);
		PageFactory.initElements(this._driver, this);
	}
	
	@FindBy(xpath = "//a[text()='Inventory']")
	WebElement inventory_Link;
	
	@FindBy(xpath = "//a[text()='Manage Warehouses']")
	WebElement manageWareHouse_Link;
	
	@FindBy(xpath = "//h1[text()='Warehouses']")
	WebElement manageWareHouseLanding_text;

	@FindBy(xpath = "//a[contains(@href,'/warehouses/new')]")
	WebElement new_Link;
	
	@FindBy(xpath = "//h1[text()='New Warehouse']")
	WebElement newWareHouseLanding_text;
	
	@FindBy(id = "warehouse_name")
	WebElement warehouseName_txt;
	
	@FindBy(id = "warehouse_address")
	WebElement warehouseAdd_txt;
	
	@FindBy(id = "warehouse_city")
	WebElement warehouseCity_txt;
	
	@FindBy(id = "warehouse_postal_code")
	WebElement warehousePostalCode_txt;
	
	@FindBy(id = "warehouse_province")
	WebElement warehouseProvince_DrpDwn;
	
	@FindBy(xpath = "//input[@value='Create Warehouse']")
	WebElement createWarehouse_Btn;
	
	@FindBy(xpath = "//div[@id='error_explanation']//li")
	WebElement errorMsg_txt;
	
	@FindBy(xpath = "//p[text()='Warehouse was successfully created.']")
	WebElement successMsg_txt;
	
	@FindBy(xpath = "//strong[text()='Name:']/..")
	WebElement nameTxt_value;
	
	@FindBy(xpath = "//strong[text()='Address:']/..")
	WebElement addTxt_value;
	
	@FindBy(xpath = "//strong[text()='City:']/..")
	WebElement cityTxt_value;
	
	@FindBy(xpath = "//strong[text()='Postal code:']/..")
	WebElement PCTxt_value;
	
	@FindBy(xpath = "//strong[text()='Province:']/..")
	WebElement provinceTxt_value;
	
	@FindBy(xpath = "//a[text()='Edit']")
	WebElement edit_Link;
	
	@FindBy(xpath = "//h1[text()='Editing Warehouse']")
	WebElement editWareHouseLanding_text;
	
	@FindBy(xpath = "//input[@value='Update Warehouse']")
	WebElement updateWarehouse_Btn;
	
	@FindBy(xpath = "//p[text()='Warehouse was successfully updated.']")
	WebElement successUpdateMsg_txt;

	
	public void navigateToManageWareHouse() {
		wait.until(ExpectedConditions.visibilityOf(inventory_Link)).click();
		wait.until(ExpectedConditions.visibilityOf(manageWareHouse_Link)).click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(manageWareHouseLanding_text)).isDisplayed(), "Failed while navigating to Manage ware house link");
	}
	
	public void createNUpdateWareHouse() throws Exception {
		Map<String, String> warehouseData = new HashMap<String, String>();
		String nameofWarehouse = "Cerebra_"+new CommonFunc(_driver).timeString();
		warehouseData.put("name", nameofWarehouse);
		warehouseData.put("address", "Canada");
		warehouseData.put("city", "Toronto");
		warehouseData.put("province", "Ontario");
		warehouseData.put("postalCode", "66777");
		
		//click on new link and enter data
		wait.until(ExpectedConditions.visibilityOf(new_Link)).click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(newWareHouseLanding_text)).isDisplayed(), "Failed while navigating to Manage ware house link");

		warehouseAdd_txt.sendKeys(warehouseData.get("address"));
		warehouseCity_txt.sendKeys(warehouseData.get("city"));
		warehousePostalCode_txt.sendKeys(warehouseData.get("postalCode"));
		new CommonFunc(_driver).selectValueFromDropDown(warehouseProvince_DrpDwn, warehouseData.get("province"));
		createWarehouse_Btn.click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(errorMsg_txt)).getText().equalsIgnoreCase("Name can't be blank"), "Failed while validating mandatory field error message");
		
		warehouseName_txt.sendKeys(warehouseData.get("name"));
		createWarehouse_Btn.click();
		
		assertTrue(wait.until(ExpectedConditions.visibilityOf(successMsg_txt)).isDisplayed(), "Failed while validating warehouse creation message");
		
		//validate data of ware house created
		validateDate(warehouseData);
		
		//edit data
		wait.until(ExpectedConditions.visibilityOf(edit_Link)).click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(editWareHouseLanding_text)).isDisplayed(), "Failed while navigating to Manage ware house link");
		//validate data 
		SoftAssert sAsrt = new SoftAssert();
		sAsrt.assertEquals(warehouseName_txt.getAttribute("value"), warehouseData.get("name"),"Failed while validating name value in edit window");
		sAsrt.assertEquals(warehouseAdd_txt.getAttribute("value"), warehouseData.get("address"),"Failed while validating address value in edit window");
		sAsrt.assertEquals(warehouseCity_txt.getAttribute("value"), warehouseData.get("city"),"Failed while validating name city in edit window");
		sAsrt.assertEquals(warehousePostalCode_txt.getAttribute("value"), warehouseData.get("postalCode"),"Failed while validating postal code value in edit window");
		sAsrt.assertEquals(new Select(warehouseProvince_DrpDwn).getFirstSelectedOption().getText(), warehouseData.get("province"),"Failed while validating province value in edit window");
		sAsrt.assertAll();
		//update name value 
		warehouseName_txt.clear();
		warehouseName_txt.sendKeys(nameofWarehouse+"_updated");
		warehouseData.put("name",nameofWarehouse+"_updated");
		
		updateWarehouse_Btn.click();
		
		assertTrue(wait.until(ExpectedConditions.visibilityOf(successUpdateMsg_txt)).isDisplayed(), "Failed while validating warehouse update message");
		
		//validate data of ware house created
		validateDate(warehouseData);
		
	}
	
	public void validateDate(Map<String, String> expectedDataMap) {
		SoftAssert sAsrt = new SoftAssert();
		String expProvinceData = "";
		sAsrt.assertEquals(nameTxt_value.getText().split(":")[1].trim(), expectedDataMap.get("name"),"Failed while validating name value");
		sAsrt.assertEquals(addTxt_value.getText().split(":")[1].trim(), expectedDataMap.get("address"),"Failed while validating address value");
		sAsrt.assertEquals(cityTxt_value.getText().split(":")[1].trim(), expectedDataMap.get("city"),"Failed while validating city value");
		sAsrt.assertEquals(PCTxt_value.getText().split(":")[1].trim(), expectedDataMap.get("postalCode"),"Failed while validating postal code value");
		if(expectedDataMap.get("province").equalsIgnoreCase("Ontario"))
			expProvinceData = "ON";
		sAsrt.assertEquals(provinceTxt_value.getText().split(":")[1].trim(), expProvinceData,"Failed while validating province value");
		sAsrt.assertAll();
		
	}
	
	
	
	
	
}
