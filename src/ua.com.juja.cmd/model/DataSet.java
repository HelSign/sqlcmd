package ua.com.juja.cmd.model;


import java.util.List;
import java.util.Set;

public interface DataSet {

    void put(String name, Object value);

    void update(DataSet newData);

    Object get(String name);

    Set<String> getNames();

    List<Object> getValues();
}