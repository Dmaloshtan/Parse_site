package dao;

import WriteData.InfoForTemplate;
import domain.Estate;
import domain.EstateFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EstateDaoImpl implements EstateDao {

    private static final String DELETE = "DELETE FROM real_estate_object;";

    private static final String ALTER_SEQUENCE = "ALTER SEQUENCE real_estate_object_№_seq RESTART WITH 1;";

    private static final String SELECT = "SELECT Кадастровый_номер, Вид FROM real_estate_object ORDER BY Кадастровый_номер";

    private static final String INSERT = "INSERT INTO real_estate_object (Кадастровый_номер, Тип, Вид, Кадастровый_квартал, " +
            "Статус, Адрес, Категория_земель, Форма_собственности, Кадастровая_стоимость, Дата_определения_КС, " +
            "Дата_внесения_сведений_о_КС, Дата_утверждения_КС, Дата_применения_КС, Площадь_декларированная, " +
            "Площадь_уточненная, Разрешенное_использование, по_документу, Наименование, Общая_площадь, Назначение, " +
            "Количество_этажей_в_том_числе_подз, Количество_подземных_этажей, Материал_стен, Завершение_строительства, " +
            "Площадь_застройки, Ввод_в_эксплуатацию)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    @Override
    public void deleteTable() {
        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(DELETE);
                pst.executeUpdate();
                pst.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alterSequence() {
        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(ALTER_SEQUENCE);
                pst.executeUpdate();
                pst.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Map<String, String> map = estate.getInfoAboutObject();
        Map<String, Integer> column = InfoForTemplate.getNumbersOfColumn();
        InfoForTemplate info = new InfoForTemplate();

        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(INSERT);
//                con.setAutoCommit(false);

                for (String key : column.keySet()) {
                    int paramIndex = column.get(key) + 1;
                    if (!map.containsKey(key)) {
                        pst.setString(paramIndex, "-");
                        continue;
                    }
                    pst.setString(paramIndex, map.get(key));
                }

                pst.executeUpdate();
                pst.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
