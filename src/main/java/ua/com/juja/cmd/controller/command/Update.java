package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;
import ua.com.juja.cmd.model.DBDataSet;


public class Update implements Command {

    private View view;
    private DBManager dbManager;

    final static public String COMMAND = "update";

    public Update(View view, DBManager dbManager) {
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

        if (cmdParams.length == 6 && cmdParams[1].trim().length() > 0) {
            String tableName = cmdParams[1].trim();
            DBDataSet condition = new DBDataSet();
            condition.put(cmdParams[2], cmdParams[3]);

            DBDataSet data = new DBDataSet();
            data.put(cmdParams[4], cmdParams[5]);

            try {
                int num = dbManager.updateRows(tableName, condition, data);
                if (num == -1)
                    view.write(String.format("Data wasn't updated in table '%s'", tableName));
                else
                    view.write(String.format("%d rows were updated in from table '%s'", num, tableName));
            } catch (Exception e) {
                view.write(String.format("Data wasn't updated in table '%s'", tableName));
                view.write("" + e);
            }
        } else
            printError(command);
    }

    private void printError(String command) {
        view.write("Please enter a valid command");
    }
}