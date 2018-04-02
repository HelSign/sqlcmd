package ua.com.juja.cmd.controller;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

public class Controller {
    private DBManager dbManager;
    private View view;


    public Controller(DBManager dbManager, View view) {
        this.dbManager = dbManager;
        this.view = view;
    }

    public void run() {
        view.write("Hello! You are using SQLCmd application");
        view.write("Please enter a command. For help use command help");

        while (true) {
            String command = view.read();
            if (command.startsWith("exit")) {
                view.write("Are you sure you want to exit now? Never mind. It's done");
                System.exit(0);
            } else if (command.startsWith("help")) {
                printHelp();
            } else if (command.startsWith("connect")) {
                connectToDB(command);
            } else {
                view.write("Unknown command");
                printHelp();
            }
        }
    }


    private void printHelp() {
        view.write("List of commands:");
        view.write("To connect to database use command connect with params");
        view.write("connect | database | username | password");
        view.write("To see list of commands use command help");
        view.write("help");

    }

    private void connectToDB(String command) {
        String user;
        String password;
        String dbName;
        String[] cmdWithParams = command.split("\\|");
        if (cmdWithParams.length == 4) {
            user = cmdWithParams[2];
            password = cmdWithParams[3];
            dbName = cmdWithParams[1];
        } else {
            view.write("Please enter correct command");
            return;
        }
        try {
            dbManager.makeConnection(dbName, user, password);
        } catch (Exception e) {
            view.write("Please enter correct username and password. See detailed error message below\t");
            view.write(e.getMessage());
        }
    }
}
