package domain;

import java.util.Map;

public class Estate {

    private String cadastreNumber;

    private Map<String, String> infoAboutObject;

    public Estate() {
    }

    public Estate(String cadastreNumber) {
        this.cadastreNumber = cadastreNumber;
    }

    public Estate(String cadastreNumber, Map<String, String> infoAboutObject) {
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

    @Override
    public String toString() {
        return "Estate{" +
                "cadastreNumber='" + cadastreNumber + '\'' +
                ", infoAboutObject=" + infoAboutObject +
                '}';
    }
}
