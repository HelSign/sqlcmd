package java.ua.com.juja.cmd.model;

public class JDBCManagerTest extends DBManagerTest {
    @Override
    DBManager getDBManager() {
        return new JDBCManager();
    }
}
