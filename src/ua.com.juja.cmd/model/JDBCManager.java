package ua.com.juja.cmd.model;

import java.sql.*;

public class JDBCManager implements DBManager {
    private Connection connection;
    private String user;
    private String password;
    private String url = "jdbc:postgresql://127.0.0.1:5432/";
    private String dbName;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
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

    @Override
    public void createTable(String name, String[] columns) {
        String query = "CREATE TABLE " + name + " (";
        for (String col : columns) {
            query += col + " varchar(40),";
        }
        query = query.substring(0, query.length() - 2) + ")";//to remove last comma sign

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void deleteRows(String table) {
        String query = "TRUNCATE TABLE public." + table;
        try (Statement st = connection.createStatement()) {
            st.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable(String table) {
        String query = "DROP TABLE public." + table;
        try (Statement st = connection.createStatement()) {
            st.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataSet() {
    }

    @Override
    public String getTablesNames() {
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";
        String result = "";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                result += rs.getString("table_name") + ",";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }
}
