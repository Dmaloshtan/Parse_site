package incomingData;

public class Test {

    public static void main(String[] args) {
        String path = "C:\\Users\\Дмитрий\\IdeaProjects\\Parsing_rosreestr\\CadastreData1.xlsx";
        CadastreNumbers cadNum = new CadastreNumbers(new InDataSearchFromExcel(path));
        cadNum.executeInDataSearch();

        for(String str : cadNum.getCadastreNumbers()){
            System.out.println(str);
        }

    }
}
