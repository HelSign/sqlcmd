package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

/**
 * Super class for all commands
 */
public abstract class GeneralCommand implements Command {
    final static Logger LOG = LogManager.getLogger();
    final View view;
    final DBManager dbManager;

    public GeneralCommand(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    void notValidMessage(String command) {
        view.write(String.format("Command '%s' is not valid", command));
        LOG.warn("Command '{}' is not valid", command);
    }
}
