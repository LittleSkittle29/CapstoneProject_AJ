package capstone.aj.testcases;

import capstone.aj.library.SelectBrowser;
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
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class RegistrationPageTests {
    WebDriver driver;
    SoftAssert softAssert;

    //Page Controllers
    HomePage homePage;
    AccountLoginPage accountLoginPage;
    RegistrationPage registrationPage;

    //Extent Report creation
    private static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setUpReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/RegistrationPageReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Registration Page Tests");
        htmlReporter.config().setReportName("Registration Page Report");
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

    //Register new User test
    @Test(priority = 1)
    public void newUserRegistrationPage() throws IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("newUserRegistrationPage","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.clickRegisterButton();
        registrationPage = new RegistrationPage(driver);

        String expected = "Sign up";
        String actual = registrationPage.getRegistrationTitleText();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/RegistrationPage.png"));
    }

    //Check if new user was created successfully
    @Test(priority = 2)
    public void verifyNewUserRegistered() throws InterruptedException, IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("verifyNewUserRegistered","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.clickRegisterButton();
        registrationPage = new RegistrationPage(driver);
        registrationPage.completeRegistrationForm("John","Fink","jfink15@gmail.com","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);

        String expected = "My account";
        String actual = accountLoginPage.verifyRegistration();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/RegistrationSuccessful.png"));
    }

    //Check that a valid email was entered in user creation process
    @Test(priority = 3)
    public void emailValidation() throws InterruptedException, IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("verifyEmailValidationError","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.clickRegisterButton();
        registrationPage = new RegistrationPage(driver);
        registrationPage.completeRegistrationForm("John","Fink","testAtgmail.com","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        String expected = "Sorry! Please try that again.";
        String actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        accountLoginPage.clickRegisterButton();
        registrationPage.completeRegistrationForm("John","Fink","test@gmailcom","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        accountLoginPage.clickRegisterButton();
        registrationPage.completeRegistrationForm("John","Fink","test@gmail","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        accountLoginPage.clickRegisterButton();
        registrationPage.completeRegistrationForm("John","Fink","@gmail","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/RegistrationEmailInvalid.png"));
    }

    //Check for failure if required fields are not filled
    @Test(priority = 4)
    public void requiredFieldsBlank() throws InterruptedException, IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("requiredFieldsBlank","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.clickRegisterButton();
        registrationPage = new RegistrationPage(driver);
        registrationPage.completeRegistrationForm("","Fink","test100@gmail.com","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        String expected = "Sorry! Please try that again.";
        String actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        accountLoginPage.clickRegisterButton();
        registrationPage.completeRegistrationForm("John","","test100@gmail.com","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        accountLoginPage.clickRegisterButton();
        registrationPage.completeRegistrationForm("John","Fink","","P@ssword");
        registrationPage.register();
        Thread.sleep(30000);
        actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        accountLoginPage.clickRegisterButton();
        registrationPage.completeRegistrationForm("John","Fink","test100@gmail.com","");
        registrationPage.register();
        Thread.sleep(30000);
        actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/RegistrationRequiredFieldsBlank.png"));
    }

    //Check that password rules were enforced during user creation
    @Test(priority = 5)
    public void passwordRules() throws InterruptedException, IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("requiredFieldsBlank","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickAccountButton();
        accountLoginPage = new AccountLoginPage(driver);
        accountLoginPage.clickRegisterButton();
        registrationPage = new RegistrationPage(driver);
        registrationPage.completeRegistrationForm("John","Fink","test76@gmail.com","passw");
        registrationPage.register();
        Thread.sleep(30000);
        String expected = "Sorry! Please try that again.";
        String actual = accountLoginPage.confirmErrorMessage();
        softAssert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/RegistrationPasswordRules.png"));
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
