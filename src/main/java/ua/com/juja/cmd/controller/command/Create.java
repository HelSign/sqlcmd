package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.Arrays;

public class Create implements Command {
    private View view;
    private DBManager dbManager;
    final static public String COMMAND = "create";

    public Create(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        String[] input = command.split("\\|");
        if (input.length > 2) {
            try {
                String tableName = input[1].trim();
                if (tableName.length() == 0) {
                    printError(command);
                    return;
                }
                String[] columns = Arrays.copyOfRange(input, 2, input.length);
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].trim();

                }

                if (dbManager.createTable(tableName, columns) == 1)
                    view.write(String.format("Table '%s' was successfully created", tableName));
                else
                    view.write(String.format("Table '%s' wasn't created", tableName));

            } catch (Exception e) {
                view.write(String.format("Table wasn't created"));
                view.write("" + e);
            }
        } else {
            printError(command);
            return;
        }
    }

    private void printError(String command) {
        view.write("Please enter a valid command");
    }
}

