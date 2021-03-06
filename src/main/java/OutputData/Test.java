package OutputData;

import business.EstateManagerImpl;
import dao.ConnectionBuilderImpl;
import dao.EstateDaoImpl;
import domain.Estate;
import incomingData.InDataSearch;
import incomingData.InDataSearchFromExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {


    public static void randomTest() throws InterruptedException {
        String path = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
        String pathToWrite = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
        String url = "https://pkk.rosreestr.ru";

        InDataSearch inDataSearch = new InDataSearchFromExcel(path);
        List<String> cadNums = inDataSearch.getCadastrNumbers();

        BrowserDriver browserDriver = new BrowserChromeDriver(url);

        browserDriver.start();
        browserDriver.navigateToSite(url);
        Map<String, String> map = new HashMap<>();

        for (String str : cadNums) {
            browserDriver.clearField();
            browserDriver.inputAndSearch(str);
            map = browserDriver.outputData();
        }

        System.out.println(map.size());
        for (String str : map.keySet()) {
            System.out.println(str + " " +  map.get(str));
        }

        Thread.sleep(2000);

        browserDriver.stop();

    }

    public static void allTest() {
        String path = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
        String pathToWrite = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
        String url = "https://pkk.rosreestr.ru";

        InDataSearch inDataSearch = new InDataSearchFromExcel(path);
        List<String> cadNums = inDataSearch.getCadastrNumbers();

        BrowserDriver browserDriver = new BrowserChromeDriver(url);

        List<Estate> estates = browserDriver.outputDataAboutEstate(cadNums);

        System.out.println(estates);

        EstateManagerImpl em = new EstateManagerImpl();
        EstateDaoImpl dao = new EstateDaoImpl();
        ConnectionBuilderImpl con = new ConnectionBuilderImpl("org.postgresql.Driver",
                "jdbc:postgresql://localhost/rosreestr_parse", "postgres", "postgres");
        em.setDao(dao);
        dao.setConnectionBuilder(con);

        em.deleteTable();
        em.alterSequence();

        for (Estate mapsAboutEstate : estates) {
            em.addEstate(mapsAboutEstate);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        allTest();

    }
}
