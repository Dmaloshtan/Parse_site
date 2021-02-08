package OutputData;

import incomingData.CadastreNumbers;

import java.util.List;

public class ParseData {
    CadastreNumbers cadastreNumbers;
    List<RealEstateObject> realEstateObjects;
    BrowserDriver browserDriver;

    public ParseData(CadastreNumbers cadastreNumbers) {
        this.cadastreNumbers = cadastreNumbers;
    }

    public List<RealEstateObject> getRealEstateObjects() {
        return realEstateObjects;
    }

    public void setBrowserDriver(BrowserDriver browserDriver) {
        this.browserDriver = browserDriver;
    }

    public void executeBrowserSearch(){
        realEstateObjects = browserDriver.outputDataAboutRealEstateObject(cadastreNumbers.getCadastreNumbers());
    }
}
