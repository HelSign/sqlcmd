package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.view.View;

/**
 * Requests to print help information
 */
public class Help implements Command {
    private final View view;
    final static private String COMMAND = "help";

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        printHelp();
    }

    private void printHelp() {
        view.write("List of commands:");
        view.write("To connect to database");
        view.write("    'connect' , format: connect | database | username | password");
        view.write("To see list of tables");
        view.write("    'tables'");
        view.write("To delete table");
        view.write("    'drop' , format: drop | tablename");
        view.write("To create table");
        view.write("    'create' , format: create| tablename | column1 | column2 | column3 ");
        view.write("To insert rows into table");
        view.write("    'insert' , format: insert| tablename | column1 | value1 | column2 | value2 ");
        view.write("To update rows in table");
        view.write("    'update' , format: update| tablename | column1 | value1 | column2 | value2 ");
        view.write("To delete rows from table");
        view.write("    'delete' , format: delete| tablename | column1 | value1 | column2 | value2 ");
        view.write("To delete ALL rows from table");
        view.write("    'clear' , format: clear| tablename ");
        view.write("To see data in table");
        view.write("    'find' , format: find| tablename ");
        view.write("To see list of commands use command help");
        view.write("    'help'");
        view.write(" To exit");
        view.write("    'exit'");

    }
}
