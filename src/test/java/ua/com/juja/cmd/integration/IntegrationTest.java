package ua.com.juja.cmd.integration;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.juja.cmd.controller.Configuration;
import ua.com.juja.cmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static java.lang.System.lineSeparator;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    private ByteArrayOutputStream out;
    private ByteArrayOutputStream err;
    private SqlCmdInputStream in;
    private Configuration configuration;
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setup() {
        out = new ByteArrayOutputStream();
        err = new ByteArrayOutputStream();
        in = new SqlCmdInputStream();
        System.setOut(new PrintStream(out));
        System.setIn(in);
        System.setErr(new PrintStream(err));
        configuration = new Configuration();

    }

    @AfterEach
    public void release() throws IOException {
        out.close();
        in.close();
        err.close();
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testHelp() {
        //given
        in.add("help");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello! You are using SQLCmd application\n" +
                "Please enter a command. For help use command help\n" +
                "List of commands:\n" +
                "To connect to database\n" +
                "    'connect' , format: connect | database | username | password\n" +
                "To see list of tables\n" +
                "    'tables'\n" +
                "To delete table\n" +
                "    'drop' , format: drop | tablename\n" +
                "To create table\n" +
                "    'create' , format: create| tablename | column1 | column2 | column3 \n" +
                "To insert rows into table\n" +
                "    'insert' , format: insert| tablename | column1 | value1 | column2 | value2 \n" +
                "To update rows in table\n" +
                "    'update' , format: update| tablename | column1 | value1 | column2 | value2 \n" +
                "To delete rows from table\n" +
                "    'delete' , format: delete| tablename | column1 | value1 | column2 | value2 \n" +
                "To delete ALL rows from table\n" +
                "    'clear' , format: clear| tablename \n" +
                "To see data in table\n" +
                "    'find' , format: find| tablename \n" +
                "To see list of commands use command help\n" +
                "    'help'\n" +
                " To exit\n" +
                "    'exit'\n" +
                "Are you sure you want to exit now? Never mind. It's done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testConnection() {
        //given
        in.add(connectCommand());
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello! You are using SQLCmd application\n" +
                "Please enter a command. For help use command help\n" +
                "You are connected to your DB now!\n" +
                "Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testConnectionWrongPassword() {
        //given
        in.add(connectCommand() + "wrong");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        String expected = String.format("Hello! You are using SQLCmd application\n" +
                        "Please enter a command. For help use command help\n" +
                        "Please enter correct username and password. See detailed error message below\t\n" +
                        "Connection failed to database: %s as user: %s , url: %s \n" +
                        "Are you sure you want to exit now? Never mind. It's done\n",
                configuration.getDbName(), configuration.getUser(), configuration.getUrl());
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello! You are using SQLCmd application\n" +
                "Please enter a command. For help use command help\n" +
                "Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testUnsupported() {
        in.add("");
        in.add("exit");
        Main.main(new String[0]);
        String expected = "Hello! You are using SQLCmd application\n" +
                "Please enter a command. For help use command help\n" +
                "Command '' is not valid\n" +
                "Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testLifeCycle() {
        in.add(connectCommand());
        in.add("create|author|name|last_name|birthday");
        in.add("insert|author|name|Jane|last_name|Rowling|birthday|1963");
        in.add("insert|author|name|Stiven|last_name|King|birthday|1955");
        in.add("update|author|name|Stiven|birthday|1950");
        in.add("find|author");
        in.add("delete|author|name|Stiven");
        in.add("clear|author");
        in.add("drop|author");
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        String expected = "Hello! You are using SQLCmd application\n" +
                "Please enter a command. For help use command help\n" +
                "You are connected to your DB now!\n" +
                "Table 'author' was successfully created\n" +
                "1 rows were successfully inserted into table 'author'\n" +
                "1 rows were successfully inserted into table 'author'\n" +
                "1 rows were updated in table 'author'\n" +
                "Table 'author' has following data\n" +
                "\n" +
                "+--------------------+--------------------+--------------------+\n" +
                "|name                |last_name           |birthday            |\n" +
                "+--------------------+--------------------+--------------------+\n" +
                "|Jane                |Rowling             |1963                |\n" +
                "|Stiven              |King                |1950                |\n" +
                "+--------------------+--------------------+--------------------+\n\n" +
                "End data\n" +
                "1 rows were successfully  deleted from table 'author'\n" +
                "Table 'author' was successfully cleared\n" +
                "Table 'author' was successfully deleted\n" +
                "Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    private String connectCommand() {
        String dbName = configuration.getDbName();
        String user = configuration.getUser();
        String password = configuration.getPassword();
        return String.format("connect|%s|%s|%s", dbName, user, password);
    }
}
