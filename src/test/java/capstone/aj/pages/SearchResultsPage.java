package capstone.aj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {
    WebDriver driver;

    //Identifiers for Web Elements
    By searchResults = By.xpath("//*[@id='keyboard-nav-3']/div[2]/div[1]/p");
    By searchResultsTitle = By.xpath("//*[@id='isp_search_results_container']/li[1]");

    //Search results page constructor
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    //Collects search result text
    public String actualSearchResults(){
        return driver.findElement(searchResults).getText();
    }

    //Collects text for if no string was entered in search bar before searching
    public String searchResultsForNoResults(){
        return driver.findElement(searchResultsTitle).getText();
    }
}
