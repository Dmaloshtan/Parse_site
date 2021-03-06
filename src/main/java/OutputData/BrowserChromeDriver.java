package OutputData;

import domain.Estate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
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
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.className("type-ahead-select")).clear();
    }

    //TODO прописать условие, если есть окошко то кликаем, если нет, то работаем дальше
    public void navigateToSite(String url) {
        driver.navigate().to(url);
        driver.findElement(By.className("tutorial-button-outline")).click(); // выключает обучение на сайте росреестра
    }


    public void inputAndSearch(String cadastreNumber) throws InterruptedException {

        driver.findElement(By.className("type-ahead-select")).sendKeys(cadastreNumber);
        Thread.sleep(800);
        List<WebElement> elements = driver.findElements(By.cssSelector("svg[xmlns='http://www.w3.org/2000/svg']"));
        elements.get(86).click(); // нажимаем на поиск
        System.out.println(driver.findElement(By.className("count-found")).getText());
        /*Если не нашлось, то не меняем на ОКС */

        if (driver.findElement(By.className("count-found")).getText().contains("Ничего не найдено")) {

            WebElement changeButton = driver.findElement(By.className("button"));
            changeButton.click();

            Thread.sleep(800);

            List<WebElement> typesOfObject = driver.findElements(By.className("item"));

//            for(WebElement wb : typesOfObject){
//                System.out.println(wb.getText());
//            }

            Thread.sleep(800);

            WebElement nameObject = driver.findElement(By.className("name"));

            System.out.println(nameObject.getText());

            if (nameObject.getText().equals("Участки")) {
                typesOfObject.get(5).click();
            } else {
                typesOfObject.get(4).click();
            }
        }

        Thread.sleep(300);
        elements.get(86).click(); // нажимаем на поиск
    }

    public Map<String, String> outputData() throws InterruptedException {
        List<WebElement> field = driver.findElements(By.className("field-name"));
        List<WebElement> content = driver.findElements(By.className("expanding-box_content"));

        List<String> fieldString = new ArrayList<>();
        List<String> contentString = new ArrayList<>();

        List<String> fieldStringAdd = new ArrayList<>();
        List<String> contentStringAdd = new ArrayList<>();

        for (int i = 0; i < field.size(); i++) {
            fieldString.add(field.get(i).getText());
            contentString.add(content.get(i).getText());
        }
        /*--------------------------------------------------------*/

        WebElement body = driver.findElement(By.className("detail-info-body"));
        body.click();

        //TODO проверить на видимость элемента (если скролл не появился, значит листать и не надо)

        // Проверяем, есть ли скролл на странице, если да, то выполняем скроллинг
        boolean isPresent = driver.findElements(By.className("scrollbar")).size() > 0;

        /* --------------------------------------------------- */
        if(isPresent){

            WebElement scroll = driver.findElement(By.className("scrollbar"));
/*
Прокручиваем скролл вниз
* */

            Actions actions = new Actions(driver);
            actions.moveToElement(body).perform();

            Thread.sleep(400);
            actions.moveToElement(scroll).clickAndHold().moveByOffset(0, 300).perform();

//Получаем новые значения
            List<WebElement> fieldAdd = driver.findElements(By.className("field-name"));
            List<WebElement> contentAdd = driver.findElements(By.className("expanding-box_content"));



            for (int i = 0; i < fieldAdd.size(); i++) {
                fieldStringAdd.add(fieldAdd.get(i).getText());
                contentStringAdd.add(contentAdd.get(i).getText());
            }
        }

        /* ----------------------------------------------------- */

        Map<String, String> infoAndValue = new HashMap<>();

        for (int i = 0; i < fieldString.size(); i++) {
            String info = fieldString.get(i);
            String value = contentString.get(i);
            infoAndValue.put(info, value);
        }

        for (int i = 0; i < fieldStringAdd.size(); i++) {
            String info = fieldStringAdd.get(i);
            String value = contentStringAdd.get(i);

            if (!infoAndValue.containsKey(info)) {
                infoAndValue.put(info, value);
            }
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
