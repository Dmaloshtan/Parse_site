package dao;

import WriteData.InfoForTemplate;
import domain.Estate;
import domain.EstateFilter;
import org.apache.poi.ddf.EscherTertiaryOptRecord;
import org.apache.poi.ss.usermodel.Cell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EstateDaoImpl implements EstateDao {
    private static final String DROP = "DROP TABLE IF EXISTS real_estate_object;";
    private static final String CREATE = "CREATE TABLE real_estate_object" +
            "(№ SERIAL PRIMARY KEY," +
            "Кадастровый_номер varchar(100)," +
            "Тип varchar(100)," +
            "Вид varchar(100)," +
            "Кадастровый_квартал varchar(100)," +
            "Статус varchar(100)," +
            "Адрес varchar(100)," +
            "Категория_земель varchar(100)," +
            "Форма_собственности varchar(100)," +
            "Кадастровая_стоимость varchar(100)," +
            "Дата_определения_КС varchar(100)," +
            "Дата_внесения_сведений_о_КС varchar(100)," +
            "Дата_утверждения_КС varchar(100)," +
            "Дата_применения_КС varchar(100)," +
            "Площадь_декларированная varchar(100)," +
            "Площадь_уточненная varchar(100)," +
            "Разрешенное_использование varchar(100)," +
            "по_документу varchar(100)," +
            "Наименование varchar(100)," +
            "Общая_площадь varchar(100)," +
            "Назначение varchar(100)," +
            "Количество_этажей_в_том_числе_подземных varchar(100)," +
            "Количество_подземных_этажей varchar(100)," +
            "Материал_стен varchar(100)," +
            "Завершение_строительства varchar(100)," +
            "Площадь_застройки varchar(100)," +
            "Ввод_в_эксплуатацию varchar(100)" +
            ");";

    private static final String SELECT = "SELECT Кадастровый_номер, Вид FROM real_estate_object ORDER BY Кадастровый_номер";
    private static final String INSERT = "INSERT INTO real_estate_object (Кадастровый_номер, Тип, Вид, Кадастровый_квартал, " +
            "Статус, Адрес, Категория_земель, Форма_собственности, Кадастровая_стоимость, Дата_определения_КС, " +
            "Дата_внесения_сведений_о_КС, Дата_утверждения_КС, Дата_применения_КС, Площадь_декларированная, " +
            "Площадь_уточненная, Разрешенное_использование, по_документу, Наименование, Общая_площадь, Назначение, " +
            "Количество_этажей_в_том_числе_подз, Количество_подземных_этажей, Материал_стен, Завершение_строительства, " +
            "Площадь_застройки, Ввод_в_эксплуатацию)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private ConnectionBuilder connectionBuilder;

    @Override
    public void deleteTable(){
        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(DROP);
                pst.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void createTable(){
        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(CREATE);

                pst.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        Map<String, String> map = estate.getInfoAboutObject();
        Map<String, Integer> column = InfoForTemplate.getNumbersOfColumn();
        InfoForTemplate info = new InfoForTemplate();

        try {
            Connection con = getConnection();
            try {
                PreparedStatement pst = con.prepareStatement(INSERT);
//                con.setAutoCommit(false);

                for (String key : column.keySet()) {
                    int paramIndex = column.get(key)+1;
                    if (!map.containsKey(key)) {
                        pst.setString(paramIndex,"-");
                        continue;
                    }
                    pst.setString(paramIndex,map.get(key));
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
