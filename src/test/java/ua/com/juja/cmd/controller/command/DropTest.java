package ua.com.juja.cmd.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class DropTest {
    DBManager dbManager;
    View view;
    Command command;


    @Before
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Drop(view, dbManager);
    }

    @Test
    public void testDrop() throws SQLException {
        command.execute("drop|books");
        verify(dbManager).dropTable("books");
        verify(view).write("Table 'books' was successfully deleted");
    }

    @Test
    public void testDropWrongCommand() {
        command.execute("drop| ");
        verify(view).write("Command 'drop| ' is not valid");
    }

    @Test
    public void testIsExecutable() {
        Assert.assertTrue(command.isExecutable("drop|books"));
    }

    @Test
    public void testIsNotExecutable() {
        Assert.assertFalse(command.isExecutable("ddrop|books"));
    }

    @Test
    public void testDropCommandNoParams() {
        command.execute("drop");
        verify(view).write("Command 'drop' is not valid");
    }
}
