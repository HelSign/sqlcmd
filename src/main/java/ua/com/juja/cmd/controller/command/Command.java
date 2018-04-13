package ua.com.juja.cmd.controller.command;

public interface Command {

    boolean isExecutable(String command);

    void execute(String command);
}
