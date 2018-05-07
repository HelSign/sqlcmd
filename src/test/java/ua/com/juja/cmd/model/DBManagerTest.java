package ua.com.juja.cmd.model;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.controller.Configuration;
import ua.com.juja.cmd.view.Console;

import static org.junit.Assert.assertTrue;

public abstract class DBManagerTest {
    private DBManager dbManager;
    private Console view;

    @Before
    public void setup() {
        dbManager = getDBManager();
        try {
            Configuration configuration = new Configuration();
            String dbName = configuration.getDbName();
            String user = configuration.getUser();
            String password = configuration.getPassword();
            dbManager.makeConnection(dbName, user, password);
        } catch (Exception e) {
            view.write("Can't connect to database. The reason is:" + e.getMessage());
        }
    }

    abstract DBManager getDBManager();

    @Test
    public void testCheckIfConnected() {
        assertTrue(dbManager.isConnected());
    }
//todo test
   /* @Test
    public void testGetTableNames() {
        assertEquals("user,book,",  dbManager.getTablesNames());
    }*/
}
