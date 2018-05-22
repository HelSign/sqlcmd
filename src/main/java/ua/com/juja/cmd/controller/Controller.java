package ua.com.juja.cmd.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.controller.command.*;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.util.ArrayList;

/**
 * The Controller accepts input and converts it into commands to model or view
 */
public class Controller {
    private View view;
    private ArrayList<Command> commands;
    private final static Logger LOG = LogManager.getLogger();

    /**
     * Constructs Contoller who manages specified DBManager and View and uses Command pattern
     *
     * @param dbManager DBManager which manages data in database
     * @param view      Ouput / input object implementing View interface
     */
    public Controller(DBManager dbManager, View view) {
        this.view = view;
        commands = new ArrayList<Command>();
        commands.add(new Help(view));
        commands.add(new ConnectDB(view, dbManager));
        commands.add(new Create(view, dbManager));
        commands.add(new Drop(view, dbManager));
        commands.add(new Tables(view, dbManager));
        commands.add(new Clear(view, dbManager));
        commands.add(new Insert(view, dbManager));
        commands.add(new Update(view, dbManager));
        commands.add(new ViewData(view, dbManager));
        commands.add(new Delete(view, dbManager));
        commands.add(new Exit(view, dbManager));
        commands.add(new Unsupported(view, dbManager));
    }

    /**
     * The main method of Controller which interprets input from View into commands
     */
    public void run() {
        LOG.traceEntry();
        view.write("Hello! You are using SQLCmd application");
        view.write("Please enter a command. For help use command help");
        try {
            while (true) {
                try {
                    String input = view.read().trim();
                    for (Command command : commands) {
                        if (command.isExecutable(input)) {
                            command.execute(input);
                            break;
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException)
                        throw e;
                    else {
                        LOG.warn(e);
                        view.write(e.getMessage());
                    }
                    break;
                }
            }
        } catch (ExitException e) {
            LOG.traceExit();
        }
    }
}
