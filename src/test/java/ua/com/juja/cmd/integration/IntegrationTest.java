package ua.com.juja.cmd.integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.controller.Main;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.model.JDBCManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    DBManager dbManager;
    private ByteArrayOutputStream out;
    private SqlCmdInputStream in;


    @Before
    public void setup() {
        dbManager = new JDBCManager();
        out = new ByteArrayOutputStream();
        in = new SqlCmdInputStream();

        System.setOut(new PrintStream(out));
        System.setIn(in);
    }

    @Test
    public void testHelp() throws SQLException {
        //given
        in.add("help");
        in.add("exit");
        //when
        Main.main(new String[0]);

        //then
        assertEquals("**** Hello! You are using SQLCmd application" + lineSeparator() +
                "**** Please enter a command. For help use command help" + lineSeparator() +
                "**** List of commands:" + lineSeparator() +
                "****     connect | database | username | password" + lineSeparator() +
                "****         To connect to database" + lineSeparator() +
                "****     help" + lineSeparator() +
                "****         To see list of commands use command help" + lineSeparator() +
                "****     exit" + lineSeparator() +
                "****         To exit" + lineSeparator() +
                "**** Are you sure you want to exit now? Never mind. It's done" + lineSeparator(), new String(out.toByteArray()));
    }

    @Test
    public void testConnection() throws SQLException {
        //given
        in.add("connect|sqlcmd|postgres|postgres");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        assertEquals("**** Hello! You are using SQLCmd application" + lineSeparator() +
                "**** Please enter a command. For help use command help" + lineSeparator() +
                "You are connected to your DB now!" + lineSeparator() +
                "**** Are you sure you want to exit now? Never mind. It's done" + lineSeparator(), new String(out.toByteArray()));
    }

    @Test
    public void testExit() throws SQLException {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);
        //then
        assertEquals("**** Hello! You are using SQLCmd application" + lineSeparator() +
                "**** Please enter a command. For help use command help" + lineSeparator() +
                "**** Are you sure you want to exit now? Never mind. It's done" + lineSeparator(), new String(out.toByteArray()));
    }
}
