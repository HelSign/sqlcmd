package ua.com.juja.cmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.view.Console;
import ua.com.juja.cmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExitTest {
    private View view;
    private Command command;

    @Before
    public void setup() {
        view = new Console();
        command = new Exit(view);
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
