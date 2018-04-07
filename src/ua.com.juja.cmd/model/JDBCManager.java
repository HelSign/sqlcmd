package ua.com.juja.cmd.model;

import com.sun.javafx.scene.control.skin.VirtualFlow;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    public void makeConnection(String dbName, String user, String password) { //todo refactor code below
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
    public int createTable(String name, String[] columns) {
        checkIfConnected();

        String query = "CREATE TABLE " + name + " (";
        for (String col : columns) {
            query += col + " varchar(40),";
        }
        query = query.substring(0, query.length() - 1) + ")";//to remove last comma sign

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
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
    public int insertRows(String table, DataSet data) {
        checkIfConnected();

        Set<String> columns = data.getNames();
        String columnsList = " (";
        String valuesList = " (";

        for (String colName : columns) {
            columnsList += colName + ",";
            valuesList += String.format("'%s',", data.get(colName));
        }
        columnsList = columnsList.substring(0, columnsList.length() - 1) + ")";
        valuesList = valuesList.substring(0, valuesList.length() - 1) + ")";

        String query = String.format("INSERT INTO public.%1$s%2$s VALUES %3$s",
                table, columnsList, valuesList);

        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateRows(String table, DataSet condition, DataSet data) {
        checkIfConnected();

        String query = String.format("UPDATE public.%s SET ", table);

        Set<String> columns = data.getNames();
        for (String colName : columns) {
            query = String.format(query + "%1$s='%2$s',", colName, data.get(colName));
        }

        if (query.endsWith(","))
            query = query.substring(0, query.length() - 1);

        query += " WHERE ";

        columns = condition.getNames();
        for (String colName : columns) {
            query = String.format(query + "%1$s='%2$s'", colName, condition.get(colName));
        }

        System.out.println("QUERY="+query);
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteRows(String table, DataSet data) {
        checkIfConnected();

        String query = "DELETE FROM public." + table + " WHERE ";
        Set<String> columns = data.getNames();
        for (String colName : columns) {
            query = String.format(query + "%1$s='%2$s'", colName, data.get(colName));
        }

        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int truncateTable(String table) {
        checkIfConnected();

        String query = "TRUNCATE TABLE public." + table;
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int dropTable(String table) {
        checkIfConnected();

        String query = "DROP TABLE public." + table;
        try (Statement st = connection.createStatement()) {
            st.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        checkIfConnected();

        List<DataSet> result = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            while (rs.next()) {
                DBDataSet dataSet = new DBDataSet();
                for (int i = 1; i <= numColumns; i++) {
                    dataSet.put(metaData.getColumnName(i), rs.getObject(i));
                }
                result.add(dataSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        checkIfConnected();

        Set<String> result = new LinkedHashSet<>();
        String query = "SELECT * FROM " + tableName;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    result.add(metaData.getColumnName(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }


    @Override
    public String getTablesNames() {
        checkIfConnected();

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
        return result.substring(0, result.length() - 1);
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }

    public void checkIfConnected() {
        if (connection == null) {
            throw new RuntimeException("Connection to DB is not established. Please connect to you DB");
        }
    }
}
