package business;

import api.business.EstateManager;
import dao.EstateDao;
import domain.Estate;
import domain.EstateFilter;

import java.util.List;

public class EstateManagerImpl implements EstateManager {

    private EstateDao dao;

    public void setDao(EstateDao dao){
        this.dao = dao;
    }

    public void deleteTable(){
        dao.deleteTable();
    }

    public void createTable(){
        dao.createTable();
    }


    @Override
    public List<Estate> getEstateList(EstateFilter filter) {
        List<Estate> list = dao.getEstateList(filter);
        return list;
    }

    @Override
    public void addEstate(Estate estate) {
        dao.addEstate(estate);
    }
}
