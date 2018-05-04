package ua.com.juja.cmd.model;

import java.util.*;

public class DBDataSet implements DataSet {
    private Map<String, Object> data = new LinkedHashMap<>();

    @Override
    public void put(String name, Object value) {
        data.put(name, value);
    }

    @Override
    public void update(DataSet newData) {
        Set<String> columns = newData.getNames();
        for (String column : columns) {
            data.put(column, newData.get(column));
        }
    }

    @Override
    public Object get(String name) {
        return data.get(name);
    }

    @Override
    public Set<String> getNames() {
        return data.keySet();
    }

    @Override
    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }

    @Override
    public int hashCode() {
        Set<String> names = this.getNames();
        int hashCode = 0;
        for (String name : names) {
            try {
                hashCode += name.hashCode() + this.get(name).hashCode();
            } catch (Exception e) {
                return 0;
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
        if(names.size()!=dataSetNames.size()) return false;

        for (String name : names) {
            try {
                if (!this.get(name).equals(((DBDataSet) dataSet).get(name)))
                    return false;
            } catch (Exception e) {
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
