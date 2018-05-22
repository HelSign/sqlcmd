package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

/**
 * Requests to update data in table according to specified condition
 */
public class Update extends GeneralCommand {
    public final static String COMMAND = "update";

    public Update(View view, DBManager dbManager) {
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
        if (cmdParams.length < 6 || cmdParams.length % 2 != 0 || cmdParams[1].trim().length() < 1) {
            notValidMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        DBDataSet condition = new DBDataSet();
        condition.put(cmdParams[2].trim(), cmdParams[3].trim());
        DBDataSet data = new DBDataSet();
        for (int i = 4; i < cmdParams.length; i++) {
            data.put(cmdParams[i].trim(), cmdParams[++i].trim());
        }
        try {
            int num = dbManager.updateRows(tableName, condition, data);
            if (num == -1) {
                view.write(String.format("Data wasn't updated in table '%s'", tableName));
                LOG.warn("Data wasn't updated in table '{}'", tableName);
            } else
                view.write(String.format("%d rows were updated in table '%s'", num, tableName));
        } catch (SQLException e) {
            view.write(String.format("Data wasn't updated in table '%s'", tableName));
            view.write(e.getMessage());
            LOG.error("", e);
        }
        LOG.traceExit();
    }
}