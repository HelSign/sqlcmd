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
 Set <String> columns = newData.getNames();
        for (String column:columns ){
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
        return (ArrayList<Object>) data.values();
        //return new ArrayList<Object>( data.values());
    }
}
