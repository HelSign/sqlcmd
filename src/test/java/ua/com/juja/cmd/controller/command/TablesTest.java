package ua.com.juja.cmd.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TablesTest {
    private DBManager dbManager;
    private View view;
    private Command cmd;

    @BeforeEach
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        cmd = new Tables(view, dbManager);
    }

    @Test
    public void testIsExecutable() {
        assertTrue(cmd.isExecutable("tables"));
    }

    @Test
    public void testWrongCmdIsExecutable() {
        assertFalse(cmd.isExecutable("cmd"));
    }

    @Test
    public void testExecute() throws SQLException {
        //given
        when(dbManager.getTablesNames()).thenReturn("user,books");
        //when
        cmd.execute("tables");
        //then
        verify(view).write("user,books");
    }

    @Test
    public void testExecuteForEmptyList() throws SQLException {
        //given
        when(dbManager.getTablesNames()).thenReturn("");
        //when
        cmd.execute("tables");
        //then
        verify(view).write("");
    }


}
