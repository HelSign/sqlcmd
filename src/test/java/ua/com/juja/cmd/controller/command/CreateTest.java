package ua.com.juja.cmd.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class CreateTest {
    DBManager dbManager;
    View view;
    Command command;

    @BeforeEach
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Create(view, dbManager);
    }

    @Test
    public void testCreate() throws SQLException {
        command.execute("create|author|name1|last_name");
        Set<String> columns = new LinkedHashSet<String>();
        columns.add("name1");
        columns.add("last_name");
        verify(dbManager).createTable("author", columns);
        verify(view).write("Table 'author' was successfully created");
    }

    @Test
    public void testCreateWrongCommandParams() {
        command.execute("create| ");
        verify(view).write("Command 'create| ' is not valid");
    }

    @Test
    public void testClearWrongCommand() {
        command.execute("ccreate|author");
        verify(view).write("Command 'ccreate|author' is not valid");
    }

    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("create|author|name|last_name"));
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("ccreate|author|name|last_name"));
    }

    @Test
    public void testClearCommandNoParams() {
        command.execute("create|author");
        verify(view).write("Command 'create|author' is not valid");
    }
}
