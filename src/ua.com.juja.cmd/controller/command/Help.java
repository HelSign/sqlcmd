package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.view.View;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith("help");
    }

    @Override
    public void execute(String command) {
        printHelp();
    }

    private void printHelp() {
        view.write("List of commands:");
        view.write("    connect | database | username | password");
        view.write("        To connect to database");
        view.write("    help");
        view.write("        To see list of commands use command help");
        view.write("    exit");
        view.write("        To exit");
    }
}
