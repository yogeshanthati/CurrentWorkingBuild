package com.pa.qa.testcases;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pa.qa.base.TestBase;
import com.pa.qa.pages.*;
import com.pa.qa.reportlistener.CaptureScreenShot;
import com.pa.qa.util.Constants;
import com.pa.qa.util.Log;
import com.pa.qa.util.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class TestCases 
{
    HomePage HomePage;
    SingleItemAddPage SingleItemAddPage;
    MyAccountPageRegisterAndLogin MyAccountPageRegisterAndLogin;
    String sheetName="Login";
    String sheetName1="Login1";
    @BeforeTest
    public void setUp()
    {
        TestBase.intialization();
     
    }
     
    @Test(alwaysRun = true)
    public void HomePageNavigateTest() 
    {
    	 Log.info("Started HomePageImageNavigateTest");
    Constants.test =  Constants.extent.startTest("HomePageNavigateTest");
    	HomePage=new HomePage();
    	HomePage=HomePage.verifyNewArrivals();
    	SingleItemAddPage=HomePage.verifyNewArrivalsNavigation();	
    	 Log.info("ended HomePageImageNavigateTest");
    }
    @Test(alwaysRun = true)
    public void HomePageTest()
    {
    	 Log.info("Started HomePageTestCase");
    Constants.test =  Constants.extent.startTest("HomePageTest");
    	HomePage=new HomePage();
    	HomePage=HomePage.VerifyHomePageSlides();
    	HomePage=HomePage.verifyNewArrivals();
    	Log.info("ended HomePageTestCase");
    }
    @Test( dataProvider="getLogInTestData",alwaysRun = true)
	public  void LogInToApp(String uname, String pwd){
    	 Log.info("Started LogInTest");
		 Constants.test =  Constants.extent.startTest("LogInToApp");
		 MyAccountPageRegisterAndLogin=new MyAccountPageRegisterAndLogin();
		MyAccountPageRegisterAndLogin	=MyAccountPageRegisterAndLogin.LogIn(uname, pwd);
		 Log.info("ended LogInTest");
	}
    
    
    
    @AfterMethod
    public void getResult(ITestResult result) throws IOException
    {
    	
        if(result.getStatus() == ITestResult.FAILURE)
        {
            String screenShotPath = CaptureScreenShot.captureScreen( Constants.driver, "ScreenName");
            Constants.test.log(LogStatus.FAIL, result.getThrowable());
            Constants.test.log(LogStatus.FAIL, "Snapshot below: " +  Constants.test.addScreenCapture(screenShotPath));
        }
        Constants.extent.endTest(Constants.test);
       
    }  
    @DataProvider
	public Object[][] getLogInTestData(){
		Object data[][] = TestUtil.getTestData(sheetName1);
		return (data);
	}    
    @AfterTest
    public void tearDown()
    {
    	Constants.driver.quit();
    	 Constants.extent.close();
    }
}