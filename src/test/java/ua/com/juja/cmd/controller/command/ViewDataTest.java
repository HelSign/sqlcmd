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

public class ViewDataTest {
    DBManager dbManager;
    View view;
    Command command;

    @BeforeEach
    public void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new ViewData(view, dbManager);
    }

    @Test
    public void testExecute() throws SQLException {
        //when
        command.execute("find|book");

        //then
        verify(dbManager).getTableData("book");
        verify(view).write("Table 'book' has no data");
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("something"));
    }

    @Test
    public void testWrongParameters() {
        command.execute("find|table1|col1");
        verify(view).write("Command 'find|table1|col1' is not valid");
    }

    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("find"));
    }

}
