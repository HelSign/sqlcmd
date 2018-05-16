package ua.com.juja.cmd.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DBDataSetTest {
    private DataSet dataSet;

    @BeforeEach
    public void setUp() {
        dataSet = new DBDataSet();
        dataSet.put("name1", "value1");
        dataSet.put("name2", "value2");
        dataSet.put("name3", "value3");
    }

    @Test
    public void testUpdate() {
        //given
        DataSet updateData = new DBDataSet();
        updateData.put("name3", "new_value");
        DataSet expectedData = new DBDataSet();
        expectedData.put("name1", "value1");
        expectedData.put("name2", "value2");
        expectedData.put("name3", "new_value");
        //when
        dataSet.update(updateData);
        //then
        assertEquals(expectedData, dataSet);
    }

    @Test
    public void getNames() {
        Set<String> expected = new LinkedHashSet<String>();
        expected.add("name1");
        expected.add("name2");
        expected.add("name3");
        assertEquals(expected, dataSet.getNames());
    }

    @Test
    public void getValues() {
        List<String> expected = new ArrayList<String>();
        expected.add("value1");
        expected.add("value2");
        expected.add("value3");
        assertEquals(expected, dataSet.getValues());
    }

    @Test
    public void testHashCode() {
        DataSet expectedData = new DBDataSet();
        expectedData.put("name1", "value1");
        expectedData.put("name2", "value2");
        expectedData.put("name3", "value3");
        assertEquals(expectedData.hashCode(), dataSet.hashCode());
    }

    @Test
    public void testHashCodeNull() {
        dataSet.put(null, null);
        int expectedHash = "name1".hashCode() + "value1".hashCode()
                + "name2".hashCode() + "value2".hashCode()
                + "name3".hashCode() + "value3".hashCode();

        assertEquals(expectedHash, dataSet.hashCode());
    }

    @Test
    public void testEquals() {
        DataSet expectedData = new DBDataSet();
        expectedData.put("name1", "value1");
        expectedData.put("name2", "value2");
        expectedData.put("name3", "new_value");
        assertNotEquals(expectedData, dataSet);
    }

    @Test
    public void testEqualsNull() {
        DataSet expectedData = null;
        assertNotEquals(expectedData, dataSet);
    }


    @Test
    public void testToString() {
        assertEquals("[name1, name2, name3]", dataSet.toString());
    }
}