package ua.com.juja.cmd.controller.command;

import ua.com.juja.cmd.model.DataSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;


public class TableGenerator {
    private static final String SEPARATOR = "|";
    private static final String HSEPARATOR = "+";
    private static final int COLUMN_SIZE = 20;
    private static final String HBORDER = "-";

    public static String table(List<DataSet> data, Set<String> columns) {
        StringBuilder result = new StringBuilder();
        result.append(System.lineSeparator());
        result.append(closingRow(columns.size()));
        result.append(header(columns));
        result.append(closingRow(columns.size()));
        for (DataSet set : data) {
            result.append(SEPARATOR);
            for (Object obj : set.getValues()) {
                result.append(String.format("%-" + COLUMN_SIZE + "s", obj.toString())).append(SEPARATOR);
            }
            result.append(System.lineSeparator());
        }
        result.append(closingRow(columns.size()));
        return result.toString();
    }

    private static String closingRow(int columns) {
        StringBuilder result = new StringBuilder(HSEPARATOR);
        for (int i = 0; i < columns; i++) {
            result.append(String.join("", Collections.nCopies(COLUMN_SIZE, HBORDER)));
            result.append(HSEPARATOR);
        }
        result.append(System.lineSeparator());
        return result.toString();
    }

    private static String header(Set<String> columns) {
        StringBuilder result = new StringBuilder(SEPARATOR);
        for (String column : columns) {
            result.append(String.format("%-"+COLUMN_SIZE+"s",column)).append(SEPARATOR);
        }
        result.append(System.lineSeparator());
        return result.toString();
    }
}
