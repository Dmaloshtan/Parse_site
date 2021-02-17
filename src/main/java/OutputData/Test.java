package OutputData;

import incomingData.CadastreNumbers;
import incomingData.InDataSearchFromExcel;
import WriteData.OutDataWriteToExcel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        String path = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
        String pathToWrite = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
        CadastreNumbers cadNum = new CadastreNumbers(new InDataSearchFromExcel(path));
        cadNum.executeInDataSearch();

        String url = "https://pkk.rosreestr.ru";

        ParseData parseData = new ParseData(cadNum);
        parseData.setBrowserDriver(new BrowserChromeDriver(url));
        parseData.executeBrowserSearch();

        OutDataWriteToExcel write = new OutDataWriteToExcel(parseData, pathToWrite);

        try {
            write.writeInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }





//        for(RealEstateObject rEO : parseData.getRealEstateObjects()){
//
//            for(String key : rEO.getInfoAboutObject().keySet()){
//                System.out.println(key + " " + rEO.getInfoAboutObject().get(key));
//            }
//        }

    }
}
