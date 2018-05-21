package ua.com.juja.cmd.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private final static Properties properties = new Properties();
    private final static Logger LOG = LogManager.getLogger();

    public Configuration() {
        LOG.traceEntry();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jdbc.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error("",e);
            throw new RuntimeException("Can't load jdbc properties file: " + e);
        }
        LOG.traceExit();
    }

    public String getUser() {
        return properties.getProperty("database.user");
    }

    public String getPassword() {
        return properties.getProperty("database.password");
    }

    public String getDbName() {
        return properties.getProperty("database.name");
    }

    public String getJDBCDriver() {
        return properties.getProperty("database.jdbc.driver");
    }

    public String getServer() {
        return properties.getProperty("database.server");
    }

    public String getPort() {
        return properties.getProperty("database.port");
    }

    public String getUrl() {
        return getJDBCDriver() + getServer() + ":" + getPort() + "/" + getDbName();
    }
}
