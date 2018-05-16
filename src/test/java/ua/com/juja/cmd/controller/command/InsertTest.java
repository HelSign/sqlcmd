package ua.com.juja.cmd.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InsertTest {
    DBManager dbManager;
    View view;
    Command command;


    @BeforeEach
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
        assertTrue(command.isExecutable("insert|books"));
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("cinsert|books"));
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
