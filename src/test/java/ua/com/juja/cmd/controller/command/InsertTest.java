package ua.com.juja.cmd.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

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
    public void testInsert() throws SQLException {
        //given
        DBDataSet testData = new DBDataSet();
        testData.put("name", "Harry Potter");
        testData.put("author", "J.K.Rowling");
        testData.put("year", "1998");
        //when
        command.execute("insert|book|name|Harry Potter|author|J.K.Rowling|year|1998");
        //then
        verify(dbManager).insertRows("book", testData);
        verify(view).write("0 rows were successfully inserted into table 'book'");
    }

    @Test
    public void testInsertWrongCommandParams() {
        command.execute("insert|books| ");
        verify(view).write("Command 'insert|books| ' is not valid");
    }

    @Test
    public void testInsertWrongCommand() {
        command.execute("cnsert|books");
        verify(view).write("Command 'cnsert|books' is not valid");
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
    public void testInsertCommandNoParams() {
        command.execute("insert");
        verify(view).write("Command 'insert' is not valid");
    }

    @Test
    public void testInsertWithSpaces() throws SQLException {
        //given
        DBDataSet testData = new DBDataSet();
        testData.put("name", "Harry Potter");
        testData.put("author", "J.K.Rowling");
        testData.put("year", "1998");
        //when
        command.execute("insert | book| name|Harry Potter  |author|J.K.Rowling|year|1998");
        //then
        verify(dbManager).insertRows("book", testData);
        verify(view).write("0 rows were successfully inserted into table 'book'");
    }
}
