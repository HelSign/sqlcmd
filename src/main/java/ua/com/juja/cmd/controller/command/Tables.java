package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class Tables extends GeneralCommand {
    public final static String COMMAND = "tables";

    public Tables(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        LOG.traceEntry();
        try {
            view.write(dbManager.getTablesNames());
        } catch (Exception e) {
            LOG.error("", e);
            view.write("Can't print tables names. The reason is :");
            view.write(e.getMessage());
        }
        LOG.traceExit();
    }
}
