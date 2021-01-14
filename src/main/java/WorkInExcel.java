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
    private HSSFWorkbook outputBook;
    private HSSFWorkbook inputBook;
    private Sheet sheet;
    private Row row;


    public WorkInExcel(String pathOfCadastreInfo, String pathOfParseData) {
        this.inputPath = pathOfCadastreInfo;
        this.outputPath = pathOfParseData;
    }

    public void setInWorkBook(Map<String, String> inData) throws FileNotFoundException, IOException {

        int rowInFile = 0;

        outputBook = new HSSFWorkbook();
        sheet = outputBook.createSheet("Данные с Росреестра");

        for (String key : inData.keySet()) {
            row = sheet.createRow(rowInFile);
            Cell infoInput = row.createCell(0);
            Cell valueInput = row.createCell(1);
            infoInput.setCellValue(key);
            valueInput.setCellValue(inData.get(key));
            rowInFile++;
        }
        outputBook.write(new FileOutputStream(outputPath));
        outputBook.close();
    }

    public List<String> readFromFirstBook() throws FileNotFoundException, IOException {
        int numberOfCell = 0;
        int numberOfRow = 0;
        inputBook = new HSSFWorkbook(new FileInputStream(inputPath));
        HSSFSheet dataSheet = inputBook.getSheet("Кадастровые номера");
        HSSFRow row = dataSheet.getRow(numberOfRow);
        List<String> cadastreNumbers = new ArrayList<>();

        try{
//            HSSFCell cell = row.getCell(numberOfCell);
            while (!(dataSheet.getRow(numberOfRow).getCell(numberOfCell)==null)){

                if ((dataSheet.getRow(numberOfRow).getCell(numberOfCell)==null)){
                    break;
                }

                String cadastreNumber = row.getCell(numberOfCell).getStringCellValue();
                cadastreNumbers.add(cadastreNumber);
                numberOfRow++;
            }

        } catch (NullPointerException ex){
            System.out.println("Все данные прочитаны");
        }

        inputBook.close();
        return cadastreNumbers;
    }

}
