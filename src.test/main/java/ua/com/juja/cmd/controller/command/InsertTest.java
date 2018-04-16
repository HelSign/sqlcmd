package java.ua.com.juja.cmd.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.ua.com.juja.cmd.model.DBManager;
import java.ua.com.juja.cmd.view.View;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class InsertTest {
    DBManager dbManager;
    View view;
    Command command;


    @Before
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Insert(view, dbManager);
    }

    @Test
    public void testInsert() {
        command.execute("insert|books|name|Harry Potter|author|J.K.Rowling|year|1998");
       // verify(dbManager).insertRows();
        verify(view).write("1 rows were successfully inserted into table books");
    }

    @Test
    public void testInsertWrongCommandParams() {
        command.execute("insert|books| ");
        verify(view).write("Please enter a valid command");
    }
    @Test
    public void testInsertWrongCommand() {
        command.execute("cnsert|books");
        verify(view).write("Please enter a valid command");
    }

    @Test
    public void testIsExecutable() {
        Assert.assertTrue(command.isExecutable("insert|books"));
    }
    @Test
    public void testIsNotExecutable() {
        Assert.assertFalse(command.isExecutable("cinsert|books"));
    }

    @Test
    public void testClearCommandNoParams() {
        command.execute("insert");
        verify(view).write("Please enter a valid command");
    }
}