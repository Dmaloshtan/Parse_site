package OutputData;

import java.util.Map;

public class RealEstateObject {

    private String cadastreNumber;

    private Map<String, String> infoAboutObject;

    public RealEstateObject(String cadastreNumber, Map<String, String> infoAboutObject) {
        this.cadastreNumber = cadastreNumber;
        this.infoAboutObject = infoAboutObject;
    }

    public Map<String, String> getInfoAboutObject() {
        return infoAboutObject;
    }

    public void setInfoAboutObject(Map<String, String> infoAboutObject) {
        this.infoAboutObject = infoAboutObject;
    }

    public String getCadastreNumber() {
        return cadastreNumber;
    }

    public void setCadastreNumber(String cadastreNumber) {
        this.cadastreNumber = cadastreNumber;
    }
}
