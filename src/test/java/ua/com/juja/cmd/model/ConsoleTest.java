package ua.com.juja.cmd.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConsoleTest {
    private Console console = new Console();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    ByteArrayInputStream inContent;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }


    @Test
    public void testWrite() {
        console.write("hello");
        assertEquals("**** hello"+System.lineSeparator(), outContent.toString());

    }

    @Test
    public void testRead() {
        inContent = new ByteArrayInputStream(new byte[]{'1'});

        assertEquals('1', inContent.read());
    }
}
