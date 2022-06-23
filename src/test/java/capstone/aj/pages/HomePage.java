package capstone.aj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//Contains functions for interacting with web elements on Home page
public class HomePage {
    WebDriver driver;

    //Identifiers for Web Elements
    By accountButton = By.id("customer_login_link");
    By searchField = By.name("q");
    By searchButton = By.className("header-search-button");
    By productPicture = By.xpath("//*[@id='shopify-section-section-2']/section/div/article[3]/div/p/a");

    //Constructor for HomePage
    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    //Function for clicking account button
    public void clickAccountButton(){
        driver.findElement(accountButton).click();
    }

    //Function for inputting arg in the search field
    public void inputArgInSearchField(String arg){
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(arg);
    }

    //Function for clicking search button
    public void clickSearchButton(){
        driver.findElement(searchButton).click();
    }

    //Function for accessing a product tab
    public void clickProductPicture(){
        driver.findElement(productPicture).click();
    }
}
