import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainApp {

    private static String url = "https://pkk.rosreestr.ru";
    private static String outputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
    private static String inputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
    private static List <String> cadastreNumbers;
    private static WorkInBrowser driver;
    private static IncomingData incomingData;
    private static OutputData infoFromSite;
    private static Map<String, String> valueData;

    public static void main(String[] args) throws InterruptedException, IOException {

        incomingData = new IncomingData(inputFile);
        incomingData.readFile();
        incomingData.stop();
        cadastreNumbers = incomingData.getCadastreNumbers();

        driver = new WorkInBrowser();
        driver.start();
        driver.navigateToSite(url);

        infoFromSite = new OutputData(outputFile);
        infoFromSite.setTemplateOFHeaders();



        //TODO измерить скорость работы до внедрения многопоточности и после!!!

        for (String cadastreNumber : cadastreNumbers) {
            driver.clearField();
            Thread.sleep(1000);
            driver.inputAndSearchData(cadastreNumber);
            Thread.sleep(1000);
            valueData = driver.outputData();
            infoFromSite.setInfoToWorkBook(valueData);
        }

//TODO в потоке для записи должна быть логика, чтобы он не начинал писать,
// пока в map на запишет данные первый поток (для этого join и synchronized как в тесте) Сделать флажок на зпись,
// чтобы данные брались, только после того, как они там появились

        infoFromSite.write();
        infoFromSite.stop();
        driver.stop();
    }
}
