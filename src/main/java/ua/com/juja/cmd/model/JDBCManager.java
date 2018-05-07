package ua.com.juja.cmd.model;

import ua.com.juja.cmd.controller.Configuration;

import java.sql.*;
import java.util.*;

public class JDBCManager implements DBManager {
    private Connection connection;

    @Override
    public void makeConnection(String dbName, String user, String password) {
        if (connection != null)
            return;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver is not in the library path!", e);
        }
        Configuration configuration = new Configuration();
        String url = configuration.getJDBCDriver() +
                configuration.getServer() + ":" + configuration.getPort() + "/" + dbName;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Connection failed to " +
                    "database: %s as user: %s , url: %s ", dbName, user, url), e);
        }
    }

    @Override
    public int createTable(String name, String[] columns) throws SQLException {
        checkIfConnected();
        StringBuilder query = new StringBuilder("CREATE TABLE " + name + " (");
        for (String col : columns) {
            query.append(col).append(" varchar(40),");
        }
        //to remove last comma sign
        query.replace(query.length() - 1, query.length(), ")");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query.toString());
        } catch (SQLException e) {
            throw e;
        }
        return 1;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (connection != null) {
            if (!connection.getAutoCommit())
                connection.commit();
            connection.close();
        }
    }

    @Override
    public int insertRows(String table, DataSet data) throws SQLException {
        checkIfConnected();
        Set<String> columns = data.getNames();
        StringBuilder columnsList = new StringBuilder(" (");
        StringBuilder valuesList = new StringBuilder(" (");

        for (String colName : columns) {
            columnsList.append(colName + ",");
            valuesList.append("'" + data.get(colName) + "',");
        }
        columnsList.replace(columnsList.length() - 1, columnsList.length(), ")");
        valuesList.replace(valuesList.length() - 1, valuesList.length(), ")");
        String query = String.format("INSERT INTO public.%1$s%2$s VALUES %3$s",
                table, columnsList, valuesList);
        int numRows;
        try (Statement st = connection.createStatement()) {
            numRows = st.executeUpdate(query);
        } catch (SQLException e) {
            throw e;
        }
        return numRows;
    }

    @Override
    public int updateRows(String table, DataSet condition, DataSet data) {
        checkIfConnected();
        StringBuilder query = new StringBuilder(String.format("UPDATE public.%s SET ", table));
        Set<String> columns = data.getNames();
        for (String colName : columns) {
            query.append(String.format("%1$s='%2$s',", colName, data.get(colName)));
        }
        query.replace(query.length() - 1, query.length(), " WHERE ");
        columns = condition.getNames();
        for (String colName : columns) {
            query.append(String.format("%1$s='%2$s'", colName, condition.get(colName)));
        }
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(query.toString());
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
    public int dropTable(String table) throws SQLException {
        checkIfConnected();
        String query = "DROP TABLE public." + table;
        try (Statement st = connection.createStatement()) {
            st.execute(query);
        } catch (SQLException e) {
            throw e;
        }
        return 1;
    }

    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
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
            throw e;
        }
        return result;
    }

    @Override
    public Set<String> getTableColumns(String tableName) throws SQLException {
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
            throw e;
        }
        return result;
    }


    @Override
    public String getTablesNames() throws SQLException {
        checkIfConnected();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                "AND table_type='BASE TABLE'";
        StringBuilder result = new StringBuilder();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                result.append(rs.getString("table_name")).append(",");
            }
        } catch (SQLException e) {
            throw e;
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }

    private void checkIfConnected() {
        if (connection == null) {
            throw new RuntimeException("Connection to DB is not established. Please connect to you DB");
        }
    }
}
