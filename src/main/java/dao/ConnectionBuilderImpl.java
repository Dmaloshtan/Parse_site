package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilderImpl implements ConnectionBuilder{
    private String url;
    private String login;
    private String password;

    public ConnectionBuilderImpl(String className, String url, String login, String password) {
        try {
            Class.forName(className);
            this.url = url;
            this.login = login;
            this.password = password;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
       return DriverManager.getConnection(url, login, password);
    }
}
