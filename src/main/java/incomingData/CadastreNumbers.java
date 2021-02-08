package incomingData;

import java.util.List;

public class CadastreNumbers {

    private InDataSearch inData;
    private List<String> cadastreNumbers;

    public CadastreNumbers() {
    }

    public CadastreNumbers(InDataSearch inData) {
        this.inData = inData;
    }

    public void setInData(InDataSearch inData) {
        this.inData = inData;
    }

    public void executeInDataSearch(){
       cadastreNumbers = inData.addData();
    }

    public List<String> getCadastreNumbers() {
        return cadastreNumbers;
    }
}
