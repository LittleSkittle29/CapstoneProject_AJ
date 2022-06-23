package capstone.aj.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;

    //Identifiers for Web Elements
    @FindBy(xpath = "//*[@id='shopify-section-header']/section/header/div[1]/div/div[2]/div[2]/a/span[2]")
    WebElement cartAmount;

    //Cart Page constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Function to identify cart amount
    public String cartAmount(){
        return cartAmount.getText();
    }
}
