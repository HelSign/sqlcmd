package java.ua.com.juja.cmd.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.ua.com.juja.cmd.model.DBManager;
import java.ua.com.juja.cmd.view.View;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class CreateTest {
    DBManager dbManager;
    View view;
    Command command;


    @Before
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Create(view, dbManager);
    }

    @Test
    public void testCreate() {
        command.execute("create|author|name|last_name");
        verify(dbManager).createTable("author", new String[]{"name", "last_name"});
        verify(view).write("Table author was successfully created");
    }

    @Test
    public void testCreateWrongCommandParams() {
        command.execute("create| ");
        verify(view).write("Please enter a valid command");
    }

    @Test
    public void testClearWrongCommand() {
        command.execute("ccreate|author");
        verify(view).write("Please enter a valid command");
    }

    @Test
    public void testIsExecutable() {
        Assert.assertTrue(command.isExecutable("create|author|name|last_name"));
    }

    @Test
    public void testIsNotExecutable() {
        Assert.assertFalse(command.isExecutable("ccreate|author|name|last_name"));
    }

    @Test
    public void testClearCommandNoParams() {
        command.execute("create|author");
        verify(view).write("Please enter a valid command");
    }
}
