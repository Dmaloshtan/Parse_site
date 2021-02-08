package incomingData;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InDataSearchFromExcel implements InDataSearch {

    private String path;
    private XSSFWorkbook readBook;

    public InDataSearchFromExcel(String path) {
        this.path = path;
    }

    @Override
    public List<String> addData() {
        try {
            readBook = new XSSFWorkbook(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> inData = new ArrayList<>();
        Iterator<Row> rowIterator = readBook.getSheet("Кадастровые номера").rowIterator();
        int numberOfRow = 0;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String cadastreNumber = row.getCell(numberOfRow).getStringCellValue();
            inData.add(cadastreNumber);
        }

        try {
            readBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inData;
    }
}
