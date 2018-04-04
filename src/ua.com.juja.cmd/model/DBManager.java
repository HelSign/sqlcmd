package ua.com.juja.cmd.model;

public interface DBManager {
    void makeConnection(String dbName, String user, String password);

    void insertRows();

    void updateRows();

    void deleteRows();

    void dropTable();

    void getDataSet();

    String getTablesNames();

    boolean isConnected();
}
