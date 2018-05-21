package ua.com.juja.cmd.controller.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class UnsupportedTest {
    View view;
    Command command;
    DBManager dbManager;

    @BeforeEach
    public void setup() {
        view = mock(View.class);
        command = new Unsupported(view, dbManager);
    }

    @Test
    public void testUnsupported() {
        command.execute("dd");
        verify(view).write("Command 'dd' is not valid");
    }


    @Test
    public void testIsExecutable() {
        assertTrue(command.isExecutable("sd"));
    }

}
