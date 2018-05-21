package ua.com.juja.cmd.controller;

import org.apache.logging.log4j.LogManager;
import ua.com.juja.cmd.model.JDBCManager;
import ua.com.juja.cmd.view.Console;
import org.apache.logging.log4j.Logger;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.traceEntry();
        JDBCManager manager = new JDBCManager();
        Console console = new Console();
        Controller controller = new Controller(manager, console);
        controller.run();
        logger.traceExit();
    }
}
