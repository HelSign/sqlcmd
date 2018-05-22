package ua.com.juja.cmd.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.controller.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages data in database
 */
public class JDBCManager implements DBManager {
    private Connection connection;
    private final static Logger LOG = LogManager.getLogger();

    /**
     * Opens connection with specified user name and password
     * @param dbName
     * @param user
     * @param password
     */
    @Override
    public void makeConnection(String dbName, String user, String password) {
        if (connection != null) {
            LOG.trace("Connection already exist");
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error(e);
            throw new RuntimeException("PostgreSQL JDBC Driver is not in the library path!", e);
        }
        Configuration configuration = new Configuration();
        String url = configuration.getJDBCDriver() +
                configuration.getServer() + ":" + configuration.getPort() + "/" + dbName;
        LOG.trace("Trying to connect to DB with url='{}'", url);
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            LOG.error("", e);
            throw new RuntimeException(String.format("Connection failed to " +
                    "database: %s as user: %s , url: %s ", dbName, user, url), e);
        }
        LOG.trace("Connection has been created");
    }

    /**
     * Creates table with specified name and columns. All columns are varchar(40)
     * @param name
     * @param columns
     * @return 1 if table was created without any exception
     * @throws SQLException
     */
    @Override
    public int createTable(String name, Set<String> columns) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        StringBuilder query = new StringBuilder("CREATE TABLE " + name + " (");
        for (String col : columns) {
            query.append(col).append(" varchar(40),");
        }
        //to remove last comma sign
        query.replace(query.length() - 1, query.length(), ")");
        LOG.trace(query);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query.toString());
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return 1;
    }

    /**
     * Closes database conection if it is open
     * @throws SQLException
     */
    @Override
    public void closeConnection() throws SQLException {
        if (connection != null) {
            if (!connection.getAutoCommit())
                connection.commit();
            connection.close();
            LOG.trace("Connection has been closed");
        }
    }

    /**
     * Inserts data into specified table
     * @param table String table name
     * @param data DataSet object with data to be inserted (pairs of column name and value)
     * @return int number of inserted rows
     * @throws SQLException
     */
    @Override
    public int insertRows(String table, DataSet data) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        Set<String> columns = data.getNames();
        StringBuilder columnsList = new StringBuilder(" (");
        StringBuilder valuesList = new StringBuilder(" (");
        for (String colName : columns) {
            columnsList.append(colName).append(",");
            valuesList.append("'").append(data.get(colName)).append("',");
        }
        columnsList.replace(columnsList.length() - 1, columnsList.length(), ")");
        valuesList.replace(valuesList.length() - 1, valuesList.length(), ")");
        String query = String.format("INSERT INTO public.%1$s%2$s VALUES %3$s",
                table, columnsList, valuesList);
        LOG.trace(query);
        int numRows;
        try (Statement st = connection.createStatement()) {
            numRows = st.executeUpdate(query);
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return numRows;
    }

    /**
     * Updates specified table according to condition
     * @param table
     * @param condition as pair of column name and value for part of UPDATE statement WHERE column=value
     * @param data for update as pairs of column name and value for part of UPDATE statement SET column=value
     * @return int number of updated rows
     * @throws SQLException
     */
    @Override
    public int updateRows(String table, DataSet condition, DataSet data) throws SQLException {
        LOG.traceEntry();
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
        LOG.trace(query);
        int numRows = -1;
        try (Statement st = connection.createStatement()) {
            numRows = st.executeUpdate(query.toString());
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.trace(numRows + " rows were updated");
        LOG.traceExit();
        return numRows;
    }

    /**
     * Deletes rows in specified table
     * @param table
     * @param condition as pair of column name and value for part of DELETE statement WHERE column=value
     * @return number of deleted rows
     */
    @Override
    public int deleteRows(String table, DataSet condition) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        String query = "DELETE FROM public." + table + " WHERE ";
        Set<String> columns = condition.getNames();
        for (String colName : columns) {
            query = String.format(query + "%1$s='%2$s'", colName, condition.get(colName));
        }
        LOG.trace(query);
        int numRows = -1;
        try (Statement st = connection.createStatement()) {
            numRows = st.executeUpdate(query);
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.trace(numRows + " rows were deleted");
        LOG.traceExit();
        return numRows;
    }

    /**
     * Clears specified table
     * @param table
     * @return 1 if table was truncated
     */
    @Override
    public int truncateTable(String table) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        String query = "TRUNCATE TABLE public." + table;
        LOG.trace(query);
        int numRows = -1;
        try (Statement st = connection.createStatement()) {
            numRows = st.executeUpdate(query);
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return numRows;
    }

    /**
     * Drops specified table
     * @param table
     * @return 1 if table was deleted
     * @throws SQLException
     */
    @Override
    public int dropTable(String table) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        String query = "DROP TABLE public." + table;
        LOG.trace(query);
        try (Statement st = connection.createStatement()) {
            st.execute(query);
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return 1;
    }

    /**
     * Selects data from specified table
     * @param tableName
     * @return rows with data
     * @throws SQLException
     */
    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        List<DataSet> result = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        LOG.trace(query);
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
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return result;
    }

    /**
     * Gets list of columns of specified table
     * @param tableName
     * @return
     * @throws SQLException
     */
    @Override
    public Set<String> getTableColumns(String tableName) throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        Set<String> result = new LinkedHashSet<>();
        String query = "SELECT * FROM " + tableName;
        LOG.trace(query);
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
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return result;
    }

    /**
     * Select list of table names
     * @return
     * @throws SQLException
     */
    @Override
    public String getTablesNames() throws SQLException {
        LOG.traceEntry();
        checkIfConnected();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                "AND table_type='BASE TABLE'";
        StringBuilder result = new StringBuilder();
        LOG.trace(query);
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                result.append(rs.getString("table_name")).append(",");
            }
        } catch (SQLException e) {
            LOG.error("", e);
            throw e;
        }
        LOG.traceExit();
        return result.substring(0, result.length() - 1);
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }

    private void checkIfConnected() {
        if (connection == null) {
            LOG.warn("Connection to DB is not established");
            throw new RuntimeException("Connection to DB is not established. Please connect to you DB");
        }
    }
}
