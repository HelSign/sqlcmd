package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBDataSet;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class Insert implements Command {
    private View view;
    private DBManager dbManager;
    final static private String COMMAND = "insert";

    public Insert(View view, DBManager dbManager) {
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
            printError(view,command);
            return;
        }
        String[] cmdParams = command.split("\\|");
        if (cmdParams.length > 4 && cmdParams[1].trim().length() > 0) {
            String tableName = cmdParams[1].trim();
            DBDataSet data = new DBDataSet();
            for (int i = 2; i < cmdParams.length; i++) {
                data.put(cmdParams[i], cmdParams[++i]);
            }
            try {
                int num = dbManager.insertRows(tableName, data);
                view.write(String.format("%d rows were successfully inserted into table '%s'", num, tableName));
            } catch (Exception e) {
                view.write(String.format("Data wasn't inserted into table " +
                        "'%s'. The reason is: %s", tableName, e.getMessage()));
            }
        } else
            printError(view,command);
    }
}