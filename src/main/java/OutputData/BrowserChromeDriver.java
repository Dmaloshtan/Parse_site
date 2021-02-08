package OutputData;

import incomingData.CadastreNumbers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BrowserChromeDriver implements BrowserDriver {

    private String url;

    private WebDriver driver;
    private CadastreNumbers cadastreNumbers;

    public BrowserChromeDriver(String url) {
        this.url = url;
    }

    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    public void clearField() {
        driver.findElement(By.className("type-ahead-select")).clear();
    }

    public void navigateToSite(String url) {
        driver.navigate().to(url);
        driver.findElement(By.className("tutorial-button-outline")).click(); // выключает обучение на сайте росреестра
    }

    public void inputAndSearch(String cadastreNumber) throws InterruptedException {
        driver.findElement(By.className("type-ahead-select")).sendKeys(cadastreNumber);
        Thread.sleep(500);
        List<WebElement> elements = driver.findElements(By.cssSelector("svg[xmlns='http://www.w3.org/2000/svg']"));
        elements.get(85).click();
    }

    public Map<String, String> outputData() throws InterruptedException {
        List<WebElement> field = driver.findElements(By.className("field-name"));
        List<WebElement> content = driver.findElements(By.className("expanding-box_content"));

        Map<String, String> infoAndValue = new HashMap<>();
        for (int i = 0; i < field.size(); i++) {
            String info = field.get(i).getText();
            String value = content.get(i).getText();
            infoAndValue.put(info, value);
        }
        return infoAndValue;
    }


    public List<RealEstateObject> outputDataAboutRealEstateObject(List<String> cadastrenumbers) {
       List<RealEstateObject> realEstateObjects = new ArrayList<>();
        start();
        navigateToSite(url);
        for (String str : cadastrenumbers) {
            clearField();

            try {
                Thread.sleep(1000);
                inputAndSearch(str);
                Thread.sleep(1000);
                Map<String, String> infoAboutObject = outputData();
                RealEstateObject realEstateObject = new RealEstateObject(str, infoAboutObject);
                realEstateObjects.add(realEstateObject);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
        return realEstateObjects;
    }

    public void stop() {
        driver.quit();
        driver = null;
    }
}
