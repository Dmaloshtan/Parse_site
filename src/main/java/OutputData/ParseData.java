package OutputData;

import domain.Estate;
import incomingData.CadastreNumbers;

import java.util.List;

public class ParseData {
    CadastreNumbers cadastreNumbers;
    List<Estate> estates;
    BrowserDriver browserDriver;

    public ParseData(CadastreNumbers cadastreNumbers) {
        this.cadastreNumbers = cadastreNumbers;
    }

    public List<Estate> getRealEstateObjects() {
        return estates;
    }

    public void setBrowserDriver(BrowserDriver browserDriver) {
        this.browserDriver = browserDriver;
    }

    public void executeBrowserSearch(){
        estates = browserDriver.outputDataAboutRealEstateObject(cadastreNumbers.getCadastreNumbers());
    }
}
