import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WorkInBrowser {

    private WebDriver driver;
    private WebDriverWait wait;

//TODO обработать исключения

    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    public void navigateToSite(String url) {
        driver.navigate().to(url);
        driver.findElement(By.className("tutorial-button-outline")).click(); // выключает обучение на сайте росреестра
    }

    public void inputAndSearchData(String cadastreNumber) throws InterruptedException {
        driver.findElement(By.className("type-ahead-select")).sendKeys(cadastreNumber);
        Thread.sleep(500);
        List<WebElement> elements = driver.findElements(By.cssSelector("svg[xmlns='http://www.w3.org/2000/svg']"));
        elements.get(82).click();
    }
//TODO может убрать в другой класс или переименовать в этот

    public void outputData() throws InterruptedException {
        Map<WebElement, WebElement> data = new HashMap<>();
        List<WebElement> info = driver.findElements(By.className("field-name"));
        List<WebElement> value = driver.findElements(By.className("expanding-box_content"));
        for(int i = 0; i<info.size(); i++){

            System.out.println(info.get(i).getText() + " " + value.get(i).getText());
        }
    }



    public void stop() {
        driver.quit();
        driver = null;
    }

}