package ua.com.juja.cmd.controller;

import ua.com.juja.cmd.controller.command.*;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class Controller {
    private DBManager dbManager;
    private View view;
    private Command[] commands;

    public Controller(DBManager dbManager, View view) {
        this.dbManager = dbManager;
        this.view = view;
        this.commands = new Command[]{
                new Help(view),
                new ConnectDB(view, dbManager),
                new Exit(view)};
    }

    public void run() {
        view.write("Hello! You are using SQLCmd application");
        view.write("Please enter a command. For help use command help");
        try {
            while (true) {
                try {
                    String input = view.read();
                    for (Command command : commands) {
                        if (command.isExecutable(input)) {
                            command.execute(input);
                            break;
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException)
                        throw e;
                    view.write("" + e);
                    break;
                }
            }
        } catch (ExitException e) { //do nothing
        }
    }


}
