package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.model.DataSet;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;
import java.util.List;

/**
 * Requests data from particular table
 */
public class ViewData extends GeneralCommand {
    public final static String COMMAND = "find";

    public ViewData(View view, DBManager dbManager) {
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
        if (cmdParams.length != 2 || cmdParams[1].trim().length() == 0) {
            notValidMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        try {
            List<DataSet> result = dbManager.getTableData(tableName);
            LOG.trace("Number of rows in table {} : {}", tableName, result.size());
            if (result == null) {
                LOG.warn("Can't show data of '{}' table", tableName);
                view.write(String.format("Can't show data of '%s' table", tableName));
            } else if (result.size() == 0) {
                view.write(String.format("Table '%s' has no data", tableName));
                LOG.trace("Table '{}' has no data", tableName);
            } else {
                view.write(String.format("Table '%s' has following data", tableName));
                view.write(TableGenerator.table(result, dbManager.getTableColumns(tableName)));
                view.write("End data");
            }
        } catch (SQLException e) {
            LOG.error("", e);
            view.write(String.format("Can't show data of '%s' table", tableName));
            view.write(e.getMessage());
        }
        LOG.traceExit();
    }
}
