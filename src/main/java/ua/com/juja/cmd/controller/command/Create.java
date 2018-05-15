package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.LinkedHashSet;
import java.util.Set;

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
            printError(view, command);
            return;
        }
        String tableName = input[1].trim();
        if (tableName.length() == 0) {
            printError(view, command);
            return;
        }
        int result = -1;
        try {
            Set<String> columns = new LinkedHashSet<String>();
            for (int i = 2; i < input.length; i++) {
                columns.add(input[i].trim());
            }
            result = dbManager.createTable(tableName, columns);
            view.write(String.format("Table '%s' was successfully created", tableName));
        } catch (Exception e) {
            view.write(String.format("Table '%s' wasn't created. The reason is: %s", tableName, e.getMessage()));
        }
        if (result == -1)
            view.write(String.format("Table '%s' wasn't created. Please see the reason in logs", tableName));

    }
}

