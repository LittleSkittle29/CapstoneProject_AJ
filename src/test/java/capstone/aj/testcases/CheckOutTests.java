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
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CheckOutTests {
    WebDriver driver;
    SoftAssert softAssert;

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
    public void setUpReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/CheckoutTestsReport.html");
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);

        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Checkout Tests");
        htmlReporter.config().setReportName("Checkout Tests Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
    }

    //Browser Manager
    @BeforeMethod
    public void launchBrowser() {
        driver = SelectBrowser.StartBrowser("Chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.alexandnova.com/");
    }

    @Test(priority = 1)
    public void PaymentMethodsAvailable() throws IOException, InterruptedException {
        softAssert = new SoftAssert();
        test = extent.createTest("PaymentMethodsAvailable","Test Passed");
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
        checkoutPage.completeInfoForm("John","Fink", "test@gmail.com","123drive","wood", "80232");
        checkoutPage.continueButton();
        Thread.sleep(5000);
        checkoutPage.continueButton();
        String cc = checkoutPage.creditCardAvailable();
        String sp = checkoutPage.shopPayAvailable();
        String pp = checkoutPage.payPalAvailable();

        String cce = "Credit card";
        String spe = "Shop Pay";
        String ppe = "PayPal";

        softAssert.assertEquals(cc,cce);
        softAssert.assertEquals(sp,spe);
        softAssert.assertEquals(pp,ppe);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/PaymentMethodsAvailable.png"));
    }

    //Test that all mandatory fields must be filled to complete checkout
    @Test(priority = 2)
    public void checkoutMandatoryFields() throws InterruptedException, IOException {
        test = extent.createTest("checkoutMandatoryFields","Test Passed");
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
        checkoutPage.completeInfoForm("John","Fink", "test@gmail.com","123drive","wood", "80232");
        checkoutPage.continueButton();
        Thread.sleep(5000);
        checkoutPage.continueButton();

        checkoutPage.switchToCreditCardFrame();
        checkoutPage.creditCardForm("3698");
        checkoutPage.creditCardForm("5214");
        checkoutPage.creditCardForm("769");
        checkoutPage.creditCardForm("874");
        driver.switchTo().parentFrame();

        checkoutPage.switchToNameFrame();
        checkoutPage.nameForm("John Fink");
        driver.switchTo().parentFrame();

        checkoutPage.switchToExpiryFrame();
        checkoutPage.expiryForm("07");
        checkoutPage.expiryForm("25");
        driver.switchTo().parentFrame();

        checkoutPage.switchToSecureNumFrame();
        checkoutPage.secureNumForm("");
        driver.switchTo().parentFrame();

        checkoutPage.continueButton();
        Thread.sleep(5000);
        String actual = checkoutPage.paymentErrorMessage();
        String expected = "Your payment details couldn’t be verified. Check your card details and try again.";

        Assert.assertEquals(actual,expected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/CheckoutMandatoryFields.png"));
    }

    @Test(priority = 3)
    public void failedCheckout() throws InterruptedException, IOException {
        softAssert = new SoftAssert();
        test = extent.createTest("failedCheckout","Test Passed");
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
        checkoutPage.completeInfoForm("John","Fink", "test@gmail.com","123drive","wood", "80232");
        checkoutPage.continueButton();
        Thread.sleep(5000);
        checkoutPage.continueButton();

        checkoutPage.switchToCreditCardFrame();
        checkoutPage.creditCardForm("3698");
        checkoutPage.creditCardForm("5214");
        checkoutPage.creditCardForm("769");
        checkoutPage.creditCardForm("874");
        driver.switchTo().parentFrame();

        checkoutPage.switchToNameFrame();
        checkoutPage.nameForm("John Fink");
        driver.switchTo().parentFrame();

        checkoutPage.switchToExpiryFrame();
        checkoutPage.expiryForm("06");
        checkoutPage.expiryForm("5");
        driver.switchTo().parentFrame();

        checkoutPage.switchToSecureNumFrame();
        checkoutPage.secureNumForm("222");
        driver.switchTo().parentFrame();

        checkoutPage.continueButton();
        Thread.sleep(5000);

        String pError = checkoutPage.paymentErrorMessage();
        String pErrorExpected = "Your payment details couldn’t be verified. Check your card details and try again.";
        softAssert.assertEquals(pError,pErrorExpected);

        String cError = checkoutPage.cardNumErrorMessage();
        String cErrorExpected = "Enter a valid card number";
        softAssert.assertEquals(cError,cErrorExpected);

        String expError = checkoutPage.expiryErrorMessage();
        String expErrorExpected = "Enter a valid card expiry date";
        softAssert.assertEquals(expError,expErrorExpected);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("src/test/resources/capstone.aj.screenshots/CheckoutFailed.png"));
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
