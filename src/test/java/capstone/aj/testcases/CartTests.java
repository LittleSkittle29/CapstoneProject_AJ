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
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CartTests {
    WebDriver driver;

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
        test = extent.createTest("VerifyPriceIsPresent","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        String actual = productDetailsPage.confirmPriceExists();
        String expected = "USD";
        Assert.assertTrue(actual.contains(expected));

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/PricePresentInCart.png"));
    }

    //Confirm Product was added to cart
    @Test(priority = 2)
    public void ConfirmProductAddedToCart() throws IOException {
        test = extent.createTest("ConfirmProductAddedToCart","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("1");
        productDetailsPage.addProductsToCart();
        cartPage = new CartPage(driver);
        String actual = cartPage.cartAmount();
        String expected = "1";
        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/ProductAddedToCart.png"));
    }

    //Check that items stay in the cart even when refreshing the webpage
    @Test(priority = 3)
    public void productsStayInCart() throws IOException {
        test = extent.createTest("productsStayInCart","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("1");
        productDetailsPage.addProductsToCart();
        driver.navigate().refresh();
        cartPage = new CartPage(driver);
        String actual = cartPage.cartAmount();
        String expected = "1";
        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/ProductStayedInCart.png"));
    }

    //Verify that an increased quantity is present in cart
    @Test(priority = 4)
    public void increaseQuantity() throws IOException {
        test = extent.createTest("increaseQuantity","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("2");
        productDetailsPage.addProductsToCart();
        cartPage = new CartPage(driver);
        String actual = cartPage.cartAmount();
        String expected = "2";
        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/CartIncreasedQuantity.png"));
    }

    //Confirm that the cart amount and product amount are the same
    @Test(priority = 5)
    public void cartAndProductAmountMatch() throws IOException {
        test = extent.createTest("cartAndProductAmountMatch","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("1");
        productDetailsPage.addProductsToCart();
        cartPage = new CartPage(driver);
        String cartAmount = cartPage.cartAmount();
        String productAmount = cartPage.productAmount();
        Assert.assertEquals(cartAmount,productAmount);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/CartAndProductAmountMatch.png"));
    }

    //Confirm that the cart can be cleared successfully
    @Test(priority = 6)
    public void clearCart() throws IOException {
        test = extent.createTest("clearCart","Test Passed");
        homePage = new HomePage(driver);
        homePage.clickProductPicture();
        productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectAdultSize();
        productDetailsPage.selectType();
        productDetailsPage.adjustQuantity("1");
        productDetailsPage.addProductsToCart();
        cartPage = new CartPage(driver);
        cartPage.removeProduct();
        String actual = cartPage.cartAmount();
        String expected = "0";
        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/CartEmpty.png"));
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
