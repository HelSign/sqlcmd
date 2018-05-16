package ua.com.juja.cmd.controller.command;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ClearTest {
    DBManager dbManager;
    View view;
    Command command;

    @BeforeEach
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
        command.execute("something|books");
        verify(view).write("Command 'something|books' is not valid");
    }

    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("clear|books"));
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("something|books"));
    }

    @Test
    public void testClearCommandNoParams() {
        command.execute("clear");
        verify(view).write("Command 'clear' is not valid");
    }
}
