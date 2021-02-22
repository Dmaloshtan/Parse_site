package OutputData;

import domain.Estate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BrowserChromeDriver implements BrowserDriver {

    private String url;
    private WebDriver driver;

    public BrowserChromeDriver(String url) {
        this.url = url;
    }

    public void start() {
        ChromeOptions op = new ChromeOptions();
//        op.addArguments("--headless");
        op.addArguments("--start-maximized");
        driver = new ChromeDriver(op);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10);
    }

    public void clearField() {
        driver.findElement(By.className("type-ahead-select")).clear();
    }
    //TODO прописать условие, если есть окошко то кликаем, если нет, то работаем дальше
    public void navigateToSite(String url) {
        driver.navigate().to(url);
        driver.findElement(By.className("tutorial-button-outline")).click(); // выключает обучение на сайте росреестра
    }

    public void inputAndSearch(String cadastreNumber) throws InterruptedException {
        driver.findElement(By.className("type-ahead-select")).sendKeys(cadastreNumber);
        Thread.sleep(1000);
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


    public List<Estate> outputDataAboutEstate(List<String> cadastrenumbers) {
        List<Estate> estates = new ArrayList<>();
        start();
        navigateToSite(url);
        for (String str : cadastrenumbers) {
            clearField();

            try {
                Thread.sleep(1500);
                inputAndSearch(str);
                Thread.sleep(1500);
                Map<String, String> infoAboutObject = outputData();
                Estate estate = new Estate(str, infoAboutObject);
                estates.add(estate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
        return estates;
    }

    public void stop() {
        driver.quit();
        driver = null;
    }
}
