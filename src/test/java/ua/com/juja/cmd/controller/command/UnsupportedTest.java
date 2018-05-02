package ua.com.juja.cmd.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.view.View;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class UnsupportedTest {
    View view;
    Command command;


    @Before
    public void setup() {
        view = mock(View.class);
        command = new Unsupported(view);
    }

    @Test
    public void testUnsupported() {
        command.execute("dd");
        verify(view).write("Command 'dd' is not valid");
    }


    @Test
    public void testIsExecutable() {
        Assert.assertTrue(command.isExecutable("sd"));
    }

}
