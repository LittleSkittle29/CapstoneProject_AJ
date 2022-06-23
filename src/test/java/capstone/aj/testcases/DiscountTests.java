package capstone.aj.testcases;

import capstone.aj.library.SelectBrowser;
import capstone.aj.pages.CartPage;
import capstone.aj.pages.CheckoutPage;
import capstone.aj.pages.HomePage;
import capstone.aj.pages.ProductDetailsPage;
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

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class DiscountTests {
    WebDriver driver;

    //Page Controllers
    HomePage homePage;
    ProductDetailsPage productDetailsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    //Extent Report creation
    private static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setUpReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/DiscountTestsReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Discount Tests");
        htmlReporter.config().setReportName("Discount Tests Report");
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

    //Check if discount is applied
    @Test
    public void applyDiscountTest() throws IOException {
        test = extent.createTest("applyDiscountTest","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("1");
        productDetailsPage.addProductsToCart();
        cartPage = new CartPage(driver);
        cartPage.checkout();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterDiscountCode("MASK15");
        checkoutPage.applyDiscount();
        String actual = checkoutPage.discountApplied();
        String expected ="MASK15";

        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/DiscountApplied.png"));
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
