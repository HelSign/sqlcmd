package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.view.View;

public class Exit implements Command {
    private View view;
    final static public String COMMAND = "exit";

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith("exit");
    }

    @Override
    public void execute(String command) {
        view.write("Are you sure you want to exit now? Never mind. It's done");
        throw new ExitException();
    }
}
