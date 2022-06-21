package capstone.aj.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//Contains functions for interacting with web elements on Account Login page
public class AccountLoginPage {
    WebDriver driver;

    //Identifiers for Web Elements
    @FindBy(xpath="//a[@href='/account/register']")
    WebElement registerButton;
    @FindBy(name = "customer[email]")
    WebElement emailField;
    @FindBy(name = "customer[password]")
    WebElement passwordField;
    @FindBy(xpath="//input[@value='Login']")
    WebElement loginButton;
    @FindBy(xpath = "//a[@href='/account']")
    WebElement accountRegistered;
    @FindBy(xpath = "//*[@id='customer_login']/p")
    WebElement emailErrorMessage;

    //Constructor for AccountLoginPage
    public AccountLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Function for clicking register button
    public void clickRegisterButton(){
        registerButton.click();
    }

    //Function for inputting arg into email field
    public void inputArgInEmailField(String arg){
        emailField.sendKeys(arg);
    }

    //Function for inputting arg into password field
    public void inputArgInPasswordField(String arg){
        passwordField.sendKeys(arg);
    }

    //Function for clicking login button
    public void clickLoginButton(){
        loginButton.submit();
    }

    public String verifyRegistration() {
        String actual = accountRegistered.getText();
        return actual;
    }

    public String confirmErrorMessage(){
        return emailErrorMessage.getText();
    }
}
