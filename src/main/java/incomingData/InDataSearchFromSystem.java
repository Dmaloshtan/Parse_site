package incomingData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InDataSearchFromSystem implements InDataSearch{

    @Override
    public List<String> getCadastrNumbers() {
        ArrayList<String> inData = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        while (true){
            String s = sc.nextLine();
            if(s.isEmpty()){
                break;
            }
            inData.add(s);
        }
        return inData;
    }
}
