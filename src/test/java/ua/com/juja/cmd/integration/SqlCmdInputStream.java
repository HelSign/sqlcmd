package ua.com.juja.cmd.integration;

import java.io.IOException;
import java.io.InputStream;

public class SqlCmdInputStream extends InputStream {
    String input;
    private boolean endLine = false;

    @Override
    public int read() throws IOException {
        if (input.length() == 0) {
            return -1;
        }

        if (endLine) {
            endLine = false;
            return -1;
        }

        char ch = input.charAt(0);
        input = input.substring(1);
        if (ch == '\n') {
            endLine = true;
        }
        return (int) ch;
    }

    public void add(String input) {
        if (this.input == null) {
            this.input = input;
        } else this.input += "\n" + input;
    }
}
