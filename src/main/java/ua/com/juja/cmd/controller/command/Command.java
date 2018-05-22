package ua.com.juja.cmd.controller.command;

/**
 * Interface for Command pattern
 */
public interface Command {

    boolean isExecutable(String command);

    void execute(String command);
}
