package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;
import ua.com.juja.cmd.model.DataSet;

import java.util.List;

public class ViewData implements Command {

    private View view;
    private DBManager dbManager;
    final static private String COMMAND = "find";

    public ViewData(View view, DBManager dbManager) {
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
            printError(view, command);
            return;
        }
        String[] cmdParams = command.split("\\|");
        if (cmdParams.length == 2 && cmdParams[1].trim().length() > 0) {
            String tableName = cmdParams[1].trim();
            try {
                List<DataSet> result = dbManager.getTableData(tableName);
                if (result == null)
                    view.write(String.format("Can't show data of '%s' table", tableName));
                else {
                    view.write(String.format("Table '%s' has following data", tableName));
                    for (DataSet dataSet : result) {
                        view.write(dataSet.getValues().toString());
                    }
                    view.write("End data");
                }
            } catch (Exception e) {
                view.write(String.format("Can't show data of '%s' table", tableName));
                view.write(e.getMessage());
            }
        } else
            printError(view, command);
    }

}
