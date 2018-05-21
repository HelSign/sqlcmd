package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.Arrays;

public class ConnectDB extends GeneralCommand {//todo javadocs
    private final static String COMMAND = "connect";

    public ConnectDB(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        LOG.traceEntry();
        connectToDB(command);
        LOG.traceExit();
    }

    private void connectToDB(String command) {
        if (!isExecutable(command)) {
            notValidMessage(command);
            return;
        }
        String[] cmdWithParams = command.split("\\|");
        if (cmdWithParams.length != 4) {
            notValidMessage(command);
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
            LOG.error("",e);
        }
    }
}
