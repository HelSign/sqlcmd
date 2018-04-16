package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;
import ua.com.juja.cmd.model.DBDataSet;


public class Delete implements Command {

    private View view;
    private DBManager dbManager;

    final static public String COMMAND = "delete";

    public Delete(View view, DBManager dbManager) {
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

        if (cmdParams.length == 4 && cmdParams[1].trim().length() > 0) {
            String tableName = cmdParams[1].trim();
            DBDataSet data = new DBDataSet();
            data.put(cmdParams[2], cmdParams[3]);

            try {
                int num = dbManager.deleteRows(tableName, data);
               // if (num == -1)
                 //   view.write(String.format("Data wasn't deleted from
                // table '%s'", tableName));
                //else
                    view.write(String.format("%d rows were successfully  deleted from table '%s'", num, tableName));
            } catch (Exception e) {
                view.write(String.format("Data wasn't  deleted from table " +
                        "'%s'.The reason is:", tableName, e));
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