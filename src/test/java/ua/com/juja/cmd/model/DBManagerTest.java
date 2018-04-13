package ua.com.juja.cmd.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DBManagerTest {
    private DBManager dbManager;

    @Before
    public void setup() {
        dbManager = getDBManager();
        dbManager.makeConnection("sqlcmd", "postgres", "postgres");
    }

    abstract DBManager getDBManager();

    @Test
    public void testIsConnected() {
        assertTrue(dbManager.isConnected());
    }
//todo test
   /* @Test
    public void testGetTableNames() {
        assertEquals("user,book,",  dbManager.getTablesNames());
    }*/
}
