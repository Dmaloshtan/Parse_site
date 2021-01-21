import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) throws InterruptedException, IOException {

        String url = "https://pkk.rosreestr.ru";
        String outputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
        String inputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";

        IncomingData incomingData = new IncomingData(inputFile);
        incomingData.readFile();
        incomingData.stop();
        List<String> cadastreNumbers = incomingData.getCadastreNumbers();

        WorkInBrowser driver = new WorkInBrowser();
        driver.start();
        driver.navigateToSite(url);

        OutputData info = new OutputData(outputFile);
        info.setTemplateOFHeaders();

        Map<String, String> valueData;

        for (String cadastreNumber : cadastreNumbers) {
            driver.clearField();
            Thread.sleep(1000);
            driver.inputAndSearchData(cadastreNumber);
            Thread.sleep(1000);
            valueData = driver.outputData();
            info.setInfoToWorkBook(valueData);
        }
        info.write();
        info.stop();
        driver.stop();
    }
}
