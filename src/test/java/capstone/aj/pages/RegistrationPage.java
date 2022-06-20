package capstone.aj.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    //Constructor for RegistrationPage
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Function for filling out registration form
    public void completeRegistrationForm(String fName, String lName, String email, String password){
        fNameField.sendKeys(fName);
        lNameField.sendKeys(lName);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
    }

    //Function for submitting registration form
    public void register(){
        registerButton.submit();
    }
}
