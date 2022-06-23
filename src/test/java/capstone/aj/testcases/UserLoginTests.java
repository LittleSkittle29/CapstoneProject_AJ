package capstone.aj.testcases;

import capstone.aj.library.SelectBrowser;
import capstone.aj.pages.AccountDashboardPage;
import capstone.aj.pages.AccountLoginPage;
import capstone.aj.pages.HomePage;
import capstone.aj.pages.RegistrationPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class UserLoginTests {
    WebDriver driver;
    SoftAssert softAssert;

    //Page Controllers
    HomePage homePage;
    AccountLoginPage accountLoginPage;
    AccountDashboardPage accountDashboardPage;

    //Extent Report creation
    private static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setUpReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/UserLoginReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("User Login Tests");
        htmlReporter.config().setReportName("User Login Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
    }

    //Browser Manager
    @BeforeMethod
    public void launchBrowser(){
        driver = SelectBrowser.StartBrowser("Chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.alexandnova.com/");
    }

    //Check that a registered user can log in successfully
    @Test(priority = 1)
    public void VerifySuccessfulUserLogin() throws IOException, InterruptedException {
        softAssert = new SoftAssert();
        test = extent.createTest("VerifySuccessfulUserLogin","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.inputArgInEmailField("jfink07@gmail.com");
        accountLoginPage.inputArgInPasswordField("P@ssword");
        accountLoginPage.clickLoginButton();
        accountDashboardPage = new AccountDashboardPage(driver);
        Thread.sleep(30000);
        String expected = "Welcome, John";
        String actual = accountDashboardPage.actualWelcomeMessage();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/LoginSuccessful.png"));
    }

    //Check that incorrect email will result in failure to log in
    @Test(priority = 2)
    public void VerifyFailedUserLogin() throws InterruptedException, IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("VerifyFailedUserLogin","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.inputArgInEmailField("jfink00@gmail.com");
        accountLoginPage.inputArgInPasswordField("P@ssword");
        accountLoginPage.clickLoginButton();
        String expected = "Sorry! Please try that again.";
        String actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/LoginFailed.png"));
    }

    //close driver
    @AfterMethod
    public void closeBrowser(){
        driver.quit();
    }

    //compile report
    @AfterSuite
    public void tearDown(){
        extent.flush();
    }
}
