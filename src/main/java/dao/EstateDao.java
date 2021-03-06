package dao;

import domain.Estate;
import domain.EstateFilter;

import java.util.List;

public interface EstateDao {

    public List<Estate> getEstateList(EstateFilter filter);

    public void addEstate(Estate estate);

    public void deleteTable();

    public void alterSequence();


}
