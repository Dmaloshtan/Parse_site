package api.business;

import domain.Estate;
import domain.EstateFilter;

import java.util.List;

public interface EstateManager {

    List<Estate> getEstateList(EstateFilter filter);
    void addEstate(Estate estate);


}
