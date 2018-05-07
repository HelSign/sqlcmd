package ua.com.juja.cmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExitTest {
    private View view;
    private DBManager dbManager;
    private Command command;

    @Before
    public void setup() {
        view = mock(View.class);
        dbManager = mock(DBManager.class);
        command = new Exit(view,dbManager);
    }

    @Test(expected = ExitException.class)
    public void testExecute() {
        command.execute("exit");
    }

    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("exit"));
    }

    @Test
    public void testIsNotExecutable() {
        assertFalse(command.isExecutable("1exit"));
    }
}
