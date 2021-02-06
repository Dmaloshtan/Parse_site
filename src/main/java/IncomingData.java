import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IncomingData {
    private List<String> cadastreNumbers; //TODO в абстрактный класс
    private XSSFWorkbook readBook;

    public IncomingData(String path) throws IOException {
        readBook = new XSSFWorkbook(new FileInputStream(path));
    }

    public void readFile() {
        cadastreNumbers = new ArrayList<>();
        Iterator<Row> rowIterator = readBook.getSheet("Кадастровые номера").rowIterator();
        int numberOfRow = 0;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String cadastreNumber = row.getCell(numberOfRow).getStringCellValue();
            cadastreNumbers.add(cadastreNumber);
        }
    }

    public void stop() throws IOException {
        readBook.close();
    }

    public List<String> getCadastreNumbers() {
        return cadastreNumbers;
    }
}
