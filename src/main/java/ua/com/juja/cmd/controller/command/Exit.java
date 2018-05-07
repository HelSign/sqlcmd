package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

public class Exit implements Command {
    private final DBManager dbManager;
    private View view;
    final static private String COMMAND = "exit";

    public Exit(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        view.write("Are you sure you want to exit now? Never mind. It's done");
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            view.write(e.getMessage());
        }
        throw new ExitException();
    }
}
