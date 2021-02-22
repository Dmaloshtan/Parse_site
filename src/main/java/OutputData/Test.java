package OutputData;

import business.EstateManagerImpl;
import dao.ConnectionBuilderImpl;
import dao.EstateDaoImpl;
import domain.Estate;
import incomingData.InDataSearch;
import incomingData.InDataSearchFromExcel;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String path = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
        String pathToWrite = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
        String url = "https://pkk.rosreestr.ru";

        InDataSearch inDataSearch = new InDataSearchFromExcel(path);
        List<String> cadNums = inDataSearch.getCadastrNumbers();

        BrowserDriver browserDriver = new BrowserChromeDriver(url);

        List<Estate> estates = browserDriver.outputDataAboutEstate(cadNums);


        EstateManagerImpl em = new EstateManagerImpl();
        EstateDaoImpl dao = new EstateDaoImpl();
        ConnectionBuilderImpl con = new ConnectionBuilderImpl("org.postgresql.Driver",
                "jdbc:postgresql://localhost/rosreestr_parse", "postgres", "postgres");
        em.setDao(dao);
        dao.setConnectionBuilder(con);

        em.deleteTable();
        em.createTable();

        for (Estate mapsAboutEstate : estates) {
            em.addEstate(mapsAboutEstate);
        }


    }
}
