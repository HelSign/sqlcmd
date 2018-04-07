package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class ViewData implements Command {

    public ViewData(View view, DBManager dbManager) {
    }

    @Override
    public boolean isExecutable(String command) {
        return false;
    }

    @Override
    public void execute(String command) {

    }
}
