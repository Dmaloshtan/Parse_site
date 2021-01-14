import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws InterruptedException, IOException {

        String url = "https://pkk.rosreestr.ru";
        String outputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr.xls";
        String inputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData.xls";

        WorkInExcel excel = new WorkInExcel(inputFile,outputFile);
        List<String> cadastreNumbers = excel.readFromFirstBook();

        WorkInBrowser driver = new WorkInBrowser();
        driver.start();
        driver.navigateToSite(url);

        int rowForObjectInfo = 1;

        for(String number : cadastreNumbers){
            driver.clearField();
            Thread.sleep(2000);
            driver.inputAndSearchData(number);
            Thread.sleep(3000);
            excel.setInWorkBook(driver.outputData(),rowForObjectInfo);
            rowForObjectInfo++;
        }

        driver.stop();



    }
}
