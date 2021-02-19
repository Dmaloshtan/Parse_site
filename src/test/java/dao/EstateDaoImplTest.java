package dao;

import business.EstateManagerImpl;
import domain.Estate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        estate.setCadastreNumber("77:1:4014:25");
        Map<String, String> map = new HashMap<>();
        map.put("Тип:", "Объект недвижимости");
        map.put("Вид:", "Земельный участок");
        map.put("Кадастровый номер:", "77:1:1062:23");
        map.put("Адрес:", "г. Москва, б-р Новинский, вл. 12, стр. 1");
        map.put("Кадастровая стоимость:", "143 007 560 руб.");
        estate.setInfoAboutObject(map);
        em.addEstate(estate);

    }


}