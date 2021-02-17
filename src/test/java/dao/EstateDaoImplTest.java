package dao;

import business.EstateManagerImpl;
import domain.Estate;
import org.junit.Test;

import static org.junit.Assert.*;

public class EstateDaoImplTest {

    @Test
    public void testGetEstateList(){
        EstateManagerImpl em = new EstateManagerImpl();
        EstateDaoImpl dao = new EstateDaoImpl();
        ConnectionBuilderImpl con = new ConnectionBuilderImpl("org.postgresql.Driver",
                "jdbc:postgresql://localhost/rosreestr_parse", "postgres", "postgres");
        em.setDao(dao);
        dao.setConnectionBuilder(con);

        em.getEstateList(null);

    }

    @Test
    public void testAddEstateList(){
        EstateManagerImpl em = new EstateManagerImpl();
        EstateDaoImpl dao = new EstateDaoImpl();
        ConnectionBuilderImpl con = new ConnectionBuilderImpl("org.postgresql.Driver",
                "jdbc:postgresql://localhost/rosreestr_parse", "postgres", "postgres");
        em.setDao(dao);
        dao.setConnectionBuilder(con);

        Estate estate = new Estate();
        estate.setCadastreNumber("77:1:4014:23");
        em.addEstate(estate);

    }


}