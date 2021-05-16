package com.cerebra.test.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunc {
	private WebDriver _driver;
	WebDriverWait wait;
	
	public CommonFunc(WebDriver _driver) {
		this._driver = _driver;
		wait = new WebDriverWait(_driver, 30);
	}
	
	public void selectValueFromDropDown(WebElement dropDown,String valueToSelect) {
		wait.until(ExpectedConditions.visibilityOf(dropDown));
		Select select = new Select(dropDown);
		select.selectByVisibleText(valueToSelect);
	}
	
	public static String timeString() throws Exception {
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
	    String formattedDate = myDateObj.format(myFormatObj);
	    return formattedDate;		
	}
}
