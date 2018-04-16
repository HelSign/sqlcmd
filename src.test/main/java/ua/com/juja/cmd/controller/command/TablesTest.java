package java.ua.com.juja.cmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import java.ua.com.juja.cmd.model.DBManager;
import java.ua.com.juja.cmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(dbManager.getTablesNames())
                .thenReturn("user,books,");
        //when
        cmd.execute("tables");
        //then
        shouldPrint("[user,books,]");
    }

    @Test
    public void testExecuteForEmptyList() {
        //given
        when(dbManager.getTablesNames())
                .thenReturn("");
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
