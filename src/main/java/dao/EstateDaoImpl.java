package dao;

import domain.Estate;
import domain.EstateFilter;
import org.apache.poi.ddf.EscherTertiaryOptRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstateDaoImpl implements EstateDao {

    private static final String SELECT = "SELECT Кадастровый_номер, Вид FROM real_estate_object ORDER BY Кадастровый_номер";
    private static final String INSERT = "INSERT INTO real_estate_object (Кадастровый_номер) VALUES (?)";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
       return connectionBuilder.getConnection();
    }

    @Override
    public List<Estate> getEstateList(EstateFilter filter) {
        List<Estate> result = new ArrayList<>();

        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(SELECT);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Estate estate = new Estate();
                    estate.setCadastreNumber(rs.getString("Кадастровый_номер"));
                    result.add(estate);
                }
                rs.close();
                pst.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void addEstate(Estate estate) {
        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(INSERT);
                pst.setString(1, estate.getCadastreNumber());
                pst.executeUpdate();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
