package ua.com.juja.cmd.model;

import org.junit.After;
import org.junit.Before;
import ua.com.juja.cmd.view.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class ConsoleTest {//todo check it it's required
    private Console console = new Console();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

   /* @Test
    public void testWrite() {
        console.write("hello");
        assertEquals("hello", outContent.toString());

    }

    @Test
    public void testRead() {

    }*/
}
