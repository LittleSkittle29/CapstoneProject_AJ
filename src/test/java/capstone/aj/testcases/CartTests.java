package capstone.aj.testcases;

import capstone.aj.library.SelectBrowser;
import capstone.aj.pages.CartPage;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CartTests {
    WebDriver driver;
    SoftAssert softAssert;

    //Page Controllers
    HomePage homePage;
    ProductDetailsPage productDetailsPage;
    CartPage cartPage;

    //Extent Report creation
    private static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setUpReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/CartTestsReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Cart Tests");
        htmlReporter.config().setReportName("Cart Tests Report");
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

    //Verify that the price is present on the product details page
    @Test(priority = 1)
    public void VerifyPriceIsPresent() throws IOException {
        //test = extent.createTest("VerifyPriceIsPresent","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        String actual = productDetailsPage.confirmPriceExists();
        String expected = "USD";
        Assert.assertTrue(actual.contains(expected));

        //File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/PricePresent.png"));
    }

    //Confirm Product was added to cart
    @Test(priority = 2)
    public void ConfirmProductAddedToCart(){
        //test = extent.createTest("ConfirmProductAddedToCart","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("1");
        productDetailsPage.addProductsToCart();
        cartPage = new CartPage(driver);
        String actual = cartPage.cartAmount();
        System.out.println(cartPage.cartAmount());
        String expected = "1";
        Assert.assertEquals(actual,expected);

        //File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/ProductAddedToCart.png"));
    }

}
