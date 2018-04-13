package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class Tables implements Command {
    private View view;
    private DBManager dbManager;

    final static public String COMMAND = "tables";

    public Tables(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        view.write(dbManager.getTablesNames());
    }
}
