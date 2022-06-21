package capstone.aj.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.PublicKey;

public class RegistrationPage {
    WebDriver driver;

    //Identifiers for Web Elements
    @FindBy(name = "customer[first_name]")
    WebElement fNameField;
    @FindBy(name = "customer[last_name]")
    WebElement lNameField;
    @FindBy(name = "customer[email]")
    WebElement emailField;
    @FindBy(name = "customer[password]")
    WebElement passwordField;
    @FindBy(xpath = "//*[@id='create_customer']/div[5]/input")
    WebElement registerButton;
    @FindBy(xpath = "//*[@id='keyboard-nav-3']/h1")
    WebElement actualTitle;


    //Constructor for RegistrationPage
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Function for filling out registration form
    public void completeRegistrationForm(String fName, String lName, String email, String password){
        fNameField.clear();
        fNameField.sendKeys(fName);
        lNameField.clear();
        lNameField.sendKeys(lName);
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    //Function for submitting registration form
    public void register(){
        registerButton.submit();
    }

    public String getRegistrationTitleText(){
        return actualTitle.getText();
    }
}
