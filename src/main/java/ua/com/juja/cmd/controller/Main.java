package ua.com.juja.cmd.controller;

import ua.com.juja.cmd.model.JDBCManager;
import ua.com.juja.cmd.view.Console;

public class Main {
    public static void main(String[] args) {
        JDBCManager mngr = new JDBCManager();
        Console console = new Console();
        Controller cntrl = new Controller(mngr, console);
        cntrl.run();
    }
}
