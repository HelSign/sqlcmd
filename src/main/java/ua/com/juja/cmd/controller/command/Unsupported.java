package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

public class Unsupported extends GeneralCommand {

    public Unsupported(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return true;
    }

    @Override
    public void execute(String command) {
        notValidMessage(command);
    }
}
