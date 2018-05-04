package ua.com.juja.cmd.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ClearTest {
    DBManager dbManager;
    View view;
    Command command;

    @Before
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Clear(view, dbManager);
    }

    @Test
    public void testClear() throws SQLException {
        command.execute("clear|books");
            verify(dbManager).truncateTable("books");
        verify(view).write("Table 'books' was successfully cleared");
    }

    @Test
    public void testClearWrongCommandParams() {
        command.execute("clear| ");
        verify(view).write("Command 'clear| ' is not valid");
    }

    @Test
    public void testClearWrongCommand() {
        command.execute("cclear|books");
        verify(view).write("Command 'cclear|books' is not valid");
    }

    @Test
    public void testIsExecutable() {
        Assert.assertTrue(command.isExecutable("clear|books"));
    }

    @Test
    public void testIsNotExecutable() {
        Assert.assertFalse(command.isExecutable("cclear|books"));
    }

    @Test
    public void testClearCommandNoParams() {
        command.execute("clear");
        verify(view).write("Command 'clear' is not valid");
    }
}
