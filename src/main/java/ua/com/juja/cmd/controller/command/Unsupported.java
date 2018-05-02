package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.view.View;

public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean isExecutable(String command) {
        return true;
    }

    @Override
    public void execute(String command) {
        view.write("Command '" + command + "' is not supported");
    }
}
