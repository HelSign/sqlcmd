package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public abstract class GeneralCommand implements Command {
    final static Logger LOG = LogManager.getLogger();
    View view;
    DBManager dbManager;
    String COMMAND;

    public GeneralCommand(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    void notValidMessage(String command) {
        view.write(String.format("Command '%s' is not valid", command));
        LOG.warn("Command '{}' is not valid", command);
    }
}
