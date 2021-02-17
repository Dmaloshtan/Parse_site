package WriteData;

import OutputData.ParseData;
import domain.Estate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class OutDataWriteToExcel implements OutDataWrite{
    private Map<String, Integer> template;
    private ParseData parseData;
    private String path;
    private XSSFWorkbook outputBook;
    private Sheet sheet;
    private int rowToWrite = 1;

    public OutDataWriteToExcel(ParseData parseData, String path) {
        this.parseData = parseData;
        this.path = path;
        outputBook = new XSSFWorkbook();
    }

    public void setTemplateOFHeaders() {
        int rowForColumn = 0;
        sheet = outputBook.createSheet("Данные с Росреестра");
        sheet.autoSizeColumn(15);
        template = InfoForTemplate.getNumbersOfColumn();
        Row rowForHeader = sheet.createRow(rowForColumn);

        for (String columnName : template.keySet()) {
            Cell nameOfColumns = rowForHeader.createCell(template.get(columnName));
            nameOfColumns.setCellValue(columnName);
        }
    }

    public void setInfoToWorkBook(Map<String, String> inData) throws FileNotFoundException, IOException {

        Row rowForData = sheet.createRow(rowToWrite);

        for (String key : inData.keySet()) {
            if (!template.containsKey(key)) {
                System.out.println("В кадастровой карте новое значение, которого нет в шапке экселя: \"" + key + "\" нужно добавить и запустить программу заново");
                continue;
            }
            Cell valueInput = rowForData.createCell(template.get(key));
            valueInput.setCellValue(inData.get(key));
        }
        rowToWrite++;
    }

    public void write() throws IOException {
        outputBook.write(new FileOutputStream(path));
    }

    public void stop() throws IOException {
        outputBook.close();
    }

    public void writeInfo() throws IOException {
        setTemplateOFHeaders();
        for(Estate mapsAboutEstate : parseData.getRealEstateObjects()){
            setInfoToWorkBook(mapsAboutEstate.getInfoAboutObject());
        }
        write();
        stop();
    }
}
