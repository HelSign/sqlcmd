package ua.com.juja.cmd.controller;

import ua.com.juja.cmd.model.JDBCManager;
import ua.com.juja.cmd.view.Console;

public class Main {
    public static void main(String[] args) {
        JDBCManager manager = new JDBCManager();
        Console console = new Console();
        Controller controller = new Controller(manager, console);
        controller.run();
    }
}
