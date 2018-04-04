package ua.com.juja.cmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCManager implements DBManager {
    private Connection connection;
    private String user;
    private String password;
    private String url;
    private String dbName;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public JDBCManager() {
      /*  user = "postgres";
        password = "postgres";
        dbName = "sqlcmd";*/
        url = "jdbc:postgresql://127.0.0.1:5432/";
    }

    @Override
    public void makeConnection(String dbName, String user, String password) {
        this.dbName = dbName;
        this.user = user;
        this.password = password;

        if (connection != null) {
            System.out.println("You have already been connected to your DB");
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver is not in the library path!", e);
        }
        try {
            connection = DriverManager.getConnection(url + dbName, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Connection failed to database: %s as user: %s ", dbName, user), e);
        }
        if (connection != null) {
            System.out.println("You are connected to your DB now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    private boolean closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("It's impossible to close DB connection");
                e.printStackTrace();
                return false;
            }

            return true;
        } else return true;
    }

    @Override
    public void insertRows() {
    }

    @Override
    public void updateRows() {
    }

    @Override
    public void deleteRows() {
    }

    @Override
    public void dropTable() {
    }

    @Override
    public void getDataSet() {
    }

    @Override
    public String getTablesNames() {
        return "";
    }

    @Override
    public boolean isConnected() {
        if (connection != null)
            return true;
        else return false;
    }
}
