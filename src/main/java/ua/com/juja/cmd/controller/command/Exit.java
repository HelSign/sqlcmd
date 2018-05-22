package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

public class Exit extends GeneralCommand {
    public final static String COMMAND = "exit";

    public Exit(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        LOG.traceEntry();
        view.write("Are you sure you want to exit now? Never mind. It's done");
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            LOG.error("", e);
            view.write(e.getMessage());
        }
        LOG.traceExit();
        throw new ExitException();
    }
}
