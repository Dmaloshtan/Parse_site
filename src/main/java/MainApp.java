import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) throws IOException, InterruptedException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://pkk.rosreestr.ru/#/search/55.75360848236939,37.58436619798951/20/@bs7nhvd51?text=77%3A1%3A1062%3A136&type=1&nameTab&indexTab&opened=77%3A1%3A1062%3A136");
        CloseableHttpResponse execute = httpClient.execute(httpGet);

        String body = EntityUtils.toString(execute.getEntity());
        System.out.println(body);

        HttpPost httpPost = new HttpPost("https://pkk.rosreestr.ru/#/search/55.75360848236939,37.58436619798951/20/@bs7nhvd51?text=77%3A1%3A1062%3A136&type=1&nameTab&indexTab&opened=77%3A1%3A1062%3A136");
        httpPost.setHeader("qwe","qwe");
        CloseableHttpResponse execute1 = httpClient.execute(httpPost);

        body = EntityUtils.toString(execute1.getEntity());
        System.out.println(body);

    }
}
