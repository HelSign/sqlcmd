package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

/**
 * Requests to delete specified table
 */
public class Drop extends GeneralCommand {
    public final static String COMMAND = "drop";

    public Drop(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        LOG.traceEntry();
        if (!isExecutable(command)) {
            notValidMessage(command);
            return;
        }
        String[] cmdParams = command.split("\\|");
        if (cmdParams.length < 2 || cmdParams[1].trim().length() == 0) {
            notValidMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        int result = -1;
        try {
            result = dbManager.dropTable(tableName);
            if (result == -1) {
                LOG.warn("Table '{}' wasn't deleted. Please see the reason in logs", tableName);
                view.write(String.format("Table '%s' wasn't deleted. Please see the reason in logs", tableName));
            } else
                view.write(String.format("Table '%s' was successfully deleted", tableName));
        } catch (Exception e) {
            LOG.error("", e);
            view.write(String.format("Table '%s' wasn't deleted. The reason is: %s", tableName, e.getMessage()));
        }
        LOG.traceExit();
    }
}