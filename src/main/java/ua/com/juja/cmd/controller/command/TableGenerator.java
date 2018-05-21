package ua.com.juja.cmd.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.com.juja.cmd.model.DataSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TableGenerator {//todo test
    private static final String SEPARATOR = "|";
    private static final String HSEPARATOR = "+";
    private static final int COLUMN_SIZE = 20;
    private static final String HBORDER = "-";
    private final static Logger LOG = LogManager.getLogger();

    public static String table(List<DataSet> data, Set<String> columns) {
        LOG.traceEntry();
        StringBuilder result = new StringBuilder();
        result.append(System.lineSeparator());
        result.append(closingRow(columns.size()));
        result.append(header(columns));
        result.append(closingRow(columns.size()));
        LOG.trace(result);
        for (DataSet set : data) {
            result.append(SEPARATOR);
            List<Object> values = set.getValues();
            for (Object obj : values) {
                LOG.trace("obj="+obj);
                result.append(String.format("%-" + COLUMN_SIZE + "s", (obj==null)? " ": obj.toString()));
                result.append(SEPARATOR);
            }
            result.append(System.lineSeparator());
        }
        result.append(closingRow(columns.size()));
        LOG.traceExit();
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
            result.append(String.format("%-" + COLUMN_SIZE + "s", column)).append(SEPARATOR);
        }
        result.append(System.lineSeparator());
        return result.toString();
    }
}
