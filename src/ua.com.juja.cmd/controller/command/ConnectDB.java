package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class ConnectDB implements Command {
    private View view;
    private DBManager dbManager;

    public ConnectDB(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith("connect");
    }

    @Override
    public void execute(String command) {
        connectToDB(command);
    }

    private void connectToDB(String command) {
        String user;
        String password;
        String dbName;
        String[] cmdWithParams = command.split("\\|");
        if (cmdWithParams.length == 4) {
            user = cmdWithParams[2];
            password = cmdWithParams[3];
            dbName = cmdWithParams[1];
        } else {
            view.write("Please enter correct command");
            return;
        }
        try {
            dbManager.makeConnection(dbName, user, password);
        } catch (Exception e) {
            view.write("Please enter correct username and password. See detailed error message below\t");
            view.write(e.getMessage());
        }
    }

}
