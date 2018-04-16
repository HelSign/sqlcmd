package ua.com.juja.cmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TablesTest {
    private DBManager dbManager;
    private View view;
    private Command cmd;

    @Before
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
    public void testExecute() {
        //given
        try {
            when(dbManager.getTablesNames()).thenReturn("user,books,");
        } catch (Exception e) {
            view.write("Can't print tables names. The reason is: " + e.getMessage());
        }
        //when
        cmd.execute("tables");
        //then
        shouldPrint("[user,books,]");
    }

    @Test
    public void testExecuteForEmptyList() {
        //given
        try {
            when(dbManager.getTablesNames()).thenReturn("");
        } catch (Exception e) {
            view.write("Can't print tables names. The reason is: " + e.getMessage());
        }
        //when
        cmd.execute("tables");
        //then
        shouldPrint("[]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
