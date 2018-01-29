package com.pa.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.pa.qa.reportlistener.Log4j;
import com.pa.qa.util.Log;
import com.pa.qa.util.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public abstract class TestBase extends Constants {	
	public static Properties prop;
	protected  abstract  void  VerifyValidPage();
	protected abstract void WaitForPageLoad();
	
	public TestBase(){
	PageFactory.initElements(driver, this);
	VerifyValidPage();
	WaitForPageLoad();
	}
	public static void intialization(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("src/main/resources/testconfig.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		try {
			Log4j.createLog();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 Constants.extent = new ExtentReports(System.getProperty("user.dir") + "/TestResults/Extentreports/PAExtent"+Constants.TestResult_Path+".html", true);
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver.exe");	
			driver = new ChromeDriver(); 
			Log.info("Driver Intiallized");
			driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		}
		else if(browserName.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", "src/main/resources/com/pa/qa/BrowserSpecDrivers/chromedriver.exe");	
			driver = new FirefoxDriver(); 
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		Log.info("Url Has Entered");
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);	
	}
	
	
}
