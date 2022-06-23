package capstone.aj.testcases;

import capstone.aj.library.SelectBrowser;
import capstone.aj.pages.HomePage;
import capstone.aj.pages.SearchResultsPage;
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

public class SearchTests {
    WebDriver driver;
    SoftAssert softAssert;

    //Page Controllers
    HomePage homePage;
    SearchResultsPage searchResultsPage;

    //Extent Report creation
    private static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setUpReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/SearchTestsReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Search Tests");
        htmlReporter.config().setReportName("Search Tests Report");
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

    //Verify Successful product search
    @Test(priority = 1)
    public void ProductSearchSuccessTest() throws IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("ProductSearchSuccessTest","Test Passed");
        homePage = new HomePage(driver);
        homePage.inputArgInSearchField("baby shoes");
        homePage.clickSearchButton();
        searchResultsPage = new SearchResultsPage(driver);
        String actual = searchResultsPage.actualSearchResults();
        String expected ="baby shoes";
        softAssert.assertTrue(actual.contains(expected));

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/SearchSuccessful.png"));
    }

    //Verify if search bar is blank website will display popular items instead
    @Test(priority = 2)
    public void ProductSearchFailTest() throws IOException {
        test = extent.createTest("ProductSearchFailTest","Test Failed");
        homePage = new HomePage(driver);
        homePage.clickSearchButton();
        searchResultsPage = new SearchResultsPage(driver);
        String actual = searchResultsPage.searchResultsForNoResults();
        System.out.println(actual);
        String expected = "No results found. Showing top popular products you might want to consider...";
        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/SearchFailure.png"));
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
