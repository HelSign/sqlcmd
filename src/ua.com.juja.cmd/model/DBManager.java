package ua.com.juja.cmd.model;

public interface DBManager {

    void makeConnection(String dbName, String user, String password);

    void createTable(String name, String[]columns);

    void insertRows();

    void updateRows();

    void deleteRows(String table);

    void dropTable(String table);

    void getDataSet();

    String getTablesNames();

    boolean isConnected();
}
