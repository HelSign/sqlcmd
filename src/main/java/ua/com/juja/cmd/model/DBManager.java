package ua.com.juja.cmd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DBManager {

    void makeConnection(String dbName, String user, String password) throws SQLException;

    int createTable(String name, String[] columns) throws SQLException;
    //todoarraylist

    int insertRows(String table, DataSet data) throws SQLException;

    int updateRows(String table, DataSet condition, DataSet data) throws SQLException;

    int deleteRows(String table, DataSet data) throws SQLException;

    int truncateTable(String table) throws SQLException;

    int dropTable(String table) throws SQLException;

    List<DataSet> getTableData(String tableName) throws SQLException;

    Set<String> getTableColumns(String tableName) throws SQLException;

    String getTablesNames() throws SQLException;

    boolean isConnected();
}
