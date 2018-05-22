package ua.com.juja.cmd.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Implementation of DataSet interface for data from database using Map interface
 */
public class DBDataSet implements DataSet {
    private final static Logger LOG = LogManager.getLogger();
    private Map<String, Object> data = new LinkedHashMap<>();

    /**
     * Adds association of given name and value to the DataSet
     *
     * @param name
     * @param value
     */
    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    /**
     * Updates DBDataSet with newData
     *
     * @param newData DataSet to update current DBDataSet with
     */
    @Override
    public void update(DataSet newData) {
        Set<String> columns = newData.getNames();
        for (String column : columns) {
            data.put(column, newData.get(column));
        }
    }

    /**
     * Returns value to which specified name is mapped
     *
     * @param name String key
     * @return Object associated with given name
     */
    @Override
    public Object get(String name) {
        return data.get(name);
    }

    /**
     * Returns Set of String names stored in the DataSet using Map.keySet()
     *
     * @return Set of String names stored in the DataSet
     */
    @Override
    public Set<String> getNames() {
        return data.keySet();
    }

    /**
     * Returns list of values stored in DataSet using Map.values
     *
     * @return List<Object>
     */
    @Override
    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }

    /** Return hash value using hashCode() of names and values in DataSet
     *
     * @return int as hash value of the DataSet
     */
    @Override
    public int hashCode() {
        Set<String> names = this.getNames();
        int hashCode = 0;
        for (String name : names) {
            try {
                hashCode += name.hashCode() + this.get(name).hashCode();
            } catch (NullPointerException e) {
                hashCode += 0;
            }
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object dataSet) {
        if (dataSet == null) return false;
        if (!(dataSet instanceof DBDataSet)) return false;
        if (this == dataSet) return true;

        Set<String> names = this.getNames();
        Set<String> dataSetNames = ((DBDataSet) dataSet).getNames();
        if (names.size() != dataSetNames.size()) return false;

        for (String name : names) {
            try {
                if (!this.get(name).equals(((DBDataSet) dataSet).get(name)))
                    return false;
            } catch (Exception e) {
                LOG.error("", e);
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data.keySet().toArray());
    }
}
