import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverConfig {

    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriverConfig() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,10);
    }

    public void navigateToSite(String url){
        driver.navigate().to(url);
        driver.findElement(By.className("tutorial-button-outline")).click(); // выключает обучение на сайте росреестра
    }

    public void inputAndSearchData(String cadastreNumber) throws InterruptedException {
        driver.findElement(By.className("type-ahead-select")).sendKeys(cadastreNumber);
        Thread.sleep(500);
        List<WebElement> elements = driver.findElements(By.cssSelector("svg[xmlns='http://www.w3.org/2000/svg']"));
        elements.get(82).click();
    }


    public void stop(){
        driver.quit();
        driver = null;
    }

}
