package OutputData;

import business.EstateManagerImpl;
import dao.ConnectionBuilderImpl;
import dao.EstateDaoImpl;
import domain.Estate;
import incomingData.CadastreNumbers;
import incomingData.InDataSearchFromExcel;
import WriteData.OutDataWriteToExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
        List<Estate> est = parseData.getRealEstateObjects();


        EstateManagerImpl em = new EstateManagerImpl();
        EstateDaoImpl dao = new EstateDaoImpl();
        ConnectionBuilderImpl con = new ConnectionBuilderImpl("org.postgresql.Driver",
                "jdbc:postgresql://localhost/rosreestr_parse", "postgres", "postgres");
        em.setDao(dao);
        dao.setConnectionBuilder(con);

        em.deleteTable();
        em.createTable();

        for(Estate mapsAboutEstate : parseData.getRealEstateObjects()){
            em.addEstate(mapsAboutEstate);
        }



//        for(RealEstateObject rEO : parseData.getRealEstateObjects()){
//
//            for(String key : rEO.getInfoAboutObject().keySet()){
//                System.out.println(key + " " + rEO.getInfoAboutObject().get(key));
//            }
//        }

    }
}
