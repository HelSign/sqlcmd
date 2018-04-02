package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.view.View;

public interface Command {

  boolean  isExecutable(String command);
  void execute(String command);
}
