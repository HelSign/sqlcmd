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

public class DeleteTest {
    private DBManager dbManager;
    private View view;
    private Command command;

    @BeforeEach
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Delete(view, dbManager);
    }

    @Test
    public void testDelete() throws SQLException {
        DBDataSet data = new DBDataSet();
        data.put("name", "Sting");
        command.execute("delete|author|name|Sting");
        verify(dbManager).deleteRows("author", data);
        verify(view).write("0 rows were successfully  deleted from table 'author'");
    }

    @Test
    public void testWrongCommand() {
        command.execute("delete|author");
        verify(view).write("Command 'delete|author' is not valid");
    }

    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("delete|author"));
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("dfelete|author"));
    }

    @Test
    public void testDeleteWithSpaces() throws SQLException {
        //given
        DBDataSet data = new DBDataSet();
        data.put("name", "Sting");
        //when
        command.execute("delete |author  | name| Sting");
        //then
        verify(dbManager).deleteRows("author", data);
        verify(view).write("0 rows were successfully  deleted from table 'author'");
    }
}
