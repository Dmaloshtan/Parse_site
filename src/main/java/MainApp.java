import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainApp {

    public static void main(String[] args) throws InterruptedException {
        String cadastreNumber  = "77:01:0001062:23";
        String url = "https://pkk.rosreestr.ru";

        WebDriverConfig driver = new WebDriverConfig();
        driver.navigateToSite(url);
        driver.inputAndSearchData(cadastreNumber);
        Thread.sleep(3000);
        driver.stop();


    }
}
