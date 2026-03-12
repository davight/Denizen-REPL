package main.util;

import com.denizenscript.denizencore.utilities.debugging.Debug;
import main.Main;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private Properties properties;

    public PropertyReader() {
        this.properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/app.properties"));
        } catch (IOException e) {
            properties = null;
            Debug.echoError("Could not load properties file.");
        }
    }

    public String getProperty(String key) {
        return properties == null ? "?" : properties.getProperty(key);
    }
}
