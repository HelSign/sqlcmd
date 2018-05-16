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

public class UpdateTest {
    DBManager dbManager;
    View view;
    Command command;

    @BeforeEach
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Update(view, dbManager);
    }

    @Test
    public void testUpdate() throws SQLException {
        //given
        DBDataSet condition = new DBDataSet();
        condition.put("name", "Harry Potter");
        DBDataSet testData = new DBDataSet();
        testData.put("author", "J.K.Rowling");
        testData.put("year", "1998");
        //when
        command.execute("update|book|name|Harry Potter|author|J.K.Rowling|year|1998");
        //then
        verify(dbManager).updateRows("book", condition, testData);
        verify(view).write("0 rows were updated in table 'book'");
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("updat"));
    }

    @Test
    public void testWrongParameters() {
        command.execute("update|table1|col1");
        verify(view).write("Command 'update|table1|col1' is not valid");
    }

    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("update"));
    }

    @Test
    public void testUpdateWithSpaces() throws SQLException {
        //given
        DBDataSet condition = new DBDataSet();
        condition.put("name", "Harry Potter");
        DBDataSet testData = new DBDataSet();
        testData.put("author", "J.K.Rowling");
        testData.put("year", "1998");
        //when
        command.execute("update|book  | name|Harry Potter |author|J.K.Rowling|year|1998");
        //then
        verify(dbManager).updateRows("book", condition, testData);
        verify(view).write("0 rows were updated in table 'book'");
    }
}