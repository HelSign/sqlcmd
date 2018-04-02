package ua.com.juja.cmd.controller;

import ua.com.juja.cmd.controller.command.Command;
import ua.com.juja.cmd.controller.command.ConnectDB;
import ua.com.juja.cmd.controller.command.Exit;
import ua.com.juja.cmd.controller.command.Help;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

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

        while (true) {
            String input = view.read();
            for (Command command: commands) {
                if(command.isExecutable(input)) {
                    command.execute(input);
                    break;
                }
            }
          /*  if (inpute.startsWith("exit")) {
                view.write("Are you sure you want to exit now? Never mind. It's done");
                System.exit(0);
            } else if (inpute.startsWith("help")) {
                printHelp();
            } else if (inpute.startsWith("connect")) {
                connectToDB(inpute);
            } else {
                view.write("Unknown command");
                printHelp();
            }*/
        }
    }


}
