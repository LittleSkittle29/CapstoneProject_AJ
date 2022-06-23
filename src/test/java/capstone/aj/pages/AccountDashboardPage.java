package capstone.aj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDashboardPage {
    WebDriver driver;

    //Identifiers for Web Elements
    By welcomeMessage = By.xpath("//*[@id='keyboard-nav-3']/h1");

    //Account Dashboard page constructor
    public AccountDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    //Collects welcome message on dashboard
    public String actualWelcomeMessage(){
        return driver.findElement(welcomeMessage).getText();
    }
}
