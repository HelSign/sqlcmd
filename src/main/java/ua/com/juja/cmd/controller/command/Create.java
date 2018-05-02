package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.Arrays;

public class Create implements Command {
    private View view;
    private DBManager dbManager;
    final static private String COMMAND = "create";

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

        if (input.length <= 2) {
            printError(view,command);
            return;
        } else {
            String tableName = input[1].trim();
            if (tableName.length() == 0) {
                printError(view,command);
                return;
            }
            try {
                String[] columns = Arrays.copyOfRange(input, 2, input.length);
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].trim();
                }
                dbManager.createTable(tableName, columns);
                view.write(String.format("Table '%s' was successfully created",
                        tableName));
            } catch (Exception e) {
                view.write(String.format("Table '%s' wasn't created. The " +
                        "reason is: %s", tableName, e.getMessage()));
            }
        }
    }
}

