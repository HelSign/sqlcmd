package ua.com.juja.cmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ExitTest {
    private Command command;

    @Before
    public void setup() {
        View view = mock(View.class);
        DBManager dbManager = mock(DBManager.class);
        command = new Exit(view, dbManager);
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
