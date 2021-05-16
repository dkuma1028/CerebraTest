package com.cerebra.test.pageScript;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cerebra.test.utils.CommonFunc;
import com.cerebra.test.utils.HandleExcelData;

public class LoginPage {
	private WebDriver _driver;
	WebDriverWait wait;
	CommonFunc cFunc;

	
	public LoginPage(WebDriver _driver) {
		this._driver = _driver;
		wait = new WebDriverWait(_driver, 10);
		cFunc = new CommonFunc(_driver);
		PageFactory.initElements(this._driver, this);
	}
	
	@FindBy(id = "user_email")
	WebElement email_TxtBox;
	
	@FindBy(id = "user_password")
	WebElement pwd_TxtBox;
	
	@FindBy(xpath = "//input[@value='Log in']")
	WebElement login_Btn;
	
	@FindBy(xpath = "//a[contains(@href,'/password/new')]")
	WebElement forgotPwd_Link;
	
	@FindBy(xpath = "//div[text()='Invalid Email or password.']")
	WebElement invalidCredential_Txt;
	
	@FindBy(xpath = "//button[@class='close']")
	WebElement close_Btn;
	
	@FindBy(id = "navbarDropdown")
	WebElement logoutDropDwon;
	
	@FindBy(xpath = "//a[contains(@href,'/sign_out')]")
	WebElement logout_Link;

	@FindBy(xpath = "//h1[text()='Forgot your password?']")
	WebElement forgotPwdLanding_text;
	
	@FindBy(xpath = "//input[@value='Send me reset password instructions']")
	WebElement resetInstructionLink_Btn;
	
	@FindBy(xpath = "//div[contains(@class,'alert-success')]")
	WebElement alertMsg_txt;
	
	@FindBy(xpath = "//div[@id='error_explanation']//li")
	WebElement errorMsg_txt;
	
	public boolean loginFunc(String userId,String pwd){
		
		wait.until(ExpectedConditions.visibilityOf(email_TxtBox)).clear();
		wait.until(ExpectedConditions.visibilityOf(email_TxtBox)).sendKeys(userId);
		wait.until(ExpectedConditions.visibilityOf(pwd_TxtBox)).clear();
		wait.until(ExpectedConditions.visibilityOf(pwd_TxtBox)).sendKeys(pwd);
		wait.until(ExpectedConditions.visibilityOf(login_Btn)).click();
		try {
			if (_driver.getTitle().equalsIgnoreCase("Cerebra - Admin")) {
				System.out.println("Login Successful");
				return true;
			} else if (invalidCredential_Txt.isDisplayed()) {
				System.out.println("Login failed: Invalid login credentials");
				return false;
			} else {
				System.out.println("Login failed: Invalid credential message not displayed");
				return false;
			}
		}
		catch(Exception e) {
			System.out.println("Exception occured while logging in. PLease debug!!");
			return false;
		}
	}
	
	public void logout() {
		wait.until(ExpectedConditions.visibilityOf(logoutDropDwon)).click();
		wait.until(ExpectedConditions.visibilityOf(logout_Link)).click();
		assertTrue(_driver.getTitle().equalsIgnoreCase("Cerebra - Login"),"Failed while loggin out");
	}
	
	public void forgotPasswordValidation(String userId) {
		wait.until(ExpectedConditions.visibilityOf(forgotPwd_Link)).click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(forgotPwdLanding_text)).isDisplayed(),"Failed while validating the landing of forgot pwd link");
		//validate error message on clicking send button without passing user id value
		wait.until(ExpectedConditions.visibilityOf(resetInstructionLink_Btn)).click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(errorMsg_txt)).getText().equalsIgnoreCase("Email can't be blank"), "Failed while validating mandatory field error message");

		wait.until(ExpectedConditions.visibilityOf(email_TxtBox)).sendKeys(userId);
		wait.until(ExpectedConditions.visibilityOf(resetInstructionLink_Btn)).click();
		assertTrue(wait.until(ExpectedConditions.visibilityOf(alertMsg_txt)).getText().equalsIgnoreCase("You will receive an email with instructions on how to reset your password in a few minutes."),"Failed while validating the alert message for reset instructions");

	}
	
	public void validateBrokenLink(String homeUrl) {
		boolean flag = true;
		String url = "";
		HttpURLConnection httpConn = null;
		int respCode = 200;

		List<WebElement> links = _driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();
		List<String> brokenLinks = new ArrayList<String>();
		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			System.out.println(url);
			if (url == null || url.isEmpty()) {
				System.out.println("URL is not configured");
				brokenLinks.add(url);
				flag = false;
				continue;
			}
			if (!url.startsWith("https://cerebratest.com/")) {
				flag = false;
				brokenLinks.add(url);
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
			}

			try {
				httpConn = (HttpURLConnection) (new URL(url).openConnection());
				httpConn.setRequestMethod("HEAD");
				httpConn.connect();

				respCode = httpConn.getResponseCode();

				if (respCode >= 400) {
					System.out.println(url + " is a broken link");
					brokenLinks.add(url);
				} else {
					System.out.println(url + " is a valid link");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		assertTrue(flag,"There are broken/ un configured links/links for another domain in this site. Here's the list>>"+brokenLinks);
	}
}
