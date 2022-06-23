package capstone.aj.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class CheckoutPage {
    WebDriver driver;

    //Identifiers for Web Elements
    @FindBy(id = "checkout_reduction_code")
    WebElement discountField;
    @FindBy(className = "reduction-code__text")
    WebElement discountApplied;
    @FindBy(name = "checkout[submit]")
    WebElement applyButton;
    @FindBy(name = "checkout[shipping_address][first_name]")
    WebElement fNameField;
    @FindBy(name = "checkout[shipping_address][last_name]")
    WebElement lNameField;
    @FindBy(name = "checkout[email]")
    WebElement emailField;
    @FindBy(name = "checkout[shipping_address][address1]")
    WebElement addressField;
    @FindBy(name = "checkout[shipping_address][city]")
    WebElement cityField;
    @FindBy(name = "checkout[shipping_address][zip]")
    WebElement zipField;
    @FindBy(id = "continue_button")
    WebElement continueButton;
    @FindBy(xpath = "//fieldset/div[1]/div[2]/label")
    WebElement creditCardOption;
    @FindBy(xpath = "//fieldset/div[4]/div[2]/label/img")
    WebElement shopPayOption;
    @FindBy(xpath = "//fieldset/div[6]/div[2]/label/img")
    WebElement payPalOption;
    @FindBy(xpath = "*//iframe[contains(@id,'card-fields-number')]")
    WebElement creditCardIFrame;
    @FindBy(id = "number")
    WebElement cardNumField;
    @FindBy(xpath = "*//iframe[contains(@id,'card-fields-name')]")
    WebElement nameIFrame;
    @FindBy(id = "name")
    WebElement nameField;
    @FindBy(xpath = "*//iframe[contains(@id,'card-fields-expiry')]")
    WebElement expiryIFrame;
    @FindBy(id = "expiry")
    WebElement expiryField;
    @FindBy(xpath = "*//iframe[contains(@id,'card-fields-verification')]")
    WebElement secureNumIFrame;
    @FindBy(id = "verification_value")
    WebElement secureNumField;
    @FindBy(xpath = "//form/div[1]/div[2]/div[2]/div/p")
    WebElement paymentError;
    @FindBy(id = "error-for-number")
    WebElement cardNumError;
    @FindBy(id = "error-for-expiry")
    WebElement expiryError;

    //Checkout Page Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //function for entering discount code
    public void enterDiscountCode(String arg) {
        discountField.sendKeys(arg);
    }

    //Function for Apply Discount Code
    public void applyDiscount() {
        applyButton.submit();
    }

    //Function for checking if discount present
    public String discountApplied() {
        return discountApplied.getText();
    }

    //Function for filling out info form
    public void completeInfoForm(String fName, String lName, String email, String address, String city, String zip) {
        fNameField.clear();
        fNameField.sendKeys(fName);
        lNameField.clear();
        lNameField.sendKeys(lName);
        emailField.clear();
        emailField.sendKeys(email);
        addressField.clear();
        addressField.sendKeys(address);
        cityField.clear();
        cityField.sendKeys(city);
        zipField.clear();
        zipField.sendKeys(zip);
    }

    //Function for continuing purchase process
    public void continueButton() {
        continueButton.click();
    }

    //Function for checking Credit Card Option
    public String creditCardAvailable() {
        return creditCardOption.getText();
    }

    //Function for checking Shop Pay Option
    public String shopPayAvailable() {
        return shopPayOption.getAttribute("alt");
    }

    //Function for checking PayPal Option
    public String payPalAvailable() {
        return payPalOption.getAttribute("alt");
    }

    //Function for switching Iframe
    public void switchToCreditCardFrame() {
        driver.switchTo().frame(creditCardIFrame);
    }

    //Function for entering Credit Card Number
    public void creditCardForm(String arg) {
        cardNumField.sendKeys(arg);
    }

    //Function for switching Iframe
    public void switchToNameFrame() {
        driver.switchTo().frame(nameIFrame);
    }

    //Function for entering Credit Card Name
    public void nameForm(String arg) {
        nameField.sendKeys(arg);
    }

    //Function for switching Iframe
    public void switchToExpiryFrame() {
        driver.switchTo().frame(expiryIFrame);
    }

    //Function for entering Credit Card Expiration Date
    public void expiryForm(String arg) {
        expiryField.sendKeys(arg);
    }

    //Function for switching Iframe
    public void switchToSecureNumFrame() {
        driver.switchTo().frame(secureNumIFrame);
    }

    //Function for entering Credit Card Security Number
    public void secureNumForm(String arg) {
        secureNumField.sendKeys(arg);

    }

    //Function for identifying payment error message
    public String paymentErrorMessage(){
        return paymentError.getText();
    }

    //Function for identifying card number error message
    public String cardNumErrorMessage(){
        return cardNumError.getText();
    }

    //Function for identifying expiration date error message
    public String expiryErrorMessage(){
        return expiryError.getText();
    }
}
