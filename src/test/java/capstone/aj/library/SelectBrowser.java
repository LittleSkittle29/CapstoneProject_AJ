package capstone.aj.library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SelectBrowser {

    static WebDriver driver;

    public static WebDriver StartBrowser(String browserName){
        if(browserName.equalsIgnoreCase("FireFox")){
        System.setProperty("webdriver.firefox.marionette", "src\\test\\resources\\geckodriver.exe");
        driver = new FirefoxDriver();
        }
        else if(browserName.equalsIgnoreCase("Chrome")){
            System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
            driver = new ChromeDriver();
        }
        else if (browserName.equalsIgnoreCase("Edge")){
            System.setProperty("webdriver.edge.driver", "src\\test\\resources\\msedgedriver.exe");
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);
        }
        driver.manage().window().maximize();
        return driver;
    }
}
