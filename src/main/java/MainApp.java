import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws InterruptedException, IOException {

        String cadastreNumber  = "77:01:0001062:136";
        String url = "https://pkk.rosreestr.ru";
        String outputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr.xls";
        String inputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData.xls";

        WorkInExcel excel = new WorkInExcel(inputFile,outputFile);
        List<String> cadastreNumbers = excel.readFromFirstBook();
        WorkInBrowser driver = new WorkInBrowser();
        driver.start();
        driver.navigateToSite(url);

        for(String number : cadastreNumbers){
            driver.inputAndSearchData(number);
            Thread.sleep(3000);
            excel.setInWorkBook(driver.outputData());
        }

        driver.stop();

//        HSSFWorkbook workbook = new HSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Data");
//        Row row = sheet.createRow(0);
//        Cell number = row.createCell(0);
//        number.setCellValue(cadastreNumber);
//        workbook.write(new FileOutputStream("C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Test.xls"));
//        workbook.close();


    }
}
