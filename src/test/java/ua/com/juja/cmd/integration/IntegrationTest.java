package ua.com.juja.cmd.integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.controller.Configuration;
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
        String expected = "**** Hello! You are using SQLCmd application\n" +
                "**** Please enter a command. For help use command help\n" +
                "**** List of commands:\n" +
                "**** To connect to database\n" +
                "****     'connect' , format: connect | database | username | password\n" +
                "**** To see list of tables\n" +
                "****     'tables'\n" +
                "**** To delete table\n" +
                "****     'drop' , format: drop | tablename\n" +
                "**** To create table\n" +
                "****     'create' , format: create| tablename | column1 | column2 | column3 \n" +
                "**** To insert rows into table\n" +
                "****     'insert' , format: insert| tablename | column1 | value1 | column2 | value2 \n" +
                "**** To update rows in table\n" +
                "****     'update' , format: update| tablename | column1 | value1 | column2 | value2 \n" +
                "**** To delete rows from table\n" +
                "****     'delete' , format: delete| tablename | column1 | value1 | column2 | value2 \n" +
                "**** To delete ALL rows from table\n" +
                "****     'clear' , format: clear| tablename \n" +
                "**** To see data in table\n" +
                "****     'find' , format: find| tablename \n" +
                "**** To see list of commands use command help\n" +
                "****     'help'\n" +
                "****  To exit\n" +
                "****     'exit'\n" +
                "**** Are you sure you want to exit now? Never mind. It's done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()),
                new String(out.toByteArray()));
    }

    @Test
    public void testConnection() throws SQLException {
        //given
        in.add(connect());
        in.add("exit");
        //when
        Main.main(new String[0]);
        //then
        String expected = "**** Hello! You are using SQLCmd application\n" +
                "**** Please enter a command. For help use command help\n" +
                "You are connected to your DB now!\n" +
                "**** Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testExit() throws SQLException {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);
        //then
        String expected = "**** Hello! You are using SQLCmd application\n" +
                "**** Please enter a command. For help use command help\n" +
                "**** Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testUnsupported() {
        in.add("");
        in.add("exit");
        Main.main(new String[0]);
        String expected = "**** Hello! You are using SQLCmd application\n" +
                "**** Please enter a command. For help use command help\n" +
                "**** Command '' is not valid\n" +
                "**** Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    @Test
    public void testLifeCycle() {
        in.add(connect());
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
        String expected = "**** Hello! You are using SQLCmd application\n" +
                "**** Please enter a command. For help use command help\n" +
                "You are connected to your DB now!\n" +
                "**** Table 'author' was successfully created\n" +
                "**** 1 rows were successfully inserted into table 'author'\n" +
                "**** 1 rows were successfully inserted into table 'author'\n" +
                "**** 1 rows were updated in from table 'author'\n" +
                "**** Table 'author' has following data\n" +
                "**** [Jane, Rowling, 1963]\n" +
                "**** [Stiven, King, 1950]\n" +
                "**** End data\n" +
                "**** 1 rows were successfully  deleted from table 'author'\n" +
                "**** Table 'author' was successfully cleared\n" +
                "**** Table 'author' was successfully deleted\n" +
                "**** Are you sure you want to exit now? Never mind. It's " +
                "done\n";
        assertEquals(expected.replaceAll("\\n", lineSeparator()), new String(out.toByteArray()));
    }

    private String connect() {
        Configuration configuration = new Configuration();
        String dbName = configuration.getDbName();
        String user = configuration.getUser();
        String password = configuration.getPassword();
        return String.format("connect|%s|%s|%s", dbName, user, password);
    }
}
