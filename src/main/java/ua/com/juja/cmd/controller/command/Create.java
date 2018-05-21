package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.LinkedHashSet;
import java.util.Set;

public class Create extends GeneralCommand {
    public final static String COMMAND = "create";

    public Create(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        LOG.traceEntry();
        String[] input = command.split("\\|");
        if (input.length <= 2) {
            notValidMessage(command);
            return;
        }
        String tableName = input[1].trim();
        if (tableName.length() == 0) {
            notValidMessage(command);
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
            LOG.error("",e);
            view.write(String.format("Table '%s' wasn't created. The reason is: %s", tableName, e.getMessage()));
        }
        if (result == -1) {
            LOG.warn("Table '{}' wasn't created. Please see the reason in logs", tableName);
            view.write(String.format("Table '%s' wasn't created. Please see the reason in logs", tableName));
        }
        LOG.traceExit();
    }


}

