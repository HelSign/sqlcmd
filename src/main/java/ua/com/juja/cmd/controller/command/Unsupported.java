package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.view.View;

public class Unsupported implements Command {

    public Unsupported(View view) {
    }

    @Override
    public boolean isExecutable(String command) {
        return false;
    }

    @Override
    public void execute(String command) {

    }
}
