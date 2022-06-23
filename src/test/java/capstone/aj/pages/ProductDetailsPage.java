package capstone.aj.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage {
    WebDriver driver;

    //Identifiers for Web Elements
    @FindBy(xpath = "//*[@id='bcpo-select-option-0']/div[1]/label")
    WebElement selectAdultSize;
    @FindBy(xpath = "//*[@id='bcpo-select-option-1']/div[4]/label")
    WebElement selectType;
    @FindBy(xpath = "//*[@id='ispbxii_1']")
    WebElement adjustQuantity;
    @FindBy(xpath = "//*[@id='product_form_4502868951093']/div[4]/input")
    WebElement addToCart;
    @FindBy(xpath = "//*[@id='shopify-section-product']/section/div/div[3]/div/p/span[1]")
    WebElement price;

    //Constructor for ProductDetailsPage
    public ProductDetailsPage (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //Function for Confirming price exists
    public String confirmPriceExists(){
        return price.getText();
    }

    //Function for selecting size
    public void selectAdultSize(){
        selectAdultSize.click();
    }

    //Function for selecting version of product
    public void selectType(){
        selectType.click();
    }

    //Function for adjusting quantity of product
    public void adjustQuantity(String amount){
        adjustQuantity.clear();
        adjustQuantity.sendKeys(amount);
    }

    //Function for adding product(s) to cart
    public void addProductsToCart(){
        addToCart.submit();
    }
}
