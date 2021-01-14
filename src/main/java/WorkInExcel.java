import net.bytebuddy.asm.Advice;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.spi.AbstractResourceBundleProvider;

public class WorkInExcel {

    private String outputPath;
    private String inputPath;
    private HSSFWorkbook outputBook;  //TODO переделать всё на XSSFWorkbook, тогда будет читать xlsx.
    private HSSFWorkbook inputBook;
    private Sheet sheet;
    private Row row;


    public WorkInExcel(String pathOfCadastreInfo, String pathOfParseData) {
        this.inputPath = pathOfCadastreInfo;
        this.outputPath = pathOfParseData;
    }

    public void setInWorkBook(Map<String, String> inData, int rowForObjectInfo) throws FileNotFoundException, IOException {

        int rowForColumn = 0;

        outputBook = new HSSFWorkbook();
        sheet = outputBook.createSheet("Данные с Росреестра"); //TODO создание листа и заполнение шапки нужно вынести в отдельный метод, чтобы это делалось только один раз

        // Создание шапки с наименованием столбцов
        Map<String, Integer> numberOfColumns = InfoOfObject.getNumbersOfColumn();
        row = sheet.createRow(rowForColumn);

        for (String columnName : numberOfColumns.keySet()) {
            Cell nameOfColumns = row.createCell(numberOfColumns.get(columnName));
            nameOfColumns.setCellValue(columnName);
        }

        // Помещаем значения в ячейки
        row = sheet.createRow(rowForObjectInfo);
        for (String key : inData.keySet()) {
            Cell valueInput = row.createCell(InfoOfObject.getNumberOfOneColumn(key));
            valueInput.setCellValue(inData.get(key));
        }
        outputBook.write(new FileOutputStream(outputPath));
        outputBook.close();
    }

    //TODO читает только значения из первой строки. Доделать, чтобы читались все строки по очереди

    public List<String> readFromFirstBook() throws FileNotFoundException, IOException {
        int numberOfCell = 0;
        int numberOfRow = 0;
        inputBook = new HSSFWorkbook(new FileInputStream(inputPath));
        HSSFSheet dataSheet = inputBook.getSheet("Кадастровые номера");
        HSSFRow row = dataSheet.getRow(numberOfRow);
        List<String> cadastreNumbers = new ArrayList<>();

//TODO переделать на CELLItarator https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/streaming/SXSSFRow.CellIterator.html

        try {
//            HSSFCell cell = row.getCell(numberOfCell);
            while (!(dataSheet.getRow(numberOfRow).getCell(numberOfCell) == null)) {


                String cadastreNumber = row.getCell(numberOfCell).getStringCellValue();
                cadastreNumbers.add(cadastreNumber);
                numberOfRow++;
            }

        } catch (NullPointerException ex) {
            System.out.println("Все данные прочитаны");
        }

        inputBook.close();
        return cadastreNumbers;
    }

}
