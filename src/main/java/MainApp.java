import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws InterruptedException, IOException {

        String cadastreNumber  = "77:01:0001062:23";
        String url = "https://pkk.rosreestr.ru";

        WorkInBrowser driver = new WorkInBrowser();
        driver.start();
        driver.navigateToSite(url);
        driver.inputAndSearchData(cadastreNumber);
        Thread.sleep(3000);
        driver.outputData();

//        Document page = Jsoup.parse(new URL(url),3000);
//        System.out.println(page);


        driver.stop();


    }
}
