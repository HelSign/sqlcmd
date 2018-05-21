package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.Arrays;


public class Clear extends GeneralCommand {
    public final static String COMMAND = "clear";

    public Clear(View view, DBManager dbManager) {
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
        if (cmdParams.length < 2 || cmdParams[1].trim().length() < 1) {
            notValidMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        try {
            int numRows = dbManager.truncateTable(tableName);
            if (numRows == -1) {
                view.write(String.format("Table '%s' wasn't cleared", tableName));
                LOG.warn("Table '{}' wasn't cleared", tableName);
            } else {
                view.write(String.format("Table '%s' was successfully cleared", tableName));
                LOG.trace("Table '{}' was successfully cleared", command);
            }
        } catch (Exception e) {
            view.write(String.format("Table '%s' wasn't cleared", tableName));
            view.write(e.getMessage());
            LOG.error("", e);
        }
        LOG.traceExit();
    }
}
