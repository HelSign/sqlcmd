package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

/**
 * Requests to insert data into specified table
 */
public class Insert extends GeneralCommand {
    private final static String COMMAND = "insert";

    public Insert(View view, DBManager dbManager) {
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
        if (cmdParams.length < 4 || cmdParams[1].trim().length() == 0 || cmdParams.length % 2 != 0) {
            notValidMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        DBDataSet data = new DBDataSet();
        for (int i = 2; i < cmdParams.length; i++) {
            data.put(cmdParams[i].trim(), cmdParams[++i].trim());
        }
        try {
            int num = dbManager.insertRows(tableName, data);
            view.write(String.format("%d rows were successfully inserted into table '%s'", num, tableName));
        } catch (Exception e) {
            LOG.error("", e);
            view.write(String.format("Data wasn't inserted into table " +
                    "'%s'. The reason is: %s", tableName, e.getMessage()));
        }
        LOG.traceExit();
    }
}