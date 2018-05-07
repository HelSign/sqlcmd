package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class ConnectDB implements Command {
    private View view;
    private DBManager dbManager;
    final static private String COMMAND = "connect";

    public ConnectDB(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        connectToDB(command);
    }

    private void connectToDB(String command) {
        if (!isExecutable(command)) {
            printError(view, command);
            return;
        }
        String[] cmdWithParams = command.split("\\|");
        if (cmdWithParams.length != 4) {
            printError(view, command);
            return;
        }
        String user = cmdWithParams[2].trim();
        String password = cmdWithParams[3].trim();
        String dbName = cmdWithParams[1].trim();
        try {
            dbManager.makeConnection(dbName, user, password);
            view.write("You are connected to your DB now!");
        } catch (Exception e) {
            view.write("Please enter correct username and password. See detailed error message below\t");
            view.write(e.getMessage());
        }
    }
}
