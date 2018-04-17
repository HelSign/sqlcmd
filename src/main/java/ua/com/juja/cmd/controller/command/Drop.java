package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class Drop implements Command {
    private View view;
    private DBManager dbManager;
    final static public String COMMAND = "drop";

    public Drop(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        if (!isExecutable(command)) {
            printError(command);
            return;
        }
        String[] cmdParams = command.split("\\|");
        if (cmdParams.length == 2 && cmdParams[1].trim().length() > 0) {
            String tableName = cmdParams[1].trim();
            try {
                dbManager.dropTable(tableName);
                view.write(String.format("Table '%s' was successfully deleted", tableName));
            } catch (Exception e) {
                view.write(String.format("Table '%s' wasn't deleted. The " +
                        "reason is: %s", tableName, e.getMessage()));
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