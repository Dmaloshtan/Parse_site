package OutputData;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface BrowserDriver {

    public List<RealEstateObject> outputDataAboutRealEstateObject(List<String> cadastrenumbers);

}
