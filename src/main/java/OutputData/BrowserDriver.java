package OutputData;

import domain.Estate;

import java.util.List;
import java.util.Map;

public interface BrowserDriver {

    public void start();

    public void clearField();

    public void navigateToSite(String url);

    public List<Estate> outputDataAboutEstate(List<String> cadastrenumbers);

    public void inputAndSearch(String cadastreNumber) throws InterruptedException;

    public void stop();

    public Map<String, String> outputData() throws InterruptedException;

}
