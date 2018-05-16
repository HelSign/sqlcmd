package ua.com.juja.cmd.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ExitTest {
    private Command command;

    @BeforeEach
    public void setup() {
        View view = mock(View.class);
        DBManager dbManager = mock(DBManager.class);
        command = new Exit(view, dbManager);
    }

    @Test
    public void testExecute() {
        assertThrows(ExitException.class, () -> {
            command.execute("exit");
        });
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
