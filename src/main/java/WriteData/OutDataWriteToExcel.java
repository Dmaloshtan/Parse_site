package WriteData;


import domain.Estate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OutDataWriteToExcel implements OutDataWrite{
    private Map<String, Integer> template;
    private List<Estate> estateList;
    private String path;
    private XSSFWorkbook outputBook;
    private Sheet sheet;
    private int rowToWrite = 1;

    public OutDataWriteToExcel(List<Estate> estateList, String path) {
        this.estateList = estateList;
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
        for(Estate mapsAboutEstate : estateList){
            setInfoToWorkBook(mapsAboutEstate.getInfoAboutObject());
        }
        write();
        stop();
    }
}
