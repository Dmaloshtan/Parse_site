import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainApp {

    private static String url = "https://pkk.rosreestr.ru";
    private static String outputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\Parse_Rosreestr1.xlsx";
    private static String inputFile = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
    private static List<String> cadastreNumbers;
    private static WorkInBrowser driver;
    private static IncomingData incomingData;
    private static OutputData infoFromSite;
    private static Map<String, String> oneInfoFromRosreestr;
    public static boolean isWrite;
    private Object lock = new Object();
    public static boolean isRead = true;


    public synchronized void search(String cadastreNumber) {

        driver.clearField();
        try {
            Thread.sleep(1000);
            driver.inputAndSearchData(cadastreNumber);
            Thread.sleep(1000);

//TODO снихронайсд прописать где-то здесь ниже. Чтобы поток вводил новые данные в сайт, независимо от того, занят монитор другим потоком или нет

            while (!isRead) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            oneInfoFromRosreestr = driver.outputData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isWrite = true;
        isRead = false;
        notifyAll();
    }

    public synchronized void writeInfo() {
        while (!isWrite) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            infoFromSite.setInfoToWorkBook(oneInfoFromRosreestr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isWrite = false;
        isRead = true;
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        incomingData = new IncomingData(inputFile);
        incomingData.readFile();
        incomingData.stop();
        cadastreNumbers = incomingData.getCadastreNumbers();

        driver = new WorkInBrowser();
        driver.start();
        driver.navigateToSite(url);

        infoFromSite = new OutputData(outputFile);
        infoFromSite.setTemplateOFHeaders();

        MainApp mainApp = new MainApp();  // переделать методы в отдельный класс


        //TODO измерить скорость работы до внедрения многопоточности и после!!!
        long l = System.currentTimeMillis();

        //---------------------------------------------------------------------------------------------------------
        /*
        Методы без многопоточности
         */

        for (String cadastreNumber : cadastreNumbers) {
            driver.clearField();
            Thread.sleep(1000);
            driver.inputAndSearchData(cadastreNumber);
            Thread.sleep(1000);

            oneInfoFromRosreestr = driver.outputData();
            infoFromSite.setInfoToWorkBook(oneInfoFromRosreestr);
        }

        infoFromSite.write();
        infoFromSite.stop();
        driver.stop();

        System.out.println(System.currentTimeMillis() - l);

//TODO в потоке для записи должна быть логика, чтобы он не начинал писать,
// пока в map на запишет данные первый поток (для этого join и synchronized как в тесте) Сделать флажок на зпись,
// чтобы данные брались, только после того, как они там появились


        //------------------------------------------------------------------------------------------
        /*
        Без многопоточности, но со статик методами
         */

//        for (String cadastreNumber : cadastreNumbers) {
//           mainApp.search(cadastreNumber);
//           mainApp.writeInfo();
//        }
//
//        infoFromSite.write();
//        infoFromSite.stop();
//        driver.stop();
//
//        System.out.println(System.currentTimeMillis() - l);


        //--------------------------------------------------------------------------------------------------------------

        /*
        Здесь многопоточность
         */

//        Thread searchData = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (String cadastreNumber : cadastreNumbers) {
//                    mainApp.search(cadastreNumber);
//                }
//            }
//        });
//
//        Thread writeData = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                for (String cadastreNumber : cadastreNumbers) {
//                    mainApp.writeInfo();
//                }
//            }
//        });
//
//        searchData.start();
//        writeData.start();
//        searchData.join();
//        writeData.join();
//        System.out.println("111111");
//        infoFromSite.write();
//        infoFromSite.stop();
//        driver.stop();
//
//        System.out.println(System.currentTimeMillis() - l);
//
    }
}
