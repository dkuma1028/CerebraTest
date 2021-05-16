package com.cerebra.config;

import static org.testng.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestBase {
	public static WebDriver driver;
	
	
	@BeforeTest
	@Parameters("URL")
	public void setUp(String url) {
		System.setProperty("webdriver.chrome.driver",
                "src/test/resources/chromedriver/windows/chromedriver.exe");
		driver = new ChromeDriver(); 
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
