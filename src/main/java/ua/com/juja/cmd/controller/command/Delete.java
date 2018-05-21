package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;


public class Delete extends GeneralCommand {
    public final static String COMMAND = "delete";

    public Delete(View view, DBManager dbManager) {
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
        if (cmdParams.length != 4 || cmdParams[1].trim().length() < 1) {
            notValidMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        DBDataSet data = new DBDataSet();
        data.put(cmdParams[2].trim(), cmdParams[3].trim());
        try {
            int num = dbManager.deleteRows(tableName, data);
            if (num == -1) {
                view.write(String.format("Data wasn't deleted from table '%s'", tableName));
            } else
                view.write(String.format("%d rows were successfully  deleted from table '%s'", num, tableName));
        } catch (Exception e) {
            view.write(String.format("Data wasn't  deleted from table '%s'.The reason is:%s", tableName, e));
            LOG.error(e);
        }
        LOG.traceExit();
    }
}